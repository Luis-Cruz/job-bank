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

public class EnterpriseRejectActivity extends WorkflowActivity<EnterpriseProcess, MessageInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	return JobBankSystem.getInstance().isNPEMember(user) && process.getEnterprise().isPendingToApproval();
    }

    @Override
    protected void process(MessageInformation activityInformation) {
	Enterprise enterprise = activityInformation.getProcess().getEnterprise();
	enterprise.reject();
	List<String> toAddresses = new ArrayList<String>();
	toAddresses.add(enterprise.getLoginEmail());
	new Email("Job Bank", "noreply@ist.utl.pt", new String[] {}, toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
		"Registration Failed - Job Bank", activityInformation.getMessage());
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
	return new MessageInformation(process, this, getBody());
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }

    private String getBody() {
	StringBuilder body = new StringBuilder();
	body.append(String.format("Your request for registration was not approved. Contact %s for more information.", BundleUtil
		.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "message.jobBank.ist")));
	body.append(String.format("\n\n\n%s", BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
		"message.jobBank.ist")));
	return body.toString();
    }
}
