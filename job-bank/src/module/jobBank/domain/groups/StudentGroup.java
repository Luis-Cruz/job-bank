package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class StudentGroup extends StudentGroup_Base {

    public StudentGroup() {
	super();
    }

    @Override
    public boolean isMember(User user) {
	return JobBankSystem.getInstance().isActiveStudentMember(user);
    }

    @Service
    public static StudentGroup getInstance() {
	final StudentGroup enterpriseGroupGroup = (StudentGroup) PersistentGroup.getSystemGroup(StudentGroup.class);
	return enterpriseGroupGroup == null ? new StudentGroup() : enterpriseGroupGroup;
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
