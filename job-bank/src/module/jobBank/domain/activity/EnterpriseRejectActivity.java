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
import pt.ist.emailNotifier.domain.Email;

public class EnterpriseRejectActivity extends WorkflowActivity<EnterpriseProcess, ActivityInformation<EnterpriseProcess>> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
	return JobBankSystem.getInstance().isNPEMember(user) && process.getEnterprise().isPendingToApproval();
    }

    @Override
    protected void process(ActivityInformation<EnterpriseProcess> activityInformation) {
	Enterprise enterprise = activityInformation.getProcess().getEnterprise();
	enterprise.reject();
	List<String> toAddresses = new ArrayList<String>();
	toAddresses.add(enterprise.getLoginEmail());
	new Email("Job Bank", "noreply@ist.utl.pt", new String[] {}, toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
		"Registration Failed - Job Bank", getBody());
    }

    private String getBody() {
	StringBuilder body = new StringBuilder();
	body
		.append("Your request for registration was not approved. Contact Núcleo Parcerias Empresariais for more information.\n Thanks ");
	body.append(String.format("\n\n\n Instituto Superior Técnico"));
	return body.toString();
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
