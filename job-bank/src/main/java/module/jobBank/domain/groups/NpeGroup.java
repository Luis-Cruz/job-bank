package module.jobBank.domain.groups;

import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

public class NpeGroup extends NpeGroup_Base {

    public NpeGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
        return JobBankSystem.getInstance().isNPEMember(user);
    }

    @Atomic
    public static NpeGroup getInstance() {
        final NpeGroup npeGroup = (NpeGroup) PersistentGroup.getSystemGroup(NpeGroup.class);
        return npeGroup == null ? new NpeGroup() : npeGroup;
    }

    @Override
    public String getName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.jobBank.group.jobBankGroup.name");
    }

    @Override
    public Set<User> getMembers() {
        return JobBankSystem.getInstance().getManagementUsersSet();
    }

}
