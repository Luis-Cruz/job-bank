package module.jobBank.domain.activity;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.StudentAuthorizationProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

public class StudentAuthorizationRefreshExternalDataActivity extends
        WorkflowActivity<StudentAuthorizationProcess, ActivityInformation<StudentAuthorizationProcess>> {

    @Override
    public boolean isActive(StudentAuthorizationProcess process, User user) {
        return true;
    }

    @Override
    protected void process(ActivityInformation<StudentAuthorizationProcess> activityInformation) {
        activityInformation.getProcess().getStudentAuthorization().getStudent().getCurriculum().loadExternalData();
    }

    @Override
    public ActivityInformation<StudentAuthorizationProcess> getActivityInformation(StudentAuthorizationProcess process) {
        return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
        return JobBankSystem.JOB_BANK_RESOURCES;
    }
}
