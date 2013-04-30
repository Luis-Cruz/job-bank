package module.jobBank.domain.beans;

import java.io.InputStream;
import java.io.Serializable;

import module.jobBank.domain.EmailValidation;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseStateType;
import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.bennu.core.util.InputStreamUtil;
import pt.ist.fenixframework.Atomic;
import pt.ist.jpdafinance.pt.EconomicActivityClassificationLeaf;
import pt.utl.ist.fenix.tools.util.ByteArray;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class EnterpriseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String repeatPassword;
    private String nif;
    private String loginEmail;

    private ByteArray logo;
    private transient InputStream logoInputStream;
    private String logoFilename;
    private String logoDisplayName;

    private MultiLanguageString name;
    private EconomicActivityClassificationLeaf designation;
    private MultiLanguageString summary;

    private String contactPerson;
    private JobBankAccountabilityType jobBankAccountabilityType;
    private JobBankAccountabilityType notActiveAccountabilityType;
    private String message;

    private EnterpriseStateType enterpriseStateType;
    private EmailValidation emailValidation;

    private String privateContactEmail;
    private String publicContactEmail;
    private String phone;
    private String mobilePhone;
    private String webAddress;

    public EnterpriseBean() {
        super();
    }

    public EnterpriseBean(EmailValidation emailValidation) {
        setEmailValidation(emailValidation);
    }

    public EnterpriseBean(Enterprise enterprise) {
        super();
        setUsername(enterprise.getLoginEmail());
        setLoginEmail(enterprise.getLoginEmail());
        setName(enterprise.getName());
        setNif(enterprise.getNif());
        setDesignation(enterprise.getDesignation());
        setSummary(enterprise.getSummary());
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

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String emailLogin) {
        this.loginEmail = emailLogin;
    }

    public MultiLanguageString getName() {
        return name;
    }

    public void setName(MultiLanguageString name) {
        this.name = name;
    }

    public EconomicActivityClassificationLeaf getDesignation() {
        return designation;
    }

    public void setDesignation(EconomicActivityClassificationLeaf designation) {
        this.designation = designation;
    }

    public MultiLanguageString getSummary() {
        return summary;
    }

    public void setSummary(MultiLanguageString summary) {
        this.summary = summary;
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

    @Atomic
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
        setLoginEmail(emailValidation.getEmail());
    }

    public EmailValidation getEmailValidation() {
        return emailValidation;
    }

    @Atomic
    public EmailValidation createEmailValidation() {
        EmailValidation emailValidation = EmailValidation.getValidEmailValidationForEmail(getLoginEmail());
        return emailValidation == null ? new EmailValidation(getLoginEmail()) : emailValidation;
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

    public String getPrivateContactEmail() {
        return privateContactEmail;
    }

    public void setPrivateContactEmail(String privateContactEmail) {
        this.privateContactEmail = privateContactEmail;
    }

    public String getPublicContactEmail() {
        return publicContactEmail;
    }

    public void setPublicContactEmail(String publicContactEmail) {
        this.publicContactEmail = publicContactEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

}
