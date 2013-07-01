package module.jobBank.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;

public class EmailValidation extends EmailValidation_Base {

    public EmailValidation() {
        super();
        setJobBankSystem(JobBankSystem.getInstance());
    }

    public EmailValidation(String emailToValidate) {
        super();
        setJobBankSystem(JobBankSystem.getInstance());
        setEmail(emailToValidate);
        generateValidation(emailToValidate);
    }

    public String generateChecksum() {
        DateTime now = new DateTime();
        StringBuilder checksumCode = new StringBuilder();
        checksumCode.append(hashCode());
        checksumCode.append(now.getMillis());
        return (DigestUtils.shaHex(checksumCode.toString()));
    }

    public Boolean isValidChecksum(String checksum) {
        return checksum != null && !isExpired() && checksum.equals(getChecksum());
    }

    public boolean isExpired() {
        return getExpiredDate().isBeforeNow();
    }

    public boolean isEmailAlreadyValidated(String checksum) {
        return Enterprise.readEnterpriseByEmailLogin(getEmail()) != null;
    }

    private void generateValidation(String emailToValidate) {
        setExpiredDate(generateExpiredDate());
        setChecksum(generateChecksum());
        List<String> toAddress = new LinkedList<String>();
        toAddress.add(emailToValidate);
        final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
        new Email(getJobBankSystem().getEmailValidationFromName(), getJobBankSystem().getEmailValidationFromEmail(),
                new String[] {}, toAddress, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
                BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                        "message.enterprise.emailValidation.subject"), getBody());
    }

    public String getBody() {
        StringBuilder body = new StringBuilder();
        body.append(String.format("%s \n\n", BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                "message.enterprise.emailValidation.body")));
        body.append(String.format("%s&checkEmail=%s&OID=%s", getJobBankSystem().getUrlEmailValidation(), getChecksum(),
                getExternalId()));
        body.append(String.format("\n\n\n %s",
                BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "message.jobBank.ist")));
        return body.toString();
    }

    private DateTime generateExpiredDate() {
        return new DateTime().plusDays(1);
    }

    public static EmailValidation getValidEmailValidationForEmail(String email) {
        for (EmailValidation emailValidation : JobBankSystem.getInstance().getEmailValidations()) {
            if (emailValidation.getEmail() != null && emailValidation.getEmail().equalsIgnoreCase(email)
                    && !emailValidation.isExpired()) {
                return emailValidation;
            }
        }
        return null;
    }

    @Deprecated
    public boolean hasExpiredDate() {
        return getExpiredDate() != null;
    }

    @Deprecated
    public boolean hasChecksum() {
        return getChecksum() != null;
    }

    @Deprecated
    public boolean hasEmail() {
        return getEmail() != null;
    }

    @Deprecated
    public boolean hasJobBankSystem() {
        return getJobBankSystem() != null;
    }

}
