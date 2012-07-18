package module.jobBank.domain.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;

public class UpdateExpiredEnterpriseAgreementsTask extends UpdateExpiredEnterpriseAgreementsTask_Base {
    
    private int months;

    public  UpdateExpiredEnterpriseAgreementsTask() {
        super();
    }
    
    @Override
    public void executeTask() {

	this.months = 1;

	List<Enterprise> enterprises = JobBankSystem.getInstance().getEnterprises();

	for (Enterprise enterprise : enterprises) {
	    if (!enterprise.isCanceled() && !enterprise.isDisable() && !enterprise.isPendingAgreementToApprove()) {

		// Send a warning Email
		if (enterprise.expiresIn(months) && enterprise.isJobProviderWithPrivilegesAgreement()) {
		    sendExpireWarningEmail(enterprise);
		}

		// Send an expired Email
		else if (!enterprise.hasActiveAccountability()) {
		    JobBankAccountabilityType previousAccountability = JobBankAccountabilityType
			    .readAccountabilityType(enterprise.getLastAccountabilityType());
		    enterprise.renewAndChangeToBasicAgreement();

		    if (previousAccountability.equals(JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES)) {
			sendExpiredEmail(enterprise, previousAccountability);
		    }
		}
	    }
	}
    }

    private void sendExpireWarningEmail(Enterprise enterprise) {
	sendEmail(enterprise, getWarningEmailSubject(), buildWarningEmailBody(enterprise));
    }

    private void sendExpiredEmail(Enterprise enterprise, JobBankAccountabilityType previousAccountability) {
	sendEmail(enterprise, getRejectedEmailSubject(), buildRejectedEmailBody(enterprise, previousAccountability));
    }

    private void sendEmail(Enterprise enterprise, String emailSubject, String buildBody) {
	List<String> toAddresses = new ArrayList<String>();
	toAddresses.add(enterprise.getLoginEmail());
	final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
	new Email(virtualHost.getApplicationSubTitle().getContent(), virtualHost.getSystemEmailAddress(), new String[] {},
		toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, emailSubject, buildBody);
    }

    private String buildRejectedEmailBody(Enterprise enterprise, JobBankAccountabilityType previousAccountability) {
	StringBuilder body = new StringBuilder();
	body.append(getRejectedBody(enterprise, previousAccountability));
	body.append(getSignature());
	return body.toString();
    }

    private String buildWarningEmailBody(Enterprise enterprise) {
	StringBuilder body = new StringBuilder();
	body.append(buildWarningBody(enterprise));
	body.append(getSignature());
	return body.toString();
    }

    private Object getMessageFromBundle(String bundle, String... arguments) {
	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundle, arguments);
    }

    private Object getSignature() {
	String bundle = "message.jobbank.ist.signature";
	return getMessageFromBundle(bundle);
    }

    private Object getRejectedBody(Enterprise enterprise, JobBankAccountabilityType previousAccountability) {
	String bundle = "message.jobbank.enterprise.renew.to.basic.agreement.email.body";
	String message = (String) getMessageFromBundle(bundle, enterprise.getName().toString(),
		previousAccountability.getLocalizedName(), enterprise.getActiveAccountabilityType().getName().toString());
	return message;
    }

    private String buildWarningBody(Enterprise enterprise) {
	String bundle = "message.jobbank.enterprise.warning.expired.agreement.email.body";
	String message = (String) getMessageFromBundle(bundle, enterprise.getName().toString(), enterprise
		.getActiveAccountabilityType().getName().toString(), String.valueOf(months));
	return message;
    }

    private String getRejectedEmailSubject() {
	String bundle = "message.jobbank.message.jobbank.email.renew.basic.agreement.subject";
	return (String) getMessageFromBundle(bundle);
    }

    private String getWarningEmailSubject() {
	String bundle = "message.jobbank.message.jobbank.email.warning.expired.agreement.subject";
	String message = (String) getMessageFromBundle(bundle, String.valueOf(months));
	return message;
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
    }
}
