package module.jobBank.domain.utils;

import java.util.SortedMap;
import java.util.TreeMap;

import module.jobBank.domain.JobOffer;

public class MobilityProcessStageView {

    private final JobOffer offer;

    public MobilityProcessStageView(JobOffer offer) {
	this.offer = offer;
    }

    public SortedMap<MobilityProcessStage, MobilityProcessStageState> getMobilityProcessStageStates() {
	final SortedMap<MobilityProcessStage, MobilityProcessStageState> result = new TreeMap<MobilityProcessStage, MobilityProcessStageState>();

	if (!offer.isCanceled()) {
	    result.put(MobilityProcessStage.EDITABLE, getEditable());
	    result.put(MobilityProcessStage.PUBLISHED, getPublishedState());
	    result.put(MobilityProcessStage.CANDIDATED, getCandidatedState());
	} else {
	    result.put(MobilityProcessStage.EDITABLE, MobilityProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(MobilityProcessStage.PUBLISHED, MobilityProcessStageState.NOT_YET_UNDER_WAY);
	    result.put(MobilityProcessStage.CANDIDATED, MobilityProcessStageState.NOT_YET_UNDER_WAY);
	}
	return result;
    }

    protected MobilityProcessStageState getEditable() {
	if (offer.isPendingToApproval()) {
	    return MobilityProcessStageState.COMPLETED;
	} else {
	    if (offer.isApproved()) {
		return MobilityProcessStageState.COMPLETED;
	    }
	    return MobilityProcessStageState.UNDER_WAY;
	}

    }

    private MobilityProcessStageState getPublishedState() {
	if (offer.isPendingToApproval()) {
	    return MobilityProcessStageState.UNDER_WAY;
	} else {
	    if (offer.isApproved()) {
		return MobilityProcessStageState.COMPLETED;
	    } else {
		return MobilityProcessStageState.NOT_YET_UNDER_WAY;
	    }
	}

    }

    private MobilityProcessStageState getCandidatedState() {
	if (offer.isCandidancyPeriod()) {
	    return MobilityProcessStageState.UNDER_WAY;
	} else {
	    if (offer.isAfterCompletedCandidancyPeriod()) {
		return MobilityProcessStageState.COMPLETED;
	    } else {
		return MobilityProcessStageState.NOT_YET_UNDER_WAY;
	    }
	}
    }

    public JobOffer getOffer() {
	return offer;
    }

}
