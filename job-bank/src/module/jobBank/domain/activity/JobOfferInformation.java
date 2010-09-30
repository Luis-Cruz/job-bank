package module.jobBank.domain.activity;

import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.beans.JobOfferBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class JobOfferInformation extends ActivityInformation<JobOfferProcess> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JobOfferBean jobOfferBean;

    public JobOfferInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
	setJobOfferBean(new JobOfferBean(jobOfferProcess.getJobOffer()));
    }

    @Override
    public boolean hasAllneededInfo() {
	return getProcess().getJobOffer() != null && isForwardedFromInput();
    }

    public JobOfferBean getJobOfferBean() {
	return jobOfferBean;
    }

    public void setJobOfferBean(JobOfferBean jobOfferBean) {
	this.jobOfferBean = jobOfferBean;
    }

    @Override
    public String getUsedSchema() {
	return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
    }

}
