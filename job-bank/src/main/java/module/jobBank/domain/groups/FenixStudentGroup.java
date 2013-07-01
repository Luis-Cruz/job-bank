package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.webserviceutils.client.JerseyRemoteUser;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

public class FenixStudentGroup extends FenixStudentGroup_Base {

    public FenixStudentGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
        return isStudent(user);
    }

    @Atomic
    private Boolean isStudent(User user) {
        if (user != null && user.getPerson() != null) {
            return new JerseyRemoteUser(user).hasStudent();
        }
        return false;
    }

    @Atomic
    public static FenixStudentGroup getInstance() {
        final FenixStudentGroup fenixStudentGroup = (FenixStudentGroup) PersistentGroup.getSystemGroup(FenixStudentGroup.class);
        return fenixStudentGroup == null ? new FenixStudentGroup() : fenixStudentGroup;
    }

    @Override
    public String getName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                "label.jobBank.group.fenixStudentGroup.name");
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<User>();
        for (User user : MyOrg.getInstance().getUserSet()) {
            if (user.getPerson() != null && new JerseyRemoteUser(user).hasStudent()) {
                users.add(user);
            }
        }
        return users;
    }
}
