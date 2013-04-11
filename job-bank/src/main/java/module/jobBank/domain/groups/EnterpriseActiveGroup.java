package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.groups.PersistentGroup;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

public class EnterpriseActiveGroup extends EnterpriseActiveGroup_Base {

    public EnterpriseActiveGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
        return JobBankSystem.getInstance().isEnterpriseActiveMember(user);
    }

    @Atomic
    public static EnterpriseActiveGroup getInstance() {
        final EnterpriseActiveGroup enterpriseActiveGroup =
                (EnterpriseActiveGroup) PersistentGroup.getSystemGroup(EnterpriseActiveGroup.class);
        return enterpriseActiveGroup == null ? new EnterpriseActiveGroup() : enterpriseActiveGroup;
    }

    @Override
    public String getName() {
        return BundleUtil
                .getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.jobBank.group.entepriseGroup.name");
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<User>();
        for (Enterprise enterprise : JobBankSystem.getInstance().getEnterprises()) {
            if (enterprise.isActive()) {
                users.add(enterprise.getUser());
            }
        }
        return users;
    }
}
