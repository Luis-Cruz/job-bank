package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import module.contacts.domain.ContactsConfigurator;
import module.contacts.domain.EmailAddress;
import module.contacts.domain.PartyContact;
import module.contacts.domain.PartyContactType;
import module.contacts.domain.Phone;
import module.contacts.domain.PhoneType;
import module.contacts.domain.WebAddress;
import module.jobBank.domain.beans.EnterpriseBean;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Party;
import module.organization.domain.PartyType;
import module.organization.domain.Unit;
import module.organization.domain.UnitBean;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.PasswordRecoveryRequest;
import myorg.domain.User;
import myorg.domain.VirtualHost;
import myorg.domain.exceptions.DomainException;
import myorg.domain.groups.PersistentGroup;
import myorg.domain.util.ByteArray;
import myorg.util.BundleUtil;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.emailNotifier.domain.Email;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.StringNormalizer;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Enterprise extends Enterprise_Base {

    public static final Comparator<Enterprise> COMPARATOR_BY_ENTERPRISE_STATE_AND_NAME = new Comparator<Enterprise>() {

	@Override
	public int compare(Enterprise o1, Enterprise o2) {
	    final String n1 = o1.getName().getContent();
	    final String n2 = o2.getName().getContent();
	    final int stateComparation = o1.getEnterpriseState().compareto(o2.getEnterpriseState());

	    return (stateComparation == 0) ? StringNormalizer.normalize(n1).compareTo(StringNormalizer.normalize(n2))
		    : stateComparation;
	}

    };

    // By default the accountabilityType is valid for one year
    private final int VALID_CONTRACT = 1;

    public Enterprise(EnterpriseBean enterpriseBean) {
	super();
	checks(enterpriseBean);
	setFields(enterpriseBean);
    }

    @Service
    public void edit(EnterpriseBean enterpriseBean) {
	setForm(enterpriseBean);
    }

    @Service
    public void setForm(EnterpriseBean enterpriseBean) {
	if (!getUser().matchesPassword(enterpriseBean.getPassword())) {
	    getUser().setPassword(enterpriseBean.getPassword());
	}
	setName(enterpriseBean.getName());
	setNif(enterpriseBean.getNif());
	setDesignation(enterpriseBean.getDesignation());
	setSummary(enterpriseBean.getSummary());
	setContactPerson(enterpriseBean.getContactPerson());
	setLogo(enterpriseBean.getLogo());
    }

    @Service
    public void approve() {
	if (getAgreementForApproval() != null) {
	    changeAgreement(getAgreementForApproval());
	}
    }

    @Service
    public void acceptRegister() {
	// Only get the first!! -> Registration
	if (getUnit().getParentAccountabilities().size() == 1) {
	    LocalDate now = new LocalDate();
	    Accountability registerRequest = getActiveAccountability();
	    registerRequest.editDates(registerRequest.getBeginDate(), now);
	    // create a new (basic) contract!
	    Unit rootUnit = getJobBankSystem().getTopLevelUnit();
	    AccountabilityType basicAccountability = JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType();
	    rootUnit.addChild(getUnit(), basicAccountability, now, now.plusYears(VALID_CONTRACT));
	    setAgreementForApproval(null);
	}
    }

    @Service
    public void changeAgreement(AccountabilityType accountabilityType) {
	closeActiveAgreement();
	Unit rootUnit = getJobBankSystem().getTopLevelUnit();
	LocalDate now = new LocalDate();
	rootUnit.addChild(getUnit(), accountabilityType, now, now.plusYears(VALID_CONTRACT));
	setAgreementForApproval(null);
    }

    @Service
    public void changeRequestAgreement(AccountabilityType accountabilityType) {
	if (!accountabilityType.equals(getActiveAccountability().getAccountabilityType())) {
	    setAgreementForApproval(accountabilityType);
	} else {
	    setAgreementForApproval(null);
	}
    }

    @Service
    public Boolean changeRequestAgreementByNPE(AccountabilityType accountabilityType) {
	if (accountabilityType != null && !getActiveAccountability().getAccountabilityType().equals(accountabilityType)) {
	    LocalDate now = new LocalDate();
	    Accountability registerRequest = getActiveAccountability();
	    registerRequest.editDates(registerRequest.getBeginDate(), now);
	    Unit rootUnit = getJobBankSystem().getTopLevelUnit();
	    rootUnit.addChild(getUnit(), accountabilityType, now, now.plusYears(VALID_CONTRACT));
	    return true;
	}
	return false;
    }

    @Service
    public void renewAndChangeToBasicAgreement() {
	if (!hasActiveAccountability()) {
	    LocalDate now = new LocalDate();
	    Unit rootUnit = getJobBankSystem().getTopLevelUnit();
	    AccountabilityType basicAccountability = JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType();
	    rootUnit.addChild(getUnit(), basicAccountability, now, now.plusYears(VALID_CONTRACT));
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
	return hasActiveAccountability() && !isBlocked() && !isCanceled();
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
	return !isPendingAgreementToApprove() && hasAgreementForApproval() && hasActiveAccountability();
    }

    public boolean hasActiveAccountability() {
	return getActiveAccountability() != null;
    }

    public boolean hasActiveAccountabilityType() {
	return getActiveAccountabilityType() != null;
    }

    public boolean hasBeenAcceptedBefore() {
	return getUnit().getParentAccountabilities().size() > 1;
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

    public AccountabilityType getLastAccountabilityType() {
	Integer mostRecent = null;
	Accountability ret = null;

	for (Accountability accountability : getUnit().getParentAccountabilities()) {
	    if (mostRecent == null) {
		mostRecent = accountability.getIdInternal();
		ret = accountability;
	    } else if (accountability.getIdInternal() > mostRecent) {
		mostRecent = accountability.getIdInternal();
		ret = accountability;
	    }
	}
	return ret.getAccountabilityType();
    }

    public AccountabilityType getActiveAccountabilityType() {
	if (hasActiveAccountability())
	    return getActiveAccountability().getAccountabilityType();
	return null;
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

    public ArrayList<JobOffer> getPublicationsJobOffers() {
	Set<JobOffer> offers = Utils.readValuesToSatisfiedPredicate(new IPredicate<JobOffer>() {
	    @Override
	    public boolean evaluate(JobOffer object) {
		return object.isActive() && object.isCandidancyPeriod();
	    }
	}, getJobOfferSet());

	ArrayList<JobOffer> ret = new ArrayList<JobOffer>(offers);
	Collections.sort(ret, JobOffer.COMPARATOR_BY_PROCESS_IDENTIFICATION);
	return ret;
    }

    public Set<JobOfferProcess> getJobOfferProcesses() {
	final Set<JobOfferProcess> jobOfferProcesses = new HashSet<JobOfferProcess>();
	Utils.readValuesToSatisfiedPredicate(new IPredicate<JobOffer>() {

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
	return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getEnterprisesSet());
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
	return readEnterpriseByEmailLogin(emailLogin) != null;
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

    public static Enterprise readEnterpriseByEmailLogin(String emailLogin) {
	for (Enterprise enterprise : JobBankSystem.getInstance().getEnterprises()) {
	    if (enterprise.getLoginEmail() != null) {
		if (enterprise.getLoginEmail().equalsIgnoreCase(emailLogin)) {
		    return enterprise;
		}
	    }
	}
	return null;

    }

    @Service
    public static void passwordRecover(String emailLogin) {
	Enterprise enterprise = Enterprise.readEnterpriseByEmailLogin(emailLogin);
	if (enterprise != null) {
	    final User user = enterprise.getUser();
	    if (user != null) {
		final PasswordRecoveryRequest passwordRecoveryRequest = new PasswordRecoveryRequest(user);
		List<String> toAddress = new LinkedList<String>();
		toAddress.add(emailLogin);
		final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
		new Email(virtualHost.getApplicationSubTitle().getContent(), virtualHost.getSystemEmailAddress(), new String[] {},
			toAddress, Collections.EMPTY_LIST, Collections.EMPTY_LIST, BundleUtil.getFormattedStringFromResourceBundle(
				JobBankSystem.JOB_BANK_RESOURCES, "message.enterprise.recoverPassword"),
				getBodyEmailPasswordRecover(passwordRecoveryRequest));
		return;		
	    }
	}
	throw new DomainException("message.error.enterprise.recoverPassword",
		DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));

    }

    /* Private Methods */

    private static String getBodyEmailPasswordRecover(final PasswordRecoveryRequest passwordRecoveryRequest) {
	final StringBuilder builder = new StringBuilder();
	builder.append(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
		"message.enterprise.recoverPassword.body",
		passwordRecoveryRequest.getRecoveryUrl("https://jobbank.ist.utl.pt/")));
	builder.append("\n\n\n");
	builder.append(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
		"message.jobBank.ist"));
	return builder.toString();
    }

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
	setJobBankSystem(JobBankSystem.getInstance());
	UnitBean unitBean = new UnitBean();
	unitBean.setPartyType(PartyType.readBy(JobBankSystem.PARTY_TYPE_NAME));
	unitBean.setParent(getJobBankSystem().getTopLevelUnit());
	unitBean.setAccountabilityType(JobBankAccountabilityType.PENDING.readAccountabilityType());
	unitBean.setName(enterpriseBean.getName());
	unitBean.setAcronym(enterpriseBean.getName().getContent());
	setUnit(Unit.create(unitBean));
	createUser(enterpriseBean.getEmailValidation().getEmail());
	enterpriseBean.getEmailValidation().setExpiredDate(new DateTime());
	// Set basic agreement
	enterpriseBean.setJobBankAccountabilityType(JobBankAccountabilityType.JOB_PROVIDER);
	setForm(enterpriseBean);
	setEnterpriseProcess(new EnterpriseProcess(this));

	List<PersistentGroup> privateGroup = new ArrayList<PersistentGroup>();
	List<PersistentGroup> publicGroup = ContactsConfigurator.getInstance().getVisibilityGroups();

	EmailAddress.createNewEmailAddress(enterpriseBean.getPrivateContactEmail(), this.getUnit(), true, PartyContactType.WORK,
		getUser(), privateGroup);
	if (!StringUtils.isEmpty(enterpriseBean.getPublicContactEmail())) {
	    EmailAddress.createNewEmailAddress(enterpriseBean.getPublicContactEmail(), this.getUnit(), true,
		    PartyContactType.WORK, getUser(), publicGroup);
	}
	if (!StringUtils.isEmpty(enterpriseBean.getPhone())) {
	    Phone.createNewPhone(PhoneType.REGULAR_PHONE, enterpriseBean.getPhone(), this.getUnit(), true, PartyContactType.WORK,
		    getUser(), privateGroup);
	}
	if (!StringUtils.isEmpty(enterpriseBean.getMobilePhone())) {
	    Phone.createNewPhone(PhoneType.CELLPHONE, enterpriseBean.getMobilePhone(), this.getUnit(), true,
		    PartyContactType.WORK, getUser(), privateGroup);
	}
	if (!StringUtils.isEmpty(enterpriseBean.getWebAddress())) {
	    WebAddress.createNewWebAddress(enterpriseBean.getWebAddress(), this.getUnit(), true, PartyContactType.WORK,
		    getUser(), publicGroup);
	}
    }

    private boolean isAccountabilityType(AccountabilityType accountabilityType) {
	if (hasActiveAccountability()) {
	    return getActiveAccountabilityType().equals(accountabilityType);
	}
	return false;
    }

    private void checks(EnterpriseBean enterpriseBean) {
	if (isLoginEmailAlreadyRegistered(enterpriseBean.getLoginEmail())) {
	    throw new DomainException("message.error.enterprise.email.already.registered",
		    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
	if (isNameAlreadyRegistered(enterpriseBean.getName().getContent())) {
	    throw new DomainException("error.jobBank.enterprise.name.already.exists",
		    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}

    }

    public boolean expiresIn(int months) {
	if (hasActiveAccountability()) {
	    LocalDate now = new LocalDate();
	    LocalDate nowPlusMonths = now.plusMonths(months);

	    if (getActiveAccountability().getEndDate() != null && getActiveAccountability().getEndDate().isEqual(nowPlusMonths)) {
		return true;
	    }
	}

	return false;
    }

    public EnterpriseStateType getEnterpriseState() {

	if (isCanceled()) {
	    return EnterpriseStateType.REJECTED;
	}

	if (isDisable()) {
	    return EnterpriseStateType.INACTIVE;
	}

	if (isChangeToRequestAgreement()) {
	    return EnterpriseStateType.REQUEST_CHANGE_AGREEMENT;
	}

	if (isPendingToApproval()) {
	    return EnterpriseStateType.PENDING_REGISTER;
	}

	if (isActive()) {
	    return EnterpriseStateType.ACTIVE;
	}

	return null;
    }

    public static boolean isEnterprise(Party party) {
	for (Enterprise e : JobBankSystem.getInstance().getEnterprises()) {
	    if (e.getUnit().getOid() == party.getOid()) {
		return true;
	    }
	}

	return false;
    }

    public void addContactInformation(HttpServletRequest request) {
	if (getUnit() != null) {
	    request.setAttribute("sortedContacts", getSortedContacts());
	    request.setAttribute("visibilityGroups", new ArrayList<PersistentGroup>(ContactsConfigurator.getInstance()
		    .getVisibilityGroups()));
	}
    }

    public Set<PartyContact> getSortedContacts() {
	ComparatorChain chain = new ComparatorChain();
	chain.addComparator(new BeanComparator("class.simpleName"));
	chain.addComparator(new BeanComparator("externalId"));
	TreeSet<PartyContact> sortedContacts = new TreeSet<PartyContact>(chain);
	if (getUnit() != null) {
	    User currentUser = UserView.getCurrentUser();
	    if ((currentUser.getEnterprise() != null && currentUser.getEnterprise().equals(this))
		    || JobBankSystem.getInstance().isNPEMember(currentUser)) {
		sortedContacts.addAll(getUnit().getPartyContacts());
	    } else {
		for (PartyContact contact : getUnit().getPartyContacts()) {
		    if (contact.isVisibleTo(currentUser)) {
			sortedContacts.add(contact);
		    }
		}
	    }
	}
	return sortedContacts;
    }

}
