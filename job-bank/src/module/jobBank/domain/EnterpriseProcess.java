package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.activity.ChangeContractEnterpriseActivity;
import module.jobBank.domain.activity.EditEnterpriseActivity;
import module.jobBank.domain.activity.EnterpriseApprovalActivity;
import module.jobBank.domain.activity.EnterpriseRejectActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

public class EnterpriseProcess extends EnterpriseProcess_Base {
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();

	activitiesAux.add(new EditEnterpriseActivity());
	activitiesAux.add(new ChangeContractEnterpriseActivity());
	activitiesAux.add(new EnterpriseApprovalActivity());
	activitiesAux.add(new EnterpriseRejectActivity());

	activities = Collections.unmodifiableList(activitiesAux);
    }

    public EnterpriseProcess(final Enterprise enterprise) {
	super();
	setEnterprise(enterprise);
	setProcessNumber(enterprise.getNif());
    }

    @Override
    public User getProcessCreator() {
	return getEnterprise().getUser();
    }

    @Override
    public boolean isActive() {
	return true;
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
    }

    @Override
    public boolean isAccessible(User user) {
	return isProcessOwner(user) || JobBankSystem.getInstance().isManagementMember(user);
    }

    public boolean isAccessible() {
	User user = UserView.getCurrentUser();
	return isAccessible(user);
    }

    public boolean isProcessOwner(User user) {
	return getProcessCreator().equals(user);
    }

    public boolean getCanManageJobProcess() {
	return JobBankSystem.getInstance().isEnterpriseMember();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {

	return (List<T>) activities;
    }

    @Override
    public boolean isTicketSupportAvailable() {
	return false;
    }
}
