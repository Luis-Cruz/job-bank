package module.jobBank.domain.activity;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;

public class CurriculumInfoActivity extends WorkflowActivity<CurriculumProcess, CurriculumInformation> {

    @Override
    public boolean isActive(CurriculumProcess process, User user) {
        return false;
        // return process.isProcessOwner(user);
    }

    @Override
    protected void process(CurriculumInformation activityInformation) {
        activityInformation.getProcess().getCurriculum().edit(activityInformation.getCurriculumBean());
    }

    @Override
    public ActivityInformation<CurriculumProcess> getActivityInformation(CurriculumProcess process) {
        return new CurriculumInformation(process, this);
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
