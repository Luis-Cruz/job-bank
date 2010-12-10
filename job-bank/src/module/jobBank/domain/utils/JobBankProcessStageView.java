package module.jobBank.domain.utils;

import java.util.SortedMap;
import java.util.TreeMap;

import module.jobBank.domain.JobOffer;

public class JobBankProcessStageView {

    private final JobOffer offer;

    public JobBankProcessStageView(JobOffer offer) {
	this.offer = offer;
    }

    public SortedMap<JobBankProcessStage, JobBankProcessStageState> getJobBankProcessStageStates() {
	final SortedMap<JobBankProcessStage, JobBankProcessStageState> result = new TreeMap<JobBankProcessStage, JobBankProcessStageState>();

	if (!offer.isCanceled()) {
	    result.put(JobBankProcessStage.UNDER_CONTRUCTION, getEditable());
	    result.put(JobBankProcessStage.APROVED, getAproved());
	    result.put(JobBankProcessStage.PUBLISHED, getPublishedState());
	    result.put(JobBankProcessStage.SELECTION, getCandidatedState());
	} else {
	    result.put(JobBankProcessStage.APROVED, JobBankProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(JobBankProcessStage.UNDER_CONTRUCTION, JobBankProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(JobBankProcessStage.PUBLISHED, JobBankProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(JobBankProcessStage.SELECTION, JobBankProcessStageState.NOT_YET_UNDER_WAY);
	}
	return result;
    }

    protected JobBankProcessStageState getEditable() {
	if (offer.isPendingToApproval()) {
	    return JobBankProcessStageState.COMPLETED;
	}
	if (offer.isApproved()) {
	    return JobBankProcessStageState.COMPLETED;
	}
	return JobBankProcessStageState.UNDER_WAY;
    }

    private JobBankProcessStageState getAproved() {
	if (offer.isPendingToApproval()) {
	    return JobBankProcessStageState.UNDER_WAY;
	}
	if (offer.isApproved()) {
	    return JobBankProcessStageState.COMPLETED;
	}
	return JobBankProcessStageState.NOT_YET_UNDER_WAY;

    }

    private JobBankProcessStageState getPublishedState() {
	if (offer.isCandidancyPeriod()) {
	    return JobBankProcessStageState.UNDER_WAY;
	}
	if (offer.isAfterCompleteCandidancyPeriod()) {
	    return JobBankProcessStageState.COMPLETED;
	}
	return JobBankProcessStageState.NOT_YET_UNDER_WAY;
    }

    private JobBankProcessStageState getCandidatedState() {
	if (offer.isConclued()) {
	    return JobBankProcessStageState.COMPLETED;
	}
	if (offer.isSelectionPeriod()) {
	    return JobBankProcessStageState.UNDER_WAY;
	}
	return JobBankProcessStageState.NOT_YET_UNDER_WAY;
    }

    public JobOffer getOffer() {
	return offer;
    }

}
