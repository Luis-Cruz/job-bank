package module.jobBank.domain.activity;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.LocalDate;

import pt.ist.bennu.core.domain.User;

public class AcceptTermsOfResponsibilityActivity extends
	WorkflowActivity<EnterpriseProcess, ActivityInformation<EnterpriseProcess>> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	Enterprise enterprise = process.getEnterprise();
	return process.isProcessOwner(user) && !enterprise.getAcceptedTermsOfResponsibilityForCurrentYear();
    }

    @Override
    protected void process(ActivityInformation<EnterpriseProcess> activityInformation) {
	activityInformation.getProcess().getEnterprise().setAcceptedTermsOfResponsibilityForCurrentYear(true);
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }

    @Override
    protected boolean shouldLogActivity(ActivityInformation<EnterpriseProcess> activityInformation) {
	// TODO Auto-generated method stub
	return super.shouldLogActivity(activityInformation);
    }

    @Override
    protected String[] getArgumentsDescription(ActivityInformation<EnterpriseProcess> activityInformation) {
	return new String[] { new Integer(new LocalDate().getYear()).toString() };
    }
}
