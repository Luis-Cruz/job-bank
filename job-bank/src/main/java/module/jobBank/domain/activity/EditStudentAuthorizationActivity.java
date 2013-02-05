package module.jobBank.domain.activity;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.StudentAuthorizationProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

public class EditStudentAuthorizationActivity extends
        WorkflowActivity<StudentAuthorizationProcess, StudentAuthorizationInformation> {

    @Override
    public boolean isActive(StudentAuthorizationProcess process, User user) {
        return process.isActive() && JobBankSystem.getInstance().isNPEMember(user);
    }

    @Override
    protected void process(StudentAuthorizationInformation activityInformation) {
        activityInformation.getProcess().getStudentAuthorization().editEndDate(activityInformation.getEndDate());
    }

    @Override
    public ActivityInformation<StudentAuthorizationProcess> getActivityInformation(StudentAuthorizationProcess process) {
        return new StudentAuthorizationInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
        return JobBankSystem.JOB_BANK_RESOURCES;
    }

    @Override
    public boolean isDefaultInputInterfaceUsed() {
        return true;
    }
}
