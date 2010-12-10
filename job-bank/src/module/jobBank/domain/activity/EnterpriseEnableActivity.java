package module.jobBank.domain.activity;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class EnterpriseEnableActivity extends WorkflowActivity<EnterpriseProcess, ActivityInformation<EnterpriseProcess>> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	return process.getEnterprise().isDisable() && JobBankSystem.getInstance().isNPEMember(user);
    }

    @Override
    protected void process(ActivityInformation<EnterpriseProcess> activityInformation) {
	Enterprise enterprise = activityInformation.getProcess().getEnterprise();
	enterprise.enable();
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }
}