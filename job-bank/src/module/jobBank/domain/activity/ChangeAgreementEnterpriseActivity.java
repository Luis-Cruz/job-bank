package module.jobBank.domain.activity;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class ChangeAgreementEnterpriseActivity extends WorkflowActivity<EnterpriseProcess, EnterpriseContractInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	Enterprise enterprise = process.getEnterprise();
	return process.isProcessOwner(user) && !enterprise.isPendingAgreementToApprove();

    }

    @Override
    protected void process(EnterpriseContractInformation activityInformation) {
	activityInformation.getProcess().getEnterprise().changeRequestAgreement(
		activityInformation.getEnterpriseBean().getJobBankAccountabilityType().readAccountabilityType());
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
	return new EnterpriseContractInformation(process, this);
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
