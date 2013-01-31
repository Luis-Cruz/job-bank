package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;

public class ApproveOrRejectEnterpriseChangeAgreementActivity extends
		WorkflowActivity<EnterpriseProcess, EnterpriseAgreementApprovalInformation> {

	@Override
	public boolean isActive(EnterpriseProcess process, User user) {
		Enterprise enterprise = process.getEnterprise();
		return JobBankSystem.getInstance().isNPEMember(user) && enterprise.hasAgreementForApproval()
				&& enterprise.hasBeenAcceptedBefore();
	}

	@Override
	protected void process(EnterpriseAgreementApprovalInformation activityInformation) {
		Enterprise enterprise = activityInformation.getProcess().getEnterprise();

		if (activityInformation.isApprove()) {
			enterprise.approve();
		} else {
			enterprise.rejectChangeAgreement();
		}

		sendEmail(enterprise, activityInformation);
	}

	private void sendEmail(Enterprise enterprise, EnterpriseAgreementApprovalInformation eaai) {
		List<String> toAddresses = new ArrayList<String>();
		toAddresses.add(enterprise.getLoginEmail());
		JobBankSystem jobBankSystem = JobBankSystem.getInstance();
		new Email(jobBankSystem.getEmailValidationFromName(), jobBankSystem.getEmailValidationFromEmail(), new String[] {},
				toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, getEmailSubject(eaai), eaai.getMessage());
	}

	private String getEmailSubject(EnterpriseAgreementApprovalInformation eaai) {
		String bundle = "message.jobbank.message.jobbank.email.agreement.approval.subject";
		if (!eaai.isApprove()) {
			bundle = "message.jobbank.message.jobbank.email.agreement.rejection.subject";
		}
		String message =
				BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundle, eaai.getNewAgreement()
						.getLocalizedName());
		return message;
	}

	@Override
	public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
		Enterprise enterprise = process.getEnterprise();
		return new EnterpriseAgreementApprovalInformation(process, this, enterprise.getName(),
				enterprise.getAgreementForApproval());
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
