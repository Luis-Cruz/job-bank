package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.groups.IntersectionGroup;
import myorg.domain.groups.NegationGroup;
import myorg.domain.groups.PersistentGroup;
import myorg.domain.groups.UnionGroup;
import myorg.domain.groups.UserGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class StudentGroup extends EnterpriseGroup_Base {

    public StudentGroup() {
	super();
    }

    @Override
    public boolean isMember(User user) {
	return JobBankSystem.getInstance().isStudentMember(user);
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
	    users.add(student.getUser());
	}
	return users;
    }

    // TODO: Alterar para verificar se e estudante do IST
    public static PersistentGroup getViewStudentGroup() {
	User user = UserView.getCurrentUser();
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	PersistentGroup npeEnterpriseStudentGroup = UnionGroup.createUnionGroup(NpeGroup.getInstance(), EnterpriseGroup
		.getInstance(), StudentGroup.getInstance());
	NegationGroup npeEnterpriseNegationGroup = NegationGroup.createNegationGroup(npeEnterpriseStudentGroup);
	return IntersectionGroup.createIntersectionGroup(UserGroup.getInstance(), npeEnterpriseNegationGroup);
    }
}
