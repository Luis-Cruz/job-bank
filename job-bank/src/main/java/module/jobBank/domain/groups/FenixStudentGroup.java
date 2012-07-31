package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

import module.jobBank.domain.JobBankSystem;

public class FenixStudentGroup extends FenixStudentGroup_Base {

    public FenixStudentGroup() {
	super();
	setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
	return isStudent(user);
    }

    @Service
    private Boolean isStudent(User user) {
	// return user != null && user.getPerson() != null &&
	// user.getPerson().getRemotePerson() != null
	// && user.getPerson().getRemotePerson().getStudent() != null;
	return false;
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
	    // if (user.getPerson() != null &&
	    // user.getPerson().getRemotePerson().getStudent() != null) {
	    // users.add(user);
	    // }
	}
	return users;
    }
}
