package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import myorg.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;

public class ApproveOrRejectEnterpriseActivity extends WorkflowActivity<EnterpriseProcess, EnterpriseApprovalInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	Enterprise enterprise = process.getEnterprise();
	return JobBankSystem.getInstance().isNPEMember(user) && enterprise.isPendingAgreementToApprove()
		&& !enterprise.isCanceled();
    }

    @Override
    protected void process(EnterpriseApprovalInformation activityInformation) {
	Enterprise enterprise = activityInformation.getProcess().getEnterprise();

	if (activityInformation.isApprove()) {
	    enterprise.acceptRegister();
	} else {
	    enterprise.reject();
	}

	sendEmail(enterprise, activityInformation);
    }

    private void sendEmail(Enterprise enterprise, EnterpriseApprovalInformation eai) {
	List<String> toAddresses = new ArrayList<String>();
	toAddresses.add(enterprise.getLoginEmail());
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	new Email(jobBankSystem.getEmailValidationFromName(), jobBankSystem.getEmailValidationFromEmail(), new String[] {},
		toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, getEmailSubject(eai), eai.getMessage());
    }

    private String getEmailSubject(EnterpriseApprovalInformation eai) {
	String bundle = "message.jobbank.message.jobbank.email.approval.subject";
	if (!eai.isApprove()) {
	    bundle = "message.jobbank.message.jobbank.email.rejection.subject";
	}
	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundle);
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
	return new EnterpriseApprovalInformation(process, this, process.getEnterprise().getName());
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
