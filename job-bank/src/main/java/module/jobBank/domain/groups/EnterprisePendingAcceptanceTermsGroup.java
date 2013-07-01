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

public class EnterprisePendingAcceptanceTermsGroup extends EnterprisePendingAcceptanceTermsGroup_Base {

    public EnterprisePendingAcceptanceTermsGroup() {
        super();
        setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Atomic
    public static EnterprisePendingAcceptanceTermsGroup getInstance() {
        final EnterprisePendingAcceptanceTermsGroup enterprisePendingAcceptanceTermsGroup =
                (EnterprisePendingAcceptanceTermsGroup) PersistentGroup
                        .getSystemGroup(EnterprisePendingAcceptanceTermsGroup.class);
        return enterprisePendingAcceptanceTermsGroup == null ? new EnterprisePendingAcceptanceTermsGroup() : enterprisePendingAcceptanceTermsGroup;
    }

    @Override
    public boolean isMember(User user) {
        Enterprise enterprise = user != null ? user.getEnterprise() : null;
        return JobBankSystem.getInstance().isEnterpriseMember(user)
                && !enterprise.getAcceptedTermsOfResponsibilityForCurrentYear();
    }

    @Override
    public String getName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                "label.jobBank.group.enterprisePendingAcceptanceTermsGroup.name");
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<User>();
        for (Enterprise enterprise : JobBankSystem.getInstance().getEnterprises()) {
            if (!enterprise.getAcceptedTermsOfResponsibilityForCurrentYear()) {
                users.add(enterprise.getUser());
            }
        }
        return users;
    }

}
