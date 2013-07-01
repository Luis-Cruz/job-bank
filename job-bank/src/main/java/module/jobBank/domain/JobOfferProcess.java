package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.jobBank.domain.activity.CancelJobOfferActivity;
import module.jobBank.domain.activity.CancelJobOfferApprovalActivity;
import module.jobBank.domain.activity.CancelJobOfferPublicationActivity;
import module.jobBank.domain.activity.CancelJobOfferSubmitionForApprovalActivity;
import module.jobBank.domain.activity.JobOfferApprovalActivity;
import module.jobBank.domain.activity.JobOfferConcludedActivity;
import module.jobBank.domain.activity.JobOfferEditActivity;
import module.jobBank.domain.activity.JobOfferEditActivityByNPE;
import module.jobBank.domain.activity.SubmitJobOfferForApprovalActivity;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.JobBankProcessStageView;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;

public class JobOfferProcess extends JobOfferProcess_Base {

    public static final Comparator<JobOfferProcess> COMPARATOR_BY_REGISTRATION = new Comparator<JobOfferProcess>() {

        @Override
        public int compare(JobOfferProcess o1, JobOfferProcess o2) {
            final Integer year1 = o1.getJobOffer().getJobBankYear().getYear();
            final Integer year2 = o2.getJobOffer().getJobBankYear().getYear();

            final Integer proc1 = Integer.valueOf(o1.getProcessNumber());
            final Integer proc2 = Integer.valueOf(o2.getProcessNumber());

            if (year1.compareTo(year2) == 0) {
                return -1 * proc1.compareTo(proc2);
            }

            return -1 * year1.compareTo(year2);
        }

    };

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;
    static {
        final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux =
                new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
        // NPE only
        activitiesAux.add(new JobOfferEditActivityByNPE());

        activitiesAux.add(new JobOfferEditActivity());
        activitiesAux.add(new SubmitJobOfferForApprovalActivity());
        activitiesAux.add(new JobOfferApprovalActivity());
        activitiesAux.add(new CancelJobOfferActivity());
        activitiesAux.add(new CancelJobOfferApprovalActivity());
        activitiesAux.add(new CancelJobOfferSubmitionForApprovalActivity());
        activitiesAux.add(new CancelJobOfferPublicationActivity());
        activitiesAux.add(new JobOfferConcludedActivity());

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
        return isAccessible(UserView.getCurrentUser()) || JobBankSystem.getInstance().isNPEMember();
    }

    public boolean getCanViewJobProcess() {
        JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        return jobBankSystem.isNPEMember() || jobBankSystem.isEnterpriseActiveMember() || jobBankSystem.isStudentMember();
    }

    public boolean getCanManageCandidatesJobProcess() {
        return isProcessOwner(UserView.getCurrentUser()) && getJobOffer().isCandidancyPeriod()
                || getJobOffer().isSelectionPeriod();
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

    public JobBankProcessStageView getJobBankProcessStageView() {
        return new JobBankProcessStageView(getJobOffer());
    }

    @Override
    public boolean isTicketSupportAvailable() {
        return false;
    }

    @Override
    public boolean isFileSupportAvailable() {
        return false;
    }

    @Override
    public boolean isCreatedByAvailable() {
        return false;
    }

    @Override
    public boolean isUserCanViewLogs(User user) {
        return JobBankSystem.getInstance().isNPEMember(user);
    }

    /*
     * @Override public List<Class<? extends ProcessFile>>
     * getAvailableFileTypes() { final List<Class<? extends ProcessFile>> list =
     * super.getAvailableFileTypes(); list.add(0, Curriculum.class); return
     * list; }
     */
    @Deprecated
    public boolean hasJobOffer() {
        return getJobOffer() != null;
    }

}
