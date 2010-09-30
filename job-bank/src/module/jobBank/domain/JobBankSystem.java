package module.jobBank.domain;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.domain.Unit;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.MyOrg;
import myorg.domain.RoleType;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class JobBankSystem extends JobBankSystem_Base {

    public static final String JOB_BANK_RESOURCES = "resources/JobBankResources";
    public static final String PARTY_TYPE_NAME = "Enterprise";

    private JobBankSystem() {
	super();
	if (!hasCounterEnterprise()) {
	    super.setCounterEnterprise(Integer.valueOf(0));
	}
    }

    private JobBankSystem(final MyOrg myOrg) {
	this();
	setMyOrg(myOrg);
    }

    public static module.jobBank.domain.JobBankSystem getInstance() {
	final MyOrg myOrg = MyOrg.getInstance();
	if (!myOrg.hasJobBankSystem()) {
	    initialize();
	}
	return myOrg.getJobBankSystem();

    }

    @Service
    private synchronized static void initialize() {
	final MyOrg myOrg = MyOrg.getInstance();
	if (!myOrg.hasJobBankSystem()) {
	    new JobBankSystem(myOrg);
	}
    }

    public boolean isEnterpriseMember() {
	User user = UserView.getCurrentUser();
	return isEnterpriseMember(user);
    }

    public boolean isEnterpriseMember(User user) {
	return hasUser(user) && user.hasEnterprise() && user.getEnterprise().isActive();
    }

    public boolean isManagementMember() {
	User user = UserView.getCurrentUser();
	return hasUser(user) && user.hasRoleType(RoleType.MANAGER);
    }

    public boolean isManagementMember(User user) {
	return hasUser(user) && getManagementUsers().contains(user);
    }

    public boolean isManagementOrEnterpriseMember() {
	User user = UserView.getCurrentUser();
	return isManagementMember(user) || isEnterpriseMember(user);
    }

    public boolean isManagementOrEnterpriseMember(User user) {
	return isManagementMember(user) || isEnterpriseMember(user);
    }

    public boolean isStudentMember() {
	User user = UserView.getCurrentUser();
	return isStudentMember(user);
    }

    public boolean isStudentMember(User user) {
	return hasUser(user) && user.hasPerson() && user.getPerson().hasStudent();
    }

    public boolean hasCounterEnterprise() {
	return getCounterEnterprise() != null;
    }

    @Service
    public Integer nextNumber() {
	Integer oldValue = getCounterEnterprise();
	setCounterEnterprise(oldValue.intValue() + 1);
	return getCounterEnterprise();
    }

    @Service
    @Override
    public void setOrganizationalModel(final OrganizationalModel organizationalModel) {
	super.setOrganizationalModel(organizationalModel);
    }

    @Service
    public Unit getTopLevelUnit() {
	OrganizationalModel organizationalModel = getOrganizationalModel();

	if (organizationalModel == null) {
	    throw new DomainException("message.error.jobBankSystem.organizationalModel.need.to.select.organizationalModel",
		    ResourceBundle.getBundle(JobBankSystem.JOB_BANK_RESOURCES, Language.getLocale()));
	}

	if (organizationalModel.getPartiesCount() > 2) {
	    throw new DomainException("error.jobBankSystem.organizationalModel.not.have.root.organic.unit.or.exceed",
		    ResourceBundle.getBundle(JobBankSystem.JOB_BANK_RESOURCES, Language.getLocale()));
	}

	for (Party party : organizationalModel.getPartiesSet()) {
	    if (party.isUnit()) {
		return (Unit) party;
	    }
	}
	return null;
    }

    @Override
    @Service
    public void addManagementUsers(User user) {
	if (getManagementUsers().contains(user)) {
	    throw new DomainException("message.error.user.was.added.to.management", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
	super.addManagementUsers(user);
    }

    @Override
    @Service
    public void removeManagementUsers(User user) {
	if (!getManagementUsers().contains(user)) {
	    throw new DomainException("message.error.user.was.not.exists", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
	super.removeManagementUsers(user);
    }

    public <T> Set<T> readValuesToSatisfiedPredicate(IPredicate<T> predicate, Set<T> setToRead) {
	Set<T> objects = new HashSet<T>();
	for (T object : setToRead) {
	    if (predicate.evaluate(object)) {
		objects.add(object);
	    }
	}
	return objects;

    }

    private boolean hasUser(User user) {
	return user != null;
    }

}
