package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import myorg.domain.MyOrg;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class FenixStudentGroup extends FenixStudentGroup_Base {

    public FenixStudentGroup() {
	super();
    }

    @Override
    public boolean isMember(User user) {
	return isStudent(user);
    }

    @Service
    private Boolean isStudent(User user) {
	return user != null && user.getPerson() != null && user.getPerson().getRemotePerson() != null
		&& user.getPerson().getRemotePerson().getStudent() != null;
    }

    @Service
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
	    if (user.getPerson() != null && user.getPerson().getRemotePerson().getStudent() != null) {
		users.add(user);
	    }
	}
	return users;
    }
}
