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

public class EnterpriseGroup extends EnterpriseGroup_Base {

    public EnterpriseGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
        return JobBankSystem.getInstance().isEnterpriseMember(user);
    }

    @Atomic
    public static EnterpriseGroup getInstance() {
        final EnterpriseGroup enterpriseGroupGroup = (EnterpriseGroup) PersistentGroup.getSystemGroup(EnterpriseGroup.class);
        return enterpriseGroupGroup == null ? new EnterpriseGroup() : enterpriseGroupGroup;
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
            users.add(enterprise.getUser());
        }
        return users;
    }

}
