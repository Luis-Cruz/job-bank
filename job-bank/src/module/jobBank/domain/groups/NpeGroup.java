package module.jobBank.domain.groups;

import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class NpeGroup extends NpeGroup_Base {

    public NpeGroup() {
	super();
    }

    @Override
    public boolean isMember(User user) {
	return JobBankSystem.getInstance().isManagementMember(user);
    }

    @Service
    public static NpeGroup getInstance() {
	final NpeGroup npeGroup = (NpeGroup) PersistentGroup.getSystemGroup(NpeGroup.class);
	return npeGroup == null ? new NpeGroup() : npeGroup;
    }

    @Override
    public String getName() {
	return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.jobBank.group.jobGroup.name");
    }

    @Override
    public Set<User> getMembers() {
	return JobBankSystem.getInstance().getManagementUsersSet();
    }

}
