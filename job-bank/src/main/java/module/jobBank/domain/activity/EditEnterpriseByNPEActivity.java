package module.jobBank.domain.activity;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

public class EditEnterpriseByNPEActivity extends WorkflowActivity<EnterpriseProcess, EnterpriseInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	Enterprise enterprise = process.getEnterprise();
	return enterprise.isActive() && JobBankSystem.getInstance().isNPEMember(user);

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

    @Override
    public boolean isDefaultInputInterfaceUsed() {
	return false;
    }
}
