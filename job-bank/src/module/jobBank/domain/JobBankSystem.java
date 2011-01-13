package module.jobBank.domain;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.domain.Unit;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.ModuleInitializer;
import myorg.domain.MyOrg;
import myorg.domain.RoleType;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestChecksumFilter;
import pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestChecksumFilter.ChecksumPredicate;
import pt.ist.fenixframework.plugins.remote.domain.RemoteHost;
import pt.ist.fenixframework.plugins.remote.domain.RemoteSystem;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class JobBankSystem extends JobBankSystem_Base implements ModuleInitializer {

    public static final String JOB_BANK_RESOURCES = "resources/JobBankResources";
    public static final String PARTY_TYPE_NAME = "Enterprise";

    private JobBankSystem() {
	super();
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

    public boolean isEnterpriseActiveMember() {
	User user = UserView.getCurrentUser();
	return isEnterpriseActiveMember(user);
    }

    public boolean isEnterpriseActiveMember(User user) {
	return isEnterpriseMember(user) && user.getEnterprise().isActive();
    }

    public boolean isEnterpriseMember(User user) {
	return hasUser(user) && user.hasEnterprise();
    }

    public boolean isManagementMember() {
	User user = UserView.getCurrentUser();
	return isManagementMember(user);
    }

    public boolean isManagementMember(User user) {
	return hasUser(user) && user.hasRoleType(RoleType.MANAGER);
    }

    public boolean isNPEMember() {
	User user = UserView.getCurrentUser();
	return isNPEMember(user);
    }

    public boolean isNPEMember(User user) {
	return hasUser(user) && getManagementUsers().contains(user);
    }

    public boolean isStudentMember() {
	User user = UserView.getCurrentUser();
	return isStudentMember(user);
    }

    public boolean isStudentMember(User user) {
	return Student.hasStudent(user);
    }

    public boolean isActiveStudentMember(User user) {
	return isStudentMember(user) && user.getPerson().getStudent().isActive();
    }

    @Override
    public void init(MyOrg root) {
	RequestChecksumFilter.registerFilterRule(new ChecksumPredicate() {
	    public boolean shouldFilter(HttpServletRequest httpServletRequest) {
		return !(httpServletRequest.getRequestURI().endsWith("/enterprise.do")
			&& httpServletRequest.getQueryString() != null && httpServletRequest.getQueryString().contains(
			"method=emailValidation"));
	    }
	});
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

    private boolean hasUser(User user) {
	return user != null;
    }

    /* Static Methods */
    public static RemoteHost readRemoteHost() {
	return RemoteSystem.getInstance().getRemoteHostsIterator().next();
    }

    @Override
    @Service
    public void setUrlEmailValidation(String urlEmailValidation) {
	super.setUrlEmailValidation(urlEmailValidation);
    }

}
