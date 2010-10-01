package module.jobBank.domain.beans;

import java.io.InputStream;
import java.io.Serializable;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankAccountabilityType;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;
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
    private String email;
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

    public EnterpriseBean() {
	super();
    }

    public EnterpriseBean(Enterprise enterprise) {
	super();
	setUsername(enterprise.getUser().getUsername());
	setPassword(enterprise.getUser().getPassword());
	setRepeatPassword(enterprise.getUser().getPassword());
	setNif(enterprise.getNif());
	setPhone(enterprise.getPhone());
	setFax(enterprise.getFax());
	setEmail(enterprise.getEmail());
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

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
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

    private void checkPassword() {
	if (!getPassword().equals(getRepeatPassword())) {
	    throw new DomainException("error.enterprise.password.mismatch");
	}
    }

}
