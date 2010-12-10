package module.jobBank.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

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
	DateTime now = new DateTime();
	return getExpiredDate().compareTo(now) > 0 ? false : true;
    }

    private void generateValidation(String emailToValidate) {
	setExpiredDate(generateExpiredDate());
	setChecksum(generateChecksum());
	List<String> toAddress = new LinkedList<String>();
	toAddress.add(emailToValidate);
	new Email("Job Bank", "noreply@ist.utl.pt", new String[] {}, toAddress, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
		"Email Validation - Job Bank", getBody(getChecksum()));
    }

    private String getBody(String checksum) {
	StringBuilder body = new StringBuilder();
	body.append("Need to validate your e-mail. Click the URL below \n\n");
	body.append(String.format("%s&checkEmail=%s&OID=%s", getJobBankSystem().getUrlEmailValidation(), checksum,
		getExternalId()));
	body.append(String.format("\n\n\n Instituto Superior Técnico"));
	return body.toString();
    }

    private DateTime generateExpiredDate() {
	return new DateTime().plusMinutes(30);
    }

}
