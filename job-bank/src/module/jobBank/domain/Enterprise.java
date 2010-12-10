package module.jobBank.domain;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.beans.EnterpriseBean;
import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;
import module.organization.domain.UnitBean;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;

import org.apache.commons.lang.StringUtils;
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
	setUrl(enterpriseBean.getUrl());
	setContactEmail(enterpriseBean.getContactEmail());
	setContactPerson(enterpriseBean.getContactPerson());
	setLogo(enterpriseBean.getLogo());
	setAgreementForApproval(enterpriseBean.getJobBankAccountabilityType().readAccountabilityType());

    }

    @Service
    public void approve() {
	changeAgreement(getAgreementForApproval());
    }

    @Service
    public void changeAgreement(AccountabilityType accountabilityType) {
	setAgreementForApproval(null);
	closeActiveAgreement();
	Unit rootUnit = getJobBankSystem().getTopLevelUnit();
	LocalDate now = new LocalDate();
	rootUnit.addChild(getUnit(), accountabilityType, now, now.plusYears(VALID_CONTRACT));
    }

    @Service
    public void changeRequestAgreement(AccountabilityType accountabilityType) {
	setAgreementForApproval(accountabilityType);
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

    public void rejectChangeAgreement() {
	setAgreementForApproval(null);
    }

    @Override
    @Service
    public void setLogo(ByteArray logo) {
	super.setLogo(logo);
    }

    public boolean isCanceled() {
	return getCanceled();
    }

    public boolean isEnable() {
	return hasActiveAccountability() && !isBlocked();
    }

    public boolean isBlocked() {
	return getBlocked();
    }

    public boolean isDisable() {
	return hasActiveAccountability() && isBlocked();
    }

    public boolean isActive() {
	return (isJobProviderAgreement() || isJobProviderWithPrivilegesAgreement()) && !isCanceled() && !isBlocked()
		&& hasActiveAccountability();
    }

    public boolean isPendingAgreementToApprove() {
	return isAccountabilityType(JobBankAccountabilityType.PENDING.readAccountabilityType());
    }

    public boolean isJobProviderAgreement() {
	return isAccountabilityType(JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType());
    }

    public boolean isJobProviderWithPrivilegesAgreement() {
	return isAccountabilityType(JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES.readAccountabilityType());
    }

    public boolean isChangeToRequestAgreement() {
	return !isPendingAgreementToApprove() && hasAgreementForApproval();
    }

    public boolean hasActiveAccountability() {
	return getActiveAccountability() != null;
    }

    public boolean hasActiveAccountabilityType() {
	return getActiveAccountabilityType() != null;
    }

    public String getAgreement() {
	return hasActiveAccountability() ? getActiveAccountability().getDetailsString() : "";
    }

    public String getAgreementName() {
	return hasActiveAccountabilityType() ? getActiveAccountabilityType().getName().getContent() : "";
    }

    public String getAgreementDuration() {
	return hasActiveAccountability() ? String.format("%s : %s", getActiveAccountability().getBeginDate(),
		getActiveAccountability().getEndDate()) : "";
    }

    public String getAgreementNameForApproval() {
	return hasAgreementForApproval() ? getAgreementForApproval().getName().getContent() : "";
    }

    public boolean hasName() {
	return getUnit() != null && getUnit().getPartyName() != null;
    }

    public boolean hasLoginName() {
	return getUser() != null && getUser().getUsername() != null && StringUtils.isEmpty(getUser().getUsername());
    }

    public MultiLanguageString getName() {
	return hasUnit() ? getUnit().getPartyName() : null;
    }

    public String getLoginEmail() {
	return getUser() != null ? getUser().getUsername() : null;
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

    public boolean isExpired() {
	return !isActive() && !isCanceled();
    }

    public void enable() {
	setBlocked(false);
    }

    public void disable() {
	setBlocked(true);
    }

    public boolean isPendingToApproval() {
	return !isCanceled() && isPendingAgreementToApprove();
    }

    public Set<JobOfferProcess> getJobOfferProcesses() {
	final Set<JobOfferProcess> jobOfferProcesses = new HashSet<JobOfferProcess>();
	getJobBankSystem().readValuesToSatisfiedPredicate(new IPredicate<JobOffer>() {

	    @Override
	    public boolean evaluate(JobOffer jobOffer) {
		boolean valid = !jobOffer.isCanceled();
		return valid ? jobOfferProcesses.add(jobOffer.getJobOfferProcess()) : valid;

	    }
	}, getJobOfferSet());
	return jobOfferProcesses;
    }

    /* Static Methods */
    public static Enterprise readEnterprise(User user) {
	if (user != null && user.hasEnterprise()) {
	    return user.getEnterprise();
	}
	return null;
    }

    public static Enterprise readCurrentEnterprise() {
	return readEnterprise(UserView.getCurrentUser());
    }

    public static Set<Enterprise> readAllEnterprises(IPredicate<Enterprise> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getEnterprisesSet());
    }

    public static Set<Enterprise> readAllRequestToChangeEnterprises() {
	return readAllEnterprises(new IPredicate<Enterprise>() {
	    @Override
	    public boolean evaluate(Enterprise object) {
		return object.isChangeToRequestAgreement();
	    }

	});
    }

    /* Static Methods */

    public static boolean isLoginEmailAlreadyRegistered(String emailLogin) {
	for (Enterprise enterprise : JobBankSystem.getInstance().getEnterprises()) {
	    if (enterprise.getLoginEmail() != null) {
		if (enterprise.getLoginEmail().equalsIgnoreCase(emailLogin)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public static boolean isNameAlreadyRegistered(String name) {
	for (Enterprise enterprise : JobBankSystem.getInstance().getEnterprises()) {
	    if (enterprise.getName() != null) {
		if (name.equals(enterprise.getName())) {
		    return true;
		}
	    }
	}
	return false;

    }

    /* Private Methods */

    private void createUser(String email) {
	User user = new User(email);
	setUser(user);
    }

    private void closeActiveAgreement() {
	if (hasActiveAccountability()) {
	    Accountability accountability = getActiveAccountability();
	    accountability.editDates(accountability.getBeginDate(), new LocalDate());
	}
    }

    private void setFields(EnterpriseBean enterpriseBean) {
	UnitBean unitBean = new UnitBean();
	unitBean.setPartyType(PartyType.readBy(JobBankSystem.PARTY_TYPE_NAME));
	unitBean.setParent(getJobBankSystem().getTopLevelUnit());
	unitBean.setAccountabilityType(JobBankAccountabilityType.PENDING.readAccountabilityType());
	unitBean.setName(enterpriseBean.getName());
	unitBean.setAcronym(enterpriseBean.getName().getContent());
	setUnit(Unit.create(unitBean));
	createUser(enterpriseBean.getEmailValidation().getEmail());
	// Set basic agreement
	enterpriseBean.setJobBankAccountabilityType(JobBankAccountabilityType.JOB_PROVIDER);
	setForm(enterpriseBean);
	setEnterpriseProcess(new EnterpriseProcess(this));
    }

    private boolean isAccountabilityType(AccountabilityType accountabilityType) {
	if (hasActiveAccountability()) {
	    return getActiveAccountabilityType().equals(accountabilityType);
	}
	return false;
    }

    private void checks(EnterpriseBean enterpriseBean) {
	if (isLoginEmailAlreadyRegistered(enterpriseBean.getLoginEmail())) {
	    throw new DomainException("message.error.enterprise.email.already.registered", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
	if (isNameAlreadyRegistered(enterpriseBean.getName().getContent())) {
	    throw new DomainException("error.jobBank.enterprise.name.already.exists", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}

    }

}
