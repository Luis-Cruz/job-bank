package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class EditEnterpriseActivity extends WorkflowActivity<EnterpriseProcess, EnterpriseInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	return process.isProcessOwner(user);

    }

    @Override
    protected void process(EnterpriseInformation activityInformation) {
	activityInformation.getProcess().getEnterprise().edit(activityInformation.getEnterpriseBean());
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
	return new EnterpriseInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }
}
