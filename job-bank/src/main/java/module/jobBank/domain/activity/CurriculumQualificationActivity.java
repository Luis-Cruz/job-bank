package module.jobBank.domain.activity;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

public class CurriculumQualificationActivity extends WorkflowActivity<CurriculumProcess, CurriculumQualificationInformation> {

    @Override
    public boolean isActive(CurriculumProcess process, User user) {
        return false;
        // return process.isProcessOwner(user);
    }

    @Override
    protected void process(CurriculumQualificationInformation activityInformation) {
        activityInformation.create();
    }

    @Override
    public ActivityInformation<CurriculumProcess> getActivityInformation(CurriculumProcess process) {
        return new CurriculumQualificationInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
        return JobBankSystem.JOB_BANK_RESOURCES;
    }

    @Override
    public boolean isDefaultInputInterfaceUsed() {
        return false;
    }

}
