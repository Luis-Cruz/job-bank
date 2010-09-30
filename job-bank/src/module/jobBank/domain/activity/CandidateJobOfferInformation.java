package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.CandidateOffer;
import module.jobBank.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

public class CandidateJobOfferInformation extends ActivityInformation<JobOfferProcess> {

    private final List<CandidateJobOfferHolder> holder = new ArrayList<CandidateJobOfferHolder>();

    public CandidateJobOfferInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {

	super(jobOfferProcess, activity);
	for (CandidateOffer candidateOffer : jobOfferProcess.getJobOffer().getCandidateOffer()) {
	    holder.add(new CandidateJobOfferHolder(candidateOffer));
	}

    }

    public static class CandidateJobOfferHolder {
	private DateTime creationDate;

	public CandidateJobOfferHolder(CandidateOffer candidateOffer) {
	    super();
	    setCreationDate(candidateOffer.getCreationDate());
	}

	public void setCreationDate(DateTime creationDate) {
	    this.creationDate = creationDate;
	}

	public DateTime getCreationDate() {
	    return creationDate;
	}

    }

}
