package module.jobBank.domain;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.domain.Unit;
import module.workflow.domain.WorkflowSystem;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.ModuleInitializer;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestChecksumFilter;
import pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestChecksumFilter.ChecksumPredicate;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class JobBankSystem extends JobBankSystem_Base implements ModuleInitializer {

    public static final String JOB_BANK_RESOURCES = "resources.JobBankResources";
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

    public void setEmailFromConfiguration(String emailValidationFromName, String emailValidationFromEmail) {
        setEmailValidationFromName(emailValidationFromName);
        setEmailValidationFromEmail(emailValidationFromEmail);
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
        return user != null
                && user.getPerson() != null
                && user.getPerson().getStudent() != null
                && (user.getPerson().getStudent().getStudentRegistrationSet().size() != 0 || !user.getPerson().getStudent()
                        .getHasPersonalDataAuthorization());
    }

    public boolean isActiveStudentMember(User user) {
        return isStudentMember(user) && user.getPerson().getStudent().isActive();
    }

    @Override
    public void init(MyOrg root) {
        RequestChecksumFilter.registerFilterRule(new ChecksumPredicate() {
            @Override
            public boolean shouldFilter(HttpServletRequest httpServletRequest) {
                return !(httpServletRequest.getRequestURI().endsWith("/enterprise.do")
                        && httpServletRequest.getQueryString() != null && httpServletRequest.getQueryString().contains(
                        "method=emailValidation"));
            }
        });

        if (WorkflowSystem.getInstance() == null) {
            VirtualHost currentVh = VirtualHost.getVirtualHostForThread();
            if (currentVh == null) {
                currentVh = root.getVirtualHosts().get(0);
            }
            WorkflowSystem.createSystem(currentVh);
        }

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
            throw new DomainException("message.error.user.was.added.to.management",
                    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
        }
        super.addManagementUsers(user);
    }

    @Override
    @Service
    public void removeManagementUsers(User user) {
        if (!getManagementUsers().contains(user)) {
            throw new DomainException("message.error.user.was.not.exists",
                    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
        }
        super.removeManagementUsers(user);
    }

    private boolean hasUser(User user) {
        return user != null;
    }

    @Override
    @Service
    public void setUrlEmailValidation(String urlEmailValidation) {
        super.setUrlEmailValidation(urlEmailValidation);
    }

    public Set<FenixDegree> getActiveFenixDegreeSet() {
        HashSet<FenixDegree> ret = new HashSet<FenixDegree>();
        Set<FenixDegree> allDegrees = getFenixDegreeSet();

        for (FenixDegree degree : allDegrees) {
            if (degree.getActive()) {
                ret.add(degree);
            }
        }

        return ret;
    }

    public Set<StudentRegistration> getActiveStudentRegistrationSet() {
        Set<StudentRegistration> studentRegistrations = new HashSet<StudentRegistration>();
        for (StudentRegistration studentRegistration : getStudentRegistrationSet()) {
            if (studentRegistration.hasMoreThanFirstCycle()) {
                studentRegistrations.add(studentRegistration);
            }
        }
        return studentRegistrations;
    }

    public Set<FenixDegree> getActiveMasterFenixDegreeSet() {
        HashSet<FenixDegree> ret = new HashSet<FenixDegree>();
        Set<FenixDegree> activeDegrees = getActiveFenixDegreeSet();

        for (FenixDegree degree : activeDegrees) {
            if (degree.isBolonhaMasterDegree()) {
                ret.add(degree);
            }
        }

        return ret;
    }

    public void processNotifications(JobOffer jobOffer) {
        for (JobOfferNotificationFilter filter : getJobOfferNotificationFilters()) {
            filter.sendNotification(jobOffer);
        }
    }

}
