package module.jobBank.domain.activity;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;

public class CurriculumRefreshExternalDataActivity extends
	WorkflowActivity<CurriculumProcess, ActivityInformation<CurriculumProcess>> {

    @Override
    public boolean isActive(CurriculumProcess process, User user) {
	return true;
    }

    @Override
    protected void process(ActivityInformation<CurriculumProcess> activityInformation) {
	activityInformation.getProcess().getCurriculum().loadExternalData();
    }

    @Override
    public ActivityInformation<CurriculumProcess> getActivityInformation(CurriculumProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }
}
