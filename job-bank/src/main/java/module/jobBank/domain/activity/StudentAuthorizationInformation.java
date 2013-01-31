package module.jobBank.domain.activity;

import module.jobBank.domain.StudentAuthorizationProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.LocalDate;

public class StudentAuthorizationInformation extends ActivityInformation<StudentAuthorizationProcess> {

	private LocalDate endDate;

	public StudentAuthorizationInformation(final StudentAuthorizationProcess process,
			WorkflowActivity<StudentAuthorizationProcess, ? extends ActivityInformation<StudentAuthorizationProcess>> activity) {
		super(process, activity);
		setEndDate(process.getStudentAuthorization().getEndDate());
	}

	@Override
	public boolean hasAllneededInfo() {
		return isForwardedFromInput();
	}

	@Override
	public String getUsedSchema() {
		return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
