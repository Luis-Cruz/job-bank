package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class EnterpriseGroup extends EnterpriseGroup_Base {

    public EnterpriseGroup() {
	super();
    }

    @Override
    public boolean isMember(User user) {
	return JobBankSystem.getInstance().isEnterpriseMember(user);
    }

    @Service
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
	    if (enterprise.isActive()) {
		users.add(enterprise.getUser());
	    }
	}
	return users;
    }

}
