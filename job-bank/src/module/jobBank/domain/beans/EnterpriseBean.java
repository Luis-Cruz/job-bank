package module.jobBank.domain.beans;

import java.io.InputStream;
import java.io.Serializable;

import module.jobBank.domain.EmailValidation;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseStateType;
import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;
import myorg.util.InputStreamUtil;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class EnterpriseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String repeatPassword;
    private String nif;
    private String phone;
    private String fax;
    private String url;
    private String loginEmail;
    private String contactEmail;
    private String areaCode;

    private ByteArray logo;
    private transient InputStream logoInputStream;
    private String logoFilename;
    private String logoDisplayName;

    private MultiLanguageString name;
    private MultiLanguageString designation;
    private MultiLanguageString summary;
    private String address;
    private String area;
    private String contactPerson;
    private JobBankAccountabilityType jobBankAccountabilityType;
    private JobBankAccountabilityType notActiveAccountabilityType;
    private String message;

    private EnterpriseStateType enterpriseStateType;
    private EmailValidation emailValidation;

    public EnterpriseBean() {
	super();
    }

    public EnterpriseBean(EmailValidation emailValidation) {
	setEmailValidation(emailValidation);
    }

    public EnterpriseBean(Enterprise enterprise) {
	super();
	setUsername(enterprise.getLoginEmail());
	setPassword(enterprise.getUser().getPassword());
	setRepeatPassword(enterprise.getUser().getPassword());
	setNif(enterprise.getNif());
	setPhone(enterprise.getPhone());
	setFax(enterprise.getFax());
	setUrl(enterprise.getUrl());
	setLoginEmail(enterprise.getLoginEmail());
	setContactEmail(enterprise.getContactEmail());
	setAreaCode(enterprise.getAreaCode());
	setName(enterprise.getName());
	setDesignation(enterprise.getDesignation());
	setSummary(enterprise.getSummary());
	setAddress(enterprise.getAddress());
	setArea(enterprise.getArea());
	setContactPerson(enterprise.getContactPerson());
	setLogo(enterprise.getLogo());
	setJobBankAccountabilityType(JobBankAccountabilityType.readAccountabilityType(enterprise.getActiveAccountabilityType()));
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getRepeatPassword() {
	return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
	this.repeatPassword = repeatPassword;
    }

    public String getNif() {
	return nif;
    }

    public void setNif(String nif) {
	this.nif = nif;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getLoginEmail() {
	return loginEmail;
    }

    public void setLoginEmail(String emailLogin) {
	this.loginEmail = emailLogin;
    }

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public MultiLanguageString getName() {
	return name;
    }

    public void setName(MultiLanguageString name) {
	this.name = name;
    }

    public MultiLanguageString getDesignation() {
	return designation;
    }

    public void setDesignation(MultiLanguageString designation) {
	this.designation = designation;
    }

    public MultiLanguageString getSummary() {
	return summary;
    }

    public void setSummary(MultiLanguageString summary) {
	this.summary = summary;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getArea() {
	return area;
    }

    public void setArea(String area) {
	this.area = area;
    }

    public String getContactPerson() {
	return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
    }

    public JobBankAccountabilityType getJobBankAccountabilityType() {
	return jobBankAccountabilityType;
    }

    public void setJobBankAccountabilityType(JobBankAccountabilityType jobBankAccountabilityType) {
	this.jobBankAccountabilityType = jobBankAccountabilityType;
    }

    @Service
    public Enterprise create() {
	checkPassword();
	return new Enterprise(this);
    }

    public void setLogo(ByteArray logo) {
	this.logo = logo;
    }

    public ByteArray getLogo() {
	if (getLogoInputStream() != null) {
	    setLogo(new ByteArray(InputStreamUtil.consumeInputStream(getLogoInputStream())));
	    setLogoInputStream(null);
	}
	return logo;
    }

    public void setLogoInputStream(InputStream logoInputStream) {
	this.logoInputStream = logoInputStream;
    }

    public InputStream getLogoInputStream() {
	return logoInputStream;
    }

    public void setLogoFilename(String logoFilename) {
	this.logoFilename = logoFilename;
    }

    public String getLogoFilename() {
	return logoFilename;
    }

    public void setLogoDisplayName(String logoDisplayName) {
	this.logoDisplayName = logoDisplayName;
    }

    public String getLogoDisplayName() {
	return logoDisplayName;
    }

    public boolean hasInputStreamLogo() {
	// TODO Auto-generated method stub
	return getLogoInputStream() != null;
    }

    public void checkPassword() {
	if (!getPassword().equals(getRepeatPassword())) {
	    throw new DomainException("error.enterprise.password.mismatch",
		    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
    }

    public void setEnterpriseStateType(EnterpriseStateType enterpriseStateType) {
	this.enterpriseStateType = enterpriseStateType;
    }

    public EnterpriseStateType getEnterpriseStateType() {
	return enterpriseStateType;
    }

    public void setEmailValidation(EmailValidation emailValidation) {
	this.emailValidation = emailValidation;
    }

    public EmailValidation getEmailValidation() {
	return emailValidation;
    }

    @Service
    public EmailValidation createEmailValidation() {
	return new EmailValidation(getLoginEmail());
    }

    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }

    public String getContactEmail() {
	return contactEmail;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getUrl() {
	return url;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getMessage() {
	return message;
    }

    public void setNotActiveAccountabilityType(JobBankAccountabilityType notActiveAccountabilityType) {
	this.notActiveAccountabilityType = notActiveAccountabilityType;
    }

    public JobBankAccountabilityType getNotActiveAccountabilityType() {
	if (jobBankAccountabilityType.equals(JobBankAccountabilityType.JOB_PROVIDER)) {
	    notActiveAccountabilityType = JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES;
	} else {
	    notActiveAccountabilityType = JobBankAccountabilityType.JOB_PROVIDER;
	}
	return notActiveAccountabilityType;
    }

}
