package module.jobBank.domain.activity;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

import pt.ist.bennu.core.domain.User;

public class SubmitJobOfferForApprovalActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

	@Override
	public boolean isActive(JobOfferProcess process, User user) {
		JobOffer jobOffer = process.getJobOffer();
		return jobOffer.isActive() && jobOffer.isEditable()
				&& (process.isProcessOwner(user) || JobBankSystem.getInstance().isNPEMember(user));
	}

	@Override
	protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
		activityInformation.getProcess().getJobOffer().setSubmittedForApprovalDate(new DateTime());
	}

	@Override
	public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
		return new ActivityInformation(process, this);
	}

	@Override
	public String getUsedBundle() {
		return JobBankSystem.JOB_BANK_RESOURCES;
	}
}
