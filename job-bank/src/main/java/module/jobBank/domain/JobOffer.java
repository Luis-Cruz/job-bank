package module.jobBank.domain;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.beans.JobOfferBean;
import module.jobBank.domain.enums.CandidacyType;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.JobBankProcessStageState;
import module.jobBank.domain.utils.Utils;
import module.workflow.domain.ProcessFile;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.fenixframework.Atomic;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public abstract class JobOffer extends JobOffer_Base {

	public static final Comparator<JobOffer> COMPARATOR_BY_PROCESS_IDENTIFICATION = new Comparator<JobOffer>() {

		@Override
		public int compare(JobOffer o1, JobOffer o2) {
			final JobOfferProcess p1 = o1.getJobOfferProcess();
			final JobOfferProcess p2 = o2.getJobOfferProcess();

			return JobOfferProcess.COMPARATOR_BY_REGISTRATION.compare(p1, p2);
		}

	};

	private static final Integer MIN_ACTIVE_DAYS_OFFER = 15;
	private static final Integer MAX_ACTIVE_DAYS_OFFER = 365;

	protected JobOffer() {
		super();
		setCanceled(Boolean.FALSE);
		setConclued(Boolean.FALSE);
		setJobBankSystem(JobBankSystem.getInstance());
	}

	public JobOffer(JobOfferBean bean) {
		this();
		setCreateJobOffer(bean);
	}

	public void edit(JobOfferBean jobOfferBean) {
		setForm(jobOfferBean);
	}

	public MultiLanguageString getEnterpriseName() {
		return getEnterprise().getName();
	}

	public String getContactPerson() {
		return getEnterprise().getContactPerson();
	}

	public boolean isCanCreateOfferCandidacy() {
		Student student = UserView.getCurrentUser().getPerson().getStudent();
		if (student == null) {
			return false;
		}
		return OfferCandidacy.canCreateOfferCandidacy(student, this);
	}

	public CandidacyType getCandidacyType() {
		return isExternalCandidacy() ? CandidacyType.External : CandidacyType.Internal;
	}

	public Set<ProcessFile> getStudentFilesForJobOfferCandidacy() {
		Student student = UserView.getCurrentUser().getPerson().getStudent();
		if (student == null) {
			return null;
		}
		return OfferCandidacy.getStudentFilesForJobOfferCandidacy(student, this);
	}

	public boolean isApproved() {
		return getApprovalDate() != null;
	}

	public void approve() {
		setApprovalDate(new DateTime());
		// JobBankSystem.getInstance().processNotifications(this);
	}

	public void unapprove() {
		setApprovalDate(null);
	}

	public boolean isEditable() {
		return getSubmittedForApprovalDate() == null && !isCanceled() && !isApproved();
	}

	public boolean isPendingToApproval() {
		return getSubmittedForApprovalDate() != null && !isCanceled() && !isApproved();
	}

	public boolean isCanceled() {
		return getCanceled();
	}

	public boolean isActive() {
		return !isCanceled() && !isConclued();
	}

	@Atomic
	public void conclued() {
		setConclued(true);
	}

	public boolean isConclued() {
		return getConclued();
	}

	public String getPresentationPeriod() {
		return String.format("%s - %s", getBeginDate(), getEndDate());
	}

	public boolean isCandidancyPeriod() {
		LocalDate now = new LocalDate();
		return isApproved() && (now.isAfter(getBeginDate()) || now.equals(getBeginDate()))
				&& (now.isBefore(getEndDate()) || now.equals(getEndDate()));
	}

	public boolean hasExpired() {
		LocalDate now = new LocalDate();
		return !((now.isAfter(getBeginDate()) || now.equals(getBeginDate())) && (now.isBefore(getEndDate()) || now
				.equals(getEndDate())));
	}

	public boolean isBeforeCandidancyPeriod() {
		LocalDate now = new LocalDate();
		return now.isBefore(getBeginDate());
	}

	public boolean isAfterCandidancyPeriod() {
		LocalDate now = new LocalDate();
		return now.isAfter(getEndDate());
	}

	public boolean isAfterCompleteCandidancyPeriod() {
		return isAfterCandidancyPeriod() && isApproved();
	}

	public boolean isSelectionPeriod() {
		return isAfterCompleteCandidancyPeriod() && !isConclued();
	}

	public JobBankProcessStageState getState() {
		if (isCandidancyPeriod()) {
			return JobBankProcessStageState.UNDER_WAY;
		}
		if (isAfterCompleteCandidancyPeriod()) {
			return JobBankProcessStageState.COMPLETED;
		}
		return JobBankProcessStageState.NOT_YET_UNDER_WAY;
	}

	public Set<OfferCandidacy> getActiveOfferCandidacies() {
		return Utils.readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
			@Override
			public boolean evaluate(OfferCandidacy object) {
				return object.isActive();
			}
		}, getOfferCandidacySet());
	}

	public String getReference() {
		return getJobOfferProcess().getProcessIdentification();
	}

	public Integer getMinNumberActiveDaysOffer() {
		return MIN_ACTIVE_DAYS_OFFER;
	}

	public Integer getMaxNumberActiveDaysOffer() {
		return MAX_ACTIVE_DAYS_OFFER;
	}

	public static Set<JobOffer> readAllJobOffers(IPredicate<JobOffer> predicate) {
		JobBankSystem jobBankSystem = JobBankSystem.getInstance();
		return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getJobOffersSet());
	}

	private void setForm(JobOfferBean bean) {
		if (!JobBankSystem.getInstance().isNPEMember()) {
			checkDates(bean);
		}

		setPlace(bean.getPlace());
		setFunction(bean.getFunction());
		setFunctionDescription(bean.getFunctionDescription());
		setJobOfferType(bean.getJobOfferType());
		setVacancies(bean.getVacancies());
		setRemoteDegrees(bean.getDegrees());
		setTerms(bean.getTerms());
		setRequirements(bean.getRequirements());
		setBeginDate(bean.getBeginDate());
		setEndDate(bean.getEndDate());

		if (getJobOfferExternal() != null) {
			getJobOfferExternal().setExternalLink(bean.getExternalLink());
		}
	}

	@Override
	public void setVacancies(Integer vacancies) {
		if (vacancies <= 0) {
			throw new DomainException("message.error.jobOffer.min.vacancies",
					DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
		}
		super.setVacancies(vacancies);
	}

	protected void setCreateJobOffer(JobOfferBean bean) {
		setForm(bean);
		setCreationDate(bean.getCreationDate());
		setJobBankYear(JobBankYear.findJobBankYear(bean.getCreationDate().getYear()));
		setEnterprise(bean.getEnterprise());
		setJobOfferProcess(new JobOfferProcess(this));
	}

	public void setRemoteDegrees(List<FenixDegree> degrees) {
		getDegreeSet().clear();
		for (FenixDegree degree : degrees) {
			addDegree(degree);
		}
	}

	public boolean canCreateOfferCandidacy() {
		return OfferCandidacy.canCreateOfferCandidacy(UserView.getCurrentUser().getPerson().getStudent(), this);
	}

	public boolean getCanRemoveOfferCandidacy() {
		return !isExternalCandidacy() && isCandidancyPeriod() && getCandidacyForThisUser(UserView.getCurrentUser()) != null;
	}

	public boolean getHasCandidacyForThisUser() {
		return getCandidacyForThisUser(UserView.getCurrentUser()) != null;
	}

	public OfferCandidacy getCandidacyForThisUser(User user) {
		Student student = user.getPerson().getStudent();
		if (student != null) {
			for (OfferCandidacy offerCandidacy : getOfferCandidacy()) {
				if (offerCandidacy.getStudent().equals(student) && offerCandidacy.isActive()) {
					return offerCandidacy;
				}
			}
		}
		return null;
	}

	public int getTotalNumberCandidacies() {
		return getActiveOfferCandidacies().size();
	}

	public boolean hasVacancies() {
		return getSelectCandidaciesSet().size() < getVacancies();
	}

	public Integer getNumberOfFreeVacancies() {
		int vacancies = getVacancies() - getSelectCandidaciesSet().size();
		return vacancies < 0 ? 0 : vacancies;
	}

	public boolean hasCandidacies() {
		return isApproved() && getTotalNumberCandidacies() > 0;
	}

	@Atomic
	public void selectCandidacy(OfferCandidacy offerCandidacy) {
		if (hasVacancies()) {
			addSelectCandidacies(offerCandidacy);
			offerCandidacy.setJobOfferSelectCandidacy(this);
		}
	}

	@Atomic
	public void removeCandidacy(OfferCandidacy offerCandidacy) {
		removeSelectCandidacies(offerCandidacy);
		offerCandidacy.setJobOfferSelectCandidacy(null);
	}

	private void checkDates(JobOfferBean bean) {
		LocalDate beginDate = bean.getBeginDate();
		LocalDate endDate = bean.getEndDate();
		if (beginDate.isAfter(endDate)) {
			throw new DomainException("message.jobOffer.beginDate.isAfter.endDate", ResourceBundle.getBundle(
					JobBankSystem.JOB_BANK_RESOURCES, Language.getLocale()));
		}
		int days = Days.daysBetween(bean.getBeginDate(), bean.getEndDate()).getDays();
		if (days < getMinNumberActiveDaysOffer()) {
			throw new DomainException("message.error.jobOffer.min.active.days.offer",
					DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES), getMinNumberActiveDaysOffer().toString());
		}
		if (days > getMaxNumberActiveDaysOffer()) {
			throw new DomainException("message.error.jobOffer.max.active.days.offer",
					DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES), getMaxNumberActiveDaysOffer().toString());
		}
	}

	public abstract boolean isExternalCandidacy();

	public abstract JobOfferExternal getJobOfferExternal();

	public boolean isUnderConstruction() {
		return isEditable();
	}

	public boolean isWaitingForApproval() {
		return !isApproved() && !isCanceled() && !isEditable();
	}

	public boolean isPublished() {
		return isCandidancyPeriod();
	}

	public boolean isUnderSelection() {
		return !isCandidancyPeriod() && isSelectionPeriod();
	}

	public boolean isApprovedAndOnlyApproved() {
		return isApproved() && !isCandidancyPeriod() && !isSelectionPeriod() && !isConclued();
	}

	public boolean isArchived() {
		return isConclued();
	}

	public boolean isProfessionalStage() {
		return getJobOfferType().equals(JobOfferType.PROFISSIONAL_STAGE);
	}

	public boolean isExtracurricularStage() {
		return getJobOfferType().equals(JobOfferType.EXTRACURRICULAR_STAGE);
	}

	public boolean isSummerStage() {
		return getJobOfferType().equals(JobOfferType.SUMMER_STAGE);
	}

	public boolean isNationalEmployment() {
		return getJobOfferType().equals(JobOfferType.NATIONAL_EMPLOYMENT);
	}

	public boolean isInternationalEmployment() {
		return getJobOfferType().equals(JobOfferType.INTERNATIONAL_EMPLOYMENT);
	}

	public boolean isResearch() {
		return getJobOfferType().equals(JobOfferType.RESEARCH);
	}

	@Deprecated
	public boolean hasCreationDate() {
		return getCreationDate() != null;
	}

	@Deprecated
	public boolean hasBeginDate() {
		return getBeginDate() != null;
	}

	@Deprecated
	public boolean hasEndDate() {
		return getEndDate() != null;
	}

	@Deprecated
	public boolean hasSubmittedForApprovalDate() {
		return getSubmittedForApprovalDate() != null;
	}

	@Deprecated
	public boolean hasApprovalDate() {
		return getApprovalDate() != null;
	}

	@Deprecated
	public boolean hasCanceled() {
		return getCanceled() != null;
	}

	@Deprecated
	public boolean hasConclued() {
		return getConclued() != null;
	}

	@Deprecated
	public boolean hasPlace() {
		return getPlace() != null;
	}

	@Deprecated
	public boolean hasFunction() {
		return getFunction() != null;
	}

	@Deprecated
	public boolean hasFunctionDescription() {
		return getFunctionDescription() != null;
	}

	@Deprecated
	public boolean hasRequirements() {
		return getRequirements() != null;
	}

	@Deprecated
	public boolean hasTerms() {
		return getTerms() != null;
	}

	@Deprecated
	public boolean hasJobOfferType() {
		return getJobOfferType() != null;
	}

	@Deprecated
	public boolean hasJobBankSystem() {
		return getJobBankSystem() != null;
	}

	@Deprecated
	public boolean hasJobBankYear() {
		return getJobBankYear() != null;
	}

	@Deprecated
	public boolean hasJobOfferProcess() {
		return getJobOfferProcess() != null;
	}

	@Deprecated
	public java.util.Set<module.jobBank.domain.FenixDegree> getDegree() {
		return getDegreeSet();
	}

	@Deprecated
	public boolean hasAnyDegree() {
		return !getDegreeSet().isEmpty();
	}

	@Deprecated
	public java.util.Set<module.jobBank.domain.OfferCandidacy> getOfferCandidacy() {
		return getOfferCandidacySet();
	}

	@Deprecated
	public boolean hasAnyOfferCandidacy() {
		return !getOfferCandidacySet().isEmpty();
	}

	@Deprecated
	public boolean hasEnterprise() {
		return getEnterprise() != null;
	}

	@Deprecated
	public java.util.Set<module.jobBank.domain.Student> getStudent() {
		return getStudentSet();
	}

	@Deprecated
	public boolean hasAnyStudent() {
		return !getStudentSet().isEmpty();
	}

	@Deprecated
	public java.util.Set<module.jobBank.domain.OfferCandidacy> getSelectCandidacies() {
		return getSelectCandidaciesSet();
	}

	@Deprecated
	public boolean hasAnySelectCandidacies() {
		return !getSelectCandidaciesSet().isEmpty();
	}

}
