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
import myorg.domain.VirtualHost;
import pt.ist.emailNotifier.domain.Email;

public class EnterpriseApprovalActivity extends WorkflowActivity<EnterpriseProcess, MessageInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	return JobBankSystem.getInstance().isNPEMember(user) && process.getEnterprise().isPendingToApproval();
    }

    @Override
    protected void process(MessageInformation activityInformation) {
	Enterprise enterprise = activityInformation.getProcess().getEnterprise();
	enterprise.acceptRegister();
	List<String> toAddresses = new ArrayList<String>();
	toAddresses.add(enterprise.getLoginEmail());
	final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
	new Email(virtualHost.getApplicationSubTitle().getContent(),
		    virtualHost.getSystemEmailAddress(), new String[] {}, toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
		"Registration Successfully - Job Bank", activityInformation.getMessage());
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
	body.append("Welcome to Job Bank. Your registration was successfully approved");
	body.append(String.format("\n\n\n---\nInstituto Superior Tecnico"));
	return body.toString();
    }
}
