package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

public class StudentActiveGroup extends StudentActiveGroup_Base {

    public StudentActiveGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
        return JobBankSystem.getInstance().isActiveStudentMember(user);
    }

    @Atomic
    public static StudentActiveGroup getInstance() {
        final StudentActiveGroup studentGroupGroup =
                (StudentActiveGroup) PersistentGroup.getSystemGroup(StudentActiveGroup.class);
        return studentGroupGroup == null ? new StudentActiveGroup() : studentGroupGroup;
    }

    @Override
    public String getName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.jobBank.group.studentGroup.name");
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<User>();
        for (Student student : JobBankSystem.getInstance().getStudents()) {
            if (student.isActive()) {
                users.add(student.getUser());
            }
        }
        return users;
    }
}
