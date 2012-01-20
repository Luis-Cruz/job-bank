package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import myorg.domain.MyOrg;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class StudentGroup extends StudentGroup_Base {

    public StudentGroup() {
	super();
	setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
	return JobBankSystem.getInstance().isStudentMember(user);
    }

    @Service
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
