package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.jobBank.domain.activity.AcceptTermsOfResponsibilityActivity;
import module.jobBank.domain.activity.ApproveOrRejectEnterpriseActivity;
import module.jobBank.domain.activity.ApproveOrRejectEnterpriseChangeAgreementActivity;
import module.jobBank.domain.activity.ChangeAgreementEnterpriseActivity;
import module.jobBank.domain.activity.ChangeAgreementEnterpriseByNPEActivity;
import module.jobBank.domain.activity.EditEnterpriseByNPEActivity;
import module.jobBank.domain.activity.EditEnterpriseInfoActivity;
import module.jobBank.domain.activity.EditEnterprisePasswordActivity;
import module.jobBank.domain.activity.EnterpriseDisableActivity;
import module.jobBank.domain.activity.EnterpriseEnableActivity;
import module.jobBank.domain.utils.IPredicate;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;

public class EnterpriseProcess extends EnterpriseProcess_Base {
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
        final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux =
                new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();

        // Enterprise Only
        activitiesAux.add(new EditEnterpriseInfoActivity());
        activitiesAux.add(new EditEnterprisePasswordActivity());
        activitiesAux.add(new ChangeAgreementEnterpriseActivity());
        activitiesAux.add(new AcceptTermsOfResponsibilityActivity());

        // NPE Only
        // (enterprise)
        activitiesAux.add(new ApproveOrRejectEnterpriseActivity());
        activitiesAux.add(new EditEnterpriseByNPEActivity());

        // (agreements)
        activitiesAux.add(new ApproveOrRejectEnterpriseChangeAgreementActivity());
        activitiesAux.add(new ChangeAgreementEnterpriseByNPEActivity());

        // (agreements)
        activitiesAux.add(new EnterpriseEnableActivity());
        activitiesAux.add(new EnterpriseDisableActivity());

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
        return !getEnterprise().isCanceled();
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
    }

    @Override
    public boolean isAccessible(User user) {
        return isProcessOwner(user) || JobBankSystem.getInstance().isNPEMember(user);
    }

    public boolean isAccessible() {
        User user = UserView.getCurrentUser();
        return isAccessible(user);
    }

    public boolean isProcessOwner(User user) {
        return getProcessCreator().equals(user);
    }

    public boolean getCanManageJobProcess() {
        return JobBankSystem.getInstance().isEnterpriseActiveMember();
    }

    public boolean getIsEnterpriseMember() {
        User user = UserView.getCurrentUser();
        return JobBankSystem.getInstance().isEnterpriseMember(user);
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {

        return (List<T>) activities;
    }

    @Override
    public boolean isTicketSupportAvailable() {
        return false;
    }

    @Override
    public boolean isUserCanViewLogs(User user) {
        return JobBankSystem.getInstance().isNPEMember(user);
    }

    @Override
    public boolean isCommentsSupportAvailable() {
        return false;
    }

    @Override
    public boolean isFileSupportAvailable() {
        return false;
    }

    @Override
    public boolean isCreatedByAvailable() {
        return false;
    }

    public static Set<EnterpriseProcess> readEnterpriseProcess(IPredicate<EnterpriseProcess> predicate) {
        JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        Set<EnterpriseProcess> enterprises = new HashSet<EnterpriseProcess>();
        for (Enterprise enterprise : jobBankSystem.getEnterprises()) {
            EnterpriseProcess process = enterprise.getEnterpriseProcess();
            if (predicate.evaluate(process)) {
                enterprises.add(process);
            }
        }
        return enterprises;

    }
    @Deprecated
    public boolean hasEnterprise() {
        return getEnterprise() != null;
    }

}
