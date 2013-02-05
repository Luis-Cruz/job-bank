package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.OfferCandidacy;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

public class CandidateJobOfferInformation extends ActivityInformation<JobOfferProcess> {

    private final List<CandidateJobOfferHolder> holder = new ArrayList<CandidateJobOfferHolder>();

    public CandidateJobOfferInformation(final JobOfferProcess jobOfferProcess,
            WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {

        super(jobOfferProcess, activity);
        for (OfferCandidacy offerCandidacy : jobOfferProcess.getJobOffer().getOfferCandidacy()) {
            holder.add(new CandidateJobOfferHolder(offerCandidacy));
        }

    }

    public static class CandidateJobOfferHolder {
        private DateTime creationDate;

        public CandidateJobOfferHolder(OfferCandidacy offerCandidacy) {
            super();
            setCreationDate(offerCandidacy.getCreationDate());
        }

        public void setCreationDate(DateTime creationDate) {
            this.creationDate = creationDate;
        }

        public DateTime getCreationDate() {
            return creationDate;
        }

    }

}
