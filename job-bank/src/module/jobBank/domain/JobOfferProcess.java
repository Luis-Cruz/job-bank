package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.jobBank.domain.activity.CancelJobOfferActivity;
import module.jobBank.domain.activity.EditJobOfferActivity;
import module.jobBank.domain.activity.JobOfferApprovalActivity;
import module.jobBank.domain.activity.SubmitJobOfferForApprovalActivity;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.MobilityProcessStageView;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

public class JobOfferProcess extends JobOfferProcess_Base {

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();

	activitiesAux.add(new SubmitJobOfferForApprovalActivity());
	activitiesAux.add(new CancelJobOfferActivity());
	activitiesAux.add(new EditJobOfferActivity());
	activitiesAux.add(new JobOfferApprovalActivity());
	activities = Collections.unmodifiableList(activitiesAux);
    }

    public JobOfferProcess(final JobOffer jobOffer) {
	super();
	setJobOffer(jobOffer);
	setProcessNumber(jobOffer.getJobBankYear().nextNumber().toString());
    }

    public String getProcessIdentification() {
	return "NPE" + getJobOffer().getJobBankYear().getYear() + "/" + getProcessNumber();
    }

    @Override
    public User getProcessCreator() {
	return getJobOffer().getEnterprise().getUser();
    }

    @Override
    public boolean isActive() {
	return getJobOffer().isActive();
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
    }

    @Override
    public boolean isAccessible(User user) {
	return isProcessOwner(user) || JobBankSystem.getInstance().isManagementMember(user);
    }

    public boolean isAccessible() {
	User user = UserView.getCurrentUser();
	return isAccessible(user);
    }

    public boolean isProcessOwner(User user) {
	return getProcessCreator().equals(user);

    }

    public boolean getCanManageJobProcess() {
	return isAccessible(UserView.getCurrentUser()) || JobBankSystem.getInstance().isManagementMember();
    }

    public boolean getCanViewJobProcess() {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.isManagementMember() || jobBankSystem.isEnterpriseMember() || jobBankSystem.isStudentMember();
    }

    public boolean getCanManageCandidatesJobProcess() {
	return isProcessOwner(UserView.getCurrentUser()) && getJobOffer().isCandidancyPeriod()
		|| getJobOffer().isAfterCompletedCandidancyPeriod();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {

	return (List<T>) activities;
    }

    public static Set<JobOfferProcess> readJobOfferProcess(IPredicate<JobOfferProcess> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	Set<JobOfferProcess> jobOffers = new HashSet<JobOfferProcess>();
	for (JobOffer jobOffer : jobBankSystem.getJobOffers()) {
	    JobOfferProcess process = jobOffer.getJobOfferProcess();
	    if (predicate.evaluate(process)) {
		jobOffers.add(process);
	    }
	}
	return jobOffers;

    }

    public MobilityProcessStageView getMobilityProcessStageView() {
	return new MobilityProcessStageView(getJobOffer());
    }

    @Override
    public boolean isTicketSupportAvailable() {
	return false;
    }
}
