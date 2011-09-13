package module.jobBank.domain.activity;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class CancelJobOfferActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return !jobOffer.isCanceled() && !(jobOffer.isEditable() && JobBankSystem.getInstance().isNPEMember(user))
		&& (process.isProcessOwner(user) || JobBankSystem.getInstance().isNPEMember(user))
		&& (jobOffer.isPendingToApproval() || jobOffer.isUnderConstruction()) && !jobOffer.hasCandidacies();
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	activityInformation.getProcess().getJobOffer().setCanceled(true);
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public boolean isConfirmationNeeded(JobOfferProcess process) {
	return true;
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }
}
