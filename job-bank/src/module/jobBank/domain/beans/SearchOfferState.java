package module.jobBank.domain.beans;

import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferState;
import module.jobBank.domain.utils.IPredicate;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.util.Search;

import org.apache.commons.lang.StringUtils;

import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class SearchOfferState extends Search<JobOfferProcess> {

    private JobOfferState jobOfferState;
    private String processNumber;
    private String enterprise;
    private int processesCount;

    public SearchOfferState() {
	init();
    }

    public void init() {
	setInitialState();
    }

    private void setInitialState() {
	if (JobBankSystem.getInstance().isNPEMember()) {
	    setJobOfferState(JobOfferState.WAITING_FOR_APPROVAL);
	} else {
	    setJobOfferState(JobOfferState.UNDER_SELECTION);
	}
    }

    public String getProcessNumber() {
	return processNumber;
    }

    public void setProcessNumber(String processNumber) {
	this.processNumber = processNumber;
    }

    public void setEnterprise(String enterprise) {
	this.enterprise = enterprise;
    }

    public String getEnterprise() {
	return enterprise;
    }

    private boolean isSatisfiedEnterprise(JobOffer offer) {
	return StringUtils.isEmpty(getEnterprise()) || offer.getEnterpriseName() != null
		&& StringNormalizer.normalize(offer.getEnterpriseName().getContent()).contains(
			StringNormalizer.normalize(getEnterprise()));
    }

    private boolean isSatisfiedUser(JobOfferProcess proc) {
	return proc.getCanManageJobProcess();
    }

    private boolean isSatisfiedProcessNumber(JobOffer offer) {
	return StringUtils.isEmpty(getProcessNumber())
		|| offer.getJobOfferProcess().getProcessIdentification().contains(getProcessNumber());
    }

    private boolean isSatisfiedState(JobOffer jobOffer, User user) {
	return isJobOfferAll(jobOffer) || isJobOfferUnderConstruction(jobOffer) || isJobOfferWaitingForApproval(jobOffer)
		|| isJobOfferAproved(jobOffer) || isJobOfferPublished(jobOffer) || isJobOfferUnderSelection(jobOffer)
		|| isJobOfferArchived(jobOffer) || isJobOfferCanceled(jobOffer);
    }

    private boolean isJobOfferAll(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.ALL) && !jobOffer.isUnderConstruction();
    }

    private boolean isJobOfferUnderConstruction(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.UNDER_CONSTRUCTION) && jobOffer.isUnderConstruction();
    }

    private boolean isJobOfferCanceled(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.CANCELED) && jobOffer.isCanceled();
    }

    private boolean isJobOfferArchived(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.ARCHIVED) && jobOffer.isArchived();
    }

    private boolean isJobOfferUnderSelection(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.UNDER_SELECTION) && jobOffer.isUnderSelection();
    }

    private boolean isJobOfferPublished(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.PUBLISHED) && jobOffer.isPublished();
    }

    private boolean isJobOfferAproved(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.APPROVED) && jobOffer.isApprovedAndOnlyApproved();
    }

    private boolean isJobOfferWaitingForApproval(JobOffer jobOffer) {
	return getJobOfferState().equals(JobOfferState.WAITING_FOR_APPROVAL) && jobOffer.isWaitingForApproval();
    }

    @Override
    public Set<JobOfferProcess> search() {
	final User user = UserView.getCurrentUser();
	final Set<JobOfferProcess> jobOfferProcesses = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer jobOffer = object.getJobOffer();
		return isSatisfiedUser(object) && isSatisfiedState(jobOffer, user) && isSatisfiedProcessNumber(jobOffer)
			&& isSatisfiedEnterprise(jobOffer);
	    }
	});
	return jobOfferProcesses;
    }

    public void setJobOfferState(JobOfferState jobOfferState) {
	this.jobOfferState = jobOfferState;
    }

    public JobOfferState getJobOfferState() {
	return jobOfferState;
    }

    public void setProcessesCount(int processesCount) {
	this.processesCount = processesCount;
    }

    public int getProcessesCount() {
	return processesCount;
    }

}
