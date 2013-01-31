package module.jobBank.domain.activity;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.beans.CurriculumBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class CurriculumInformation extends ActivityInformation<CurriculumProcess> {

	private static final long serialVersionUID = 1L;

	private CurriculumBean curriculumBean;

	public CurriculumInformation(final CurriculumProcess curriculumProcess,
			WorkflowActivity<CurriculumProcess, ? extends ActivityInformation<CurriculumProcess>> activity) {
		super(curriculumProcess, activity);
		setCurriculumBean(new CurriculumBean(curriculumProcess.getCurriculum()));
	}

	@Override
	public boolean hasAllneededInfo() {
		return isForwardedFromInput();
	}

	public CurriculumBean getCurriculumBean() {
		return curriculumBean;
	}

	public void setCurriculumBean(CurriculumBean curriculumBean) {
		this.curriculumBean = curriculumBean;
	}

	@Override
	public String getUsedSchema() {
		return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
	}

}
