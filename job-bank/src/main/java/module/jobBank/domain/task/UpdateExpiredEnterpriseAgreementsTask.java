package module.jobBank.domain.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Unit;

import org.joda.time.LocalDate;

import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;

public class UpdateExpiredEnterpriseAgreementsTask extends UpdateExpiredEnterpriseAgreementsTask_Base {

    private static int monthsToExpire = 1;

    public UpdateExpiredEnterpriseAgreementsTask() {
        super();
    }

    @Override
    public void executeTask() {
        LocalDate oneMonthFromNow = new LocalDate().plusMonths(monthsToExpire);
        LocalDate tomorow = new LocalDate().plusDays(1);
        Collection<Enterprise> enterprises = JobBankSystem.getInstance().getEnterprises();
        for (Enterprise enterprise : enterprises) {
            if (!enterprise.isCanceled() && !enterprise.isDisable() && !enterprise.isPendingAgreementToApprove()) {

                if (expiresIn(enterprise, oneMonthFromNow) && enterprise.isJobProviderWithPrivilegesAgreement()) {// Send a warning Email
                    sendExpireWarningEmail(enterprise);
                } else if (expiresIn(enterprise, tomorow) || !enterprise.hasActiveAccountability()) {// renew 
                    renewAndChangeToBasicAgreement(enterprise);
                }
            }
        }
    }

    public void renewAndChangeToBasicAgreement(Enterprise enterprise) {
        Accountability lastAccountability = enterprise.getLastAccountability();
        Unit rootUnit = JobBankSystem.getInstance().getTopLevelUnit();
        AccountabilityType basicAccountability = JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType();
        rootUnit.addChild(lastAccountability.getChild(), basicAccountability, lastAccountability.getEndDate(), lastAccountability
                .getEndDate().plusYears(1));
        if (lastAccountability.getAccountabilityType().equals(
                JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES.readAccountabilityType())) {
            sendExpiredEmail(enterprise,
                    JobBankAccountabilityType.readAccountabilityType(lastAccountability.getAccountabilityType()));
        }
    }

    public boolean expiresIn(Enterprise enterprise, LocalDate expirationDate) {
        Accountability lastAccountability = enterprise.getLastAccountability();
        if (lastAccountability != null) {
            if (lastAccountability.getEndDate() != null && lastAccountability.getEndDate().isEqual(expirationDate)) {
                return true;
            }
        }
        return false;
    }

    private void sendExpireWarningEmail(Enterprise enterprise) {
        String emailSubject =
                getMessageFromBundle("message.jobbank.message.jobbank.email.warning.expired.agreement.subject",
                        String.valueOf(monthsToExpire));
        StringBuilder body = new StringBuilder();
        String message =
                getMessageFromBundle("message.jobbank.enterprise.warning.expired.agreement.email.body", enterprise.getName()
                        .toString(), enterprise.getActiveAccountabilityType().getName().toString(),
                        String.valueOf(monthsToExpire));
        body.append(message);
        body.append(getSignature());
        sendEmail(enterprise, emailSubject, body.toString());
    }

    private void sendExpiredEmail(Enterprise enterprise, JobBankAccountabilityType previousAccountability) {
        String emailSubject = getMessageFromBundle("message.jobbank.message.jobbank.email.renew.basic.agreement.subject");

        StringBuilder body = new StringBuilder();
        body.append(getMessageFromBundle("message.jobbank.enterprise.renew.to.basic.agreement.email.body", enterprise.getName()
                .toString(), previousAccountability.getLocalizedName(), enterprise.getActiveAccountabilityType().getName()
                .toString()));
        body.append(getSignature());

        sendEmail(enterprise, emailSubject, body.toString());
    }

    private void sendEmail(Enterprise enterprise, String emailSubject, String buildBody) {
        List<String> toAddresses = new ArrayList<String>();
        toAddresses.add(enterprise.getLoginEmail());
        final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
        new Email(virtualHost.getApplicationSubTitle().getContent(), virtualHost.getSystemEmailAddress(), new String[] {},
                toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, emailSubject, buildBody);
    }

    private String getMessageFromBundle(String bundle, String... arguments) {
        return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundle, arguments);
    }

    private String getSignature() {
        return getMessageFromBundle("message.jobbank.ist.signature");
    }

    @Override
    public String getLocalizedName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
    }
}
