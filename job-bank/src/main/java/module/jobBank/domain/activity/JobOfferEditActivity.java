package module.jobBank.domain.activity;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

public class JobOfferEditActivity extends WorkflowActivity<JobOfferProcess, JobOfferInformation> {

	@Override
	public boolean isActive(JobOfferProcess process, User user) {
		JobOffer jobOffer = process.getJobOffer();
		return jobOffer.isActive() && jobOffer.isEditable() && process.isProcessOwner(user);
	}

	@Override
	protected void process(JobOfferInformation activityInformation) {
		activityInformation.getProcess().getJobOffer().edit(activityInformation.getJobOfferBean());
	}

	@Override
	public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
		return new JobOfferInformation(process, this);
	}

	@Override
	public String getUsedBundle() {
		return JobBankSystem.JOB_BANK_RESOURCES;
	}

	@Override
	public boolean isDefaultInputInterfaceUsed() {
		return false;
	}
}
