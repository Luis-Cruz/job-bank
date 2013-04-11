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

public class StudentGroup extends StudentGroup_Base {

    public StudentGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
        return JobBankSystem.getInstance().isStudentMember(user);
    }

    @Atomic
    public static StudentGroup getInstance() {
        final StudentGroup studentGroup = (StudentGroup) PersistentGroup.getSystemGroup(StudentGroup.class);
        return studentGroup == null ? new StudentGroup() : studentGroup;
    }

    @Override
    public String getName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.jobBank.group.studentGroup.name");
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<User>();
        for (Student student : JobBankSystem.getInstance().getStudents()) {
            if (student.getStudentRegistrationSet().size() != 0 || student.getHasPersonalDataAuthorization()) {
                users.add(student.getUser());
            }
        }
        return users;
    }
}
