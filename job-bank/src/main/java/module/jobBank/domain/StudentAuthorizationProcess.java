package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.activity.EditStudentAuthorizationActivity;
import module.jobBank.domain.activity.StudentAuthorizationRefreshExternalDataActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import pt.ist.bennu.core.domain.User;

public class StudentAuthorizationProcess extends StudentAuthorizationProcess_Base {

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
        final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux =
                new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
        activitiesAux.add(new EditStudentAuthorizationActivity());
        activitiesAux.add(new StudentAuthorizationRefreshExternalDataActivity());
        activities = Collections.unmodifiableList(activitiesAux);
    }

    public StudentAuthorizationProcess(StudentAuthorization studentAuthorization) {
        super();
        setStudentAuthorization(studentAuthorization);
        setProcessNumber(String.valueOf(studentAuthorization.getStudent().getStudentAuthorizationSet().size()));
    }

    public String getProcessIdentification() {
        return "AUT" + "/" + getStudentAuthorization().getStudent().getPerson().getUser().getUsername() + "/"
                + getProcessNumber();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
        return (List<T>) activities;
    }

    @Override
    public boolean isActive() {
        return getStudentAuthorization().getIsActive();
    }

    @Override
    public User getProcessCreator() {
        return null;
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
    }

    @Override
    public boolean isTicketSupportAvailable() {
        return false;
    }

    @Deprecated
    public boolean hasStudentAuthorization() {
        return getStudentAuthorization() != null;
    }

}
