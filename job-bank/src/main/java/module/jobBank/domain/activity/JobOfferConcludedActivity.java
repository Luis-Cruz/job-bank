package module.jobBank.domain.activity;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;

public class JobOfferConcludedActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

	@Override
	public boolean isActive(JobOfferProcess process, User user) {
		JobOffer jobOffer = process.getJobOffer();
		return jobOffer.isActive() && jobOffer.isSelectionPeriod()
				&& (process.isProcessOwner(user) || JobBankSystem.getInstance().isNPEMember(user));
	}

	@Override
	protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
		activityInformation.getProcess().getJobOffer().conclued();
	}

	@Override
	public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
		return new ActivityInformation(process, this);
	}

	@Override
	public String getUsedBundle() {
		return JobBankSystem.JOB_BANK_RESOURCES;
	}

	@Override
	public boolean isConfirmationNeeded(JobOfferProcess process) {
		return true;
	}

	@Override
	public String getLocalizedConfirmationMessage(JobOfferProcess process) {
		JobOffer jobOffer = process.getJobOffer();
		int selected = jobOffer.getVacancies() - jobOffer.getNumberOfFreeVacancies();
		int toBeSelected = jobOffer.getActiveOfferCandidacies().size() - selected;

		if (jobOffer.getNumberOfFreeVacancies() > 0 && toBeSelected > 0) {
			return BundleUtil.getFormattedStringFromResourceBundle(getUsedBundle(),
					"activity.confirmation.module.jobBank.domain.activity.JobOfferConcludedActivity.invalid");
		} else {
			return BundleUtil.getFormattedStringFromResourceBundle(getUsedBundle(),
					"activity.confirmation.module.jobBank.domain.activity.JobOfferConcludedActivity.valid");
		}
	}

}
