package module.jobBank.domain;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.beans.JobOfferBean;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.JobBankProcessStageState;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.RemoteDegree;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public abstract class JobOffer extends JobOffer_Base {

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
	Student student = Student.readStudent(UserView.getCurrentUser());
	if (student == null) {
	    return false;
	}
	return OfferCandidacy.canCreateOfferCandidacy(student, this);
    }

    public boolean isApproved() {
	return getApprovalDate() != null;
    }

    public void approve() {
	setApprovalDate(new DateTime());
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

    @Service
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
	return getJobBankSystem().readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
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
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getJobOffersSet());
    }

    private void setForm(JobOfferBean bean) {
	checkDates(bean);
	setPlace(bean.getPlace());
	setFunction(bean.getFunction());
	setJobOfferType(bean.getJobOfferType());
	setVacancies(bean.getVacancies());
	setRemoteDegrees(bean.getRemoteDegrees());
	setDescriptionOffer(bean.getDescriptionOffer());
	setRequirements(bean.getRequirements());
	setBeginDate(bean.getBeginDate());
	setEndDate(bean.getEndDate());
    }

    @Override
    public void setVacancies(Integer vacancies) {
	if (vacancies <= 0) {
	    throw new DomainException("message.error.jobOffer.min.vacancies", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
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

    public void setRemoteDegrees(List<RemoteDegree> remoteDegrees) {
	getRemoteDegrees().clear();
	for (RemoteDegree remoteDegree : remoteDegrees) {
	    addRemoteDegrees(remoteDegree);
	}
    }

    public boolean canCreateOfferCandidacy() {
	return OfferCandidacy.canCreateOfferCandidacy(Student.readCurrentStudent(), this);
    }

    public int getTotalNumberCandidacies() {
	return getActiveOfferCandidacies().size();
    }

    public boolean hasVacancies() {
	return getSelectCandidaciesCount() < getVacancies();
    }

    public Integer getNumberOfFreeVacancies() {
	int vacancies = getVacancies() - getSelectCandidaciesCount();
	return vacancies < 0 ? 0 : vacancies;
    }

    public boolean hasCandidacies() {
	return isApproved() && getTotalNumberCandidacies() > 0;
    }

    @Service
    public void selectCandidacy(OfferCandidacy offerCandidacy) {
	checkVancacies();
	addSelectCandidacies(offerCandidacy);
	offerCandidacy.setJobOfferSelectCandidacy(this);
    }

    @Service
    public void removeCandidacy(OfferCandidacy offerCandidacy) {
	removeSelectCandidacies(offerCandidacy);
	offerCandidacy.setJobOfferSelectCandidacy(null);
    }

    private void checkVancacies() {
	if (!hasVacancies()) {
	    throw new DomainException("message.error.jobOffer.there.are.no.vacancies.availables",
		    JobBankSystem.JOB_BANK_RESOURCES);
	}
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
	    throw new DomainException("message.error.jobOffer.min.active.days.offer", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES), getMinNumberActiveDaysOffer().toString());
	}
	if (days > getMaxNumberActiveDaysOffer()) {
	    throw new DomainException("message.error.jobOffer.max.active.days.offer", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES), getMaxNumberActiveDaysOffer().toString());
	}
    }

    public abstract boolean isExternalCandidacy();

    public abstract JobOfferExternal getJobOfferExternal();
}
