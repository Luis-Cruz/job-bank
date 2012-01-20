package module.jobBank.domain.groups;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import myorg.domain.MyOrg;
import myorg.domain.User;
import myorg.domain.groups.PersistentGroup;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

public class EnterpriseActiveGroup extends EnterpriseActiveGroup_Base {

    public EnterpriseActiveGroup() {
	super();
	setSystemGroupMyOrg(MyOrg.getInstance());
    }

    @Override
    public boolean isMember(User user) {
	return JobBankSystem.getInstance().isEnterpriseActiveMember(user);
    }

    @Service
    public static EnterpriseActiveGroup getInstance() {
	final EnterpriseActiveGroup enterpriseActiveGroup = (EnterpriseActiveGroup) PersistentGroup
		.getSystemGroup(EnterpriseGroup.class);
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
