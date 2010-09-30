package module.jobBank.domain;

import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.beans.JobOfferBean;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.MobilityProcessStageState;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.exceptions.DomainException;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOffer extends JobOffer_Base {

    private JobOffer() {
	super();
	setCanceled(Boolean.FALSE);
	setFilled(Boolean.FALSE);
	setJobBankSystem(JobBankSystem.getInstance());
    }

    public JobOffer(JobOfferBean bean) {
	this();
	setForm(bean);
	setCreationDate(bean.getCreationDate());
	setJobBankYear(JobBankYear.findJobBankYear(bean.getCreationDate().getYear()));
	setEnterprise(bean.getEnterprise());
	JobOfferProcess jobOfferProcess = new JobOfferProcess(this);
    }

    public void edit(JobOfferBean jobOfferBean) {
	setForm(jobOfferBean);
    }

    public MultiLanguageString getEnterpriseName() {
	return getEnterprise().getName();
    }

    public MultiLanguageString getContactPerson() {
	return getEnterprise().getContactPerson();
    }

    private void setForm(JobOfferBean bean) {
	checkDates(bean.getBeginDate(), bean.getEndDate());
	setReference(bean.getReference());
	setPlace(bean.getPlace());
	setFunction(bean.getFunction());
	setJobOfferType(bean.getJobOfferType());
	// setDegree(bean.getDegree())
	setDescriptionOffer(bean.getDescriptionOffer());
	setRequirements(bean.getRequirements());
	setEmailToSubmit(getEmailToSubmit());
	setBeginDate(bean.getBeginDate());
	setEndDate(bean.getEndDate());

    }

    private void checkDates(DateTime beginDate, DateTime endDate) {
	if (beginDate.isAfter(endDate)) {
	    throw new DomainException("message.jobOffer.beginDate.isAfter.endDate", ResourceBundle.getBundle(
		    JobBankSystem.JOB_BANK_RESOURCES, Language.getLocale()));
	}
    }

    public boolean isCanCreateCandidateOffer() {
	Student student = Student.readStudent(UserView.getCurrentUser());
	if (student == null) {
	    return false;
	}
	return CandidateOffer.canCreateCandidateOffer(student, this);
    }

    public boolean isApproved() {
	return getApprovalDate() != null;
    }

    public boolean isFilled() {
	return getFilled() != null;
    }

    public void approve() {
	setApprovalDate(new DateTime());
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
	return !isCanceled();
    }

    public boolean isCandidancyPeriod() {
	DateTime now = new DateTime();
	return isApproved() && (now.isAfter(getBeginDate()) || now.equals(getBeginDate()))
		&& (now.isBefore(getEndDate()) || now.equals(getEndDate()));
    }

    public boolean isBeforeCandidancyPeriod() {
	DateTime now = new DateTime();
	return now.isBefore(getBeginDate());
    }

    public boolean isAfterCandidancyPeriod() {
	DateTime now = new DateTime();
	return now.isAfter(getEndDate());
    }

    public boolean isAfterCompletedCandidancyPeriod() {
	return isAfterCandidancyPeriod() && isApproved();
    }

    public MobilityProcessStageState getState() {
	if (isCandidancyPeriod()) {
	    return MobilityProcessStageState.UNDER_WAY;
	}
	if (isAfterCompletedCandidancyPeriod()) {
	    return MobilityProcessStageState.COMPLETED;
	}
	return MobilityProcessStageState.NOT_YET_UNDER_WAY;
    }

    public Set<CandidateOffer> getActiveCandidateOffers() {
	return getJobBankSystem().readValuesToSatisfiedPredicate(new IPredicate<CandidateOffer>() {
	    @Override
	    public boolean evaluate(CandidateOffer object) {
		return object.isActive();
	    }
	}, getCandidateOfferSet());
    }

    public static Set<JobOffer> readAllJobOffers(IPredicate<JobOffer> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getJobOffersSet());
    }

    public static String readActiveDegrees() {
	return null;
    }

}
