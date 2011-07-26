package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.activity.CurriculumInfoActivity;
import module.jobBank.domain.activity.CurriculumQualificationActivity;
import module.jobBank.domain.activity.CurriculumRefreshExternalDataActivity;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

public class CurriculumProcess extends CurriculumProcess_Base {
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();

	activitiesAux.add(new CurriculumInfoActivity());
	activitiesAux.add(new CurriculumQualificationActivity());
	activitiesAux.add(new CurriculumRefreshExternalDataActivity());
	activities = Collections.unmodifiableList(activitiesAux);
    }

    public CurriculumProcess(final Curriculum curriculum) {
	super();
	setCurriculum(curriculum);
    }

    @Override
    public User getProcessCreator() {
	return getCurriculum().getStudent().getUser();
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
	return JobBankSystem.getInstance().isStudentMember();
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
    public boolean isCommentsSupportAvailable() {
	return false;
    }

    @Override
    public List<Class<? extends ProcessFile>> getAvailableFileTypes() {
	final List<Class<? extends ProcessFile>> list = super.getAvailableFileTypes();
	list.add(CurriculumProcessFile.class);
	list.add(CoverLetterProcessFile.class);
	list.add(LetterRecomendationProcessFile.class);
	return list;
    }

}
