package module.jobBank.domain;

import java.util.Set;

import module.jobBank.domain.beans.EnterpriseBean;
import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;
import module.organization.domain.UnitBean;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Enterprise extends Enterprise_Base {

    // By default the accountabilityType is valid for one year
    private final int VALID_CONTRACT = 1;

    protected Enterprise() {
	super();
	setJobBankSystem(JobBankSystem.getInstance());
    }

    public Enterprise(EnterpriseBean enterpriseBean) {
	this();
	checks(enterpriseBean);
	setFields(enterpriseBean);

    }

    @Service
    public void approve() {
	changeAgreement(getAccountabilityTypeForApproval());

    }

    @Service
    public void changeAgreement(AccountabilityType accountabilityType) {
	setAccountabilityTypeForApproval(null);
	closeActiveAgreement();
	Unit rootUnit = getJobBankSystem().getTopLevelUnit();
	LocalDate now = new LocalDate();
	rootUnit.addChild(getUnit(), accountabilityType, now, now.plusYears(VALID_CONTRACT));
    }

    @Service
    public void changeRequestAgreement(AccountabilityType accountabilityType) {
	if (!isChangeToRequestAgreement()) {
	    setAccountabilityTypeForApproval(accountabilityType);
	} else {
	    throw new DomainException("message.error.enterprise.pending.to.approve.change.request.agreement", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
    }

    @Service
    public void renewContract() {
	changeAgreement(getActiveAccountabilityType());
    }

    @Service
    public void setName(MultiLanguageString enterpriseName) {
	getUnit().setPartyName(enterpriseName);
    }

    @Service
    public void reject() {
	setCanceled(true);
    }

    @Override
    @Service
    public void setLogo(ByteArray logo) {
	super.setLogo(logo);
    }

    public boolean isCanceled() {
	return getCanceled();
    }

    public boolean isActive() {
	return (isJobProviderAccountabilityType() || isJobProviderWithPrivilegesAccountabilityType()) && !isCanceled()
		&& hasActiveAccountability();
    }

    public boolean isPendingAccountabilityType() {
	return isAccountabilityType(JobBankAccountabilityType.PENDING.readAccountabilityType());
    }

    public boolean isJobProviderAccountabilityType() {
	return isAccountabilityType(JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType());
    }

    public boolean isJobProviderWithPrivilegesAccountabilityType() {
	return isAccountabilityType(JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES.readAccountabilityType());
    }

    public boolean isChangeToRequestAgreement() {
	return !isPendingAccountabilityType() && hasAccountabilityTypeForApproval();
    }

    public Accountability getActiveAccountability() {
	for (Accountability accountability : getUnit().getParentAccountabilities()) {
	    if (accountability.isActiveNow()) {
		return accountability;
	    }
	}
	return null;
    }

    public AccountabilityType getActiveAccountabilityType() {
	if (hasActiveAccountability())
	    return getActiveAccountability().getAccountabilityType();
	return null;
    }

    public boolean hasActiveAccountability() {
	return getActiveAccountability() != null;
    }

    public String getAgreement() {
	return getActiveAccountability().getDetailsString();
    }

    public boolean hasName() {
	return getUnit() != null && getUnit().getPartyName() != null;
    }

    public MultiLanguageString getName() {
	return getUnit().getPartyName();
    }

    private boolean isAccountabilityType(AccountabilityType accountabilityType) {
	if (hasActiveAccountability()) {
	    return getActiveAccountabilityType().equals(accountabilityType);
	}
	return false;
    }

    public String generateUsername() {
	return String.format("e%s", getJobBankSystem().nextNumber());
    }

    @Service
    public void edit(EnterpriseBean enterpriseBean) {
	setForm(enterpriseBean);
    }

    @Service
    public void setForm(EnterpriseBean enterpriseBean) {
	getUser().setPassword(enterpriseBean.getPassword());
	setNif(enterpriseBean.getNif());
	setDesignation(enterpriseBean.getDesignation());
	setSummary(enterpriseBean.getSummary());
	setArea(enterpriseBean.getArea());
	setAreaCode(enterpriseBean.getAreaCode());
	setPhone(enterpriseBean.getPhone());
	setFax(enterpriseBean.getFax());
	setEmail(enterpriseBean.getEmail());
	setContactPerson(enterpriseBean.getContactPerson());
	setLogo(enterpriseBean.getLogo());
	setAccountabilityTypeForApproval(enterpriseBean.getJobBankAccountabilityType().readAccountabilityType());

    }

    public boolean isPendingToApproval() {
	return !isCanceled() && isPendingAccountabilityType() || isChangeToRequestAgreement();
    }

    /* Static Methods */

    public static Enterprise readEnterprise(User user) {
	if (user != null && user.hasEnterprise()) {
	    return user.getEnterprise();
	}
	return null;
    }

    public static Set<Enterprise> readAllEnterprises(IPredicate<Enterprise> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getEnterprisesSet());
    }

    public static Set<Enterprise> readAllActiveEnterprises() {
	return readAllEnterprises(new IPredicate<Enterprise>() {
	    @Override
	    public boolean evaluate(Enterprise object) {
		return object.isActive();
	    }

	});
    }

    public static Set<Enterprise> readAllRequestToChangeEnterprises() {
	return readAllEnterprises(new IPredicate<Enterprise>() {
	    @Override
	    public boolean evaluate(Enterprise object) {
		return object.isChangeToRequestAgreement();
	    }

	});
    }

    /* Private Methods */

    private void createUser() {
	String username = generateUsername();
	User user = new User(username);
	setUser(user);
    }

    private void closeActiveAgreement() {
	Accountability accountability = getActiveAccountability();
	accountability.editDates(accountability.getBeginDate(), new LocalDate());
    }

    private void setFields(EnterpriseBean enterpriseBean) {
	UnitBean unitBean = new UnitBean();
	unitBean.setPartyType(PartyType.readBy(JobBankSystem.PARTY_TYPE_NAME));
	unitBean.setParent(getJobBankSystem().getTopLevelUnit());
	unitBean.setAccountabilityType(JobBankAccountabilityType.PENDING.readAccountabilityType());
	unitBean.setName(enterpriseBean.getName());
	unitBean.setAcronym(enterpriseBean.getName().getContent());
	setUnit(Unit.create(unitBean));

	createUser();
	setForm(enterpriseBean);
	new EnterpriseProcess(this);
    }

    private void checks(EnterpriseBean enterpriseBean) {
	/*
	 * for (Enterprise enterprise :
	 * JobBankSystem.getInstance().getEnterprise()) { if
	 * (enterprise.hasName() &&
	 * enterprise.getName().equals(enterpriseBean.getName())) { throw new
	 * DomainException("error.jobBank.enterprise.name.already.exists",
	 * ResourceBundle.getBundle( JobBankSystem.JOB_BANK_RESOURCES,
	 * Language.getLocale())); } }
	 */
    }

}
