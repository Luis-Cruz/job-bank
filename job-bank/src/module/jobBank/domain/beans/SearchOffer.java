package module.jobBank.domain.beans;

import java.io.Serializable;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.utils.IPredicate;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public class SearchOffer implements Serializable {

    public enum OfferSearchState implements IPresentableEnum {
	ALL("all", "label.jobOfferSearch.all"), APPROVE("aprove", "label.jobOfferSearch.approve"), PENDING_TO_APPROVE(
		"pendingToApprove", "label.jobOfferSearch.pendingToApprove"), OLD("old", "label.jobOfferSearch.old");

	private final String nameKey;
	private final String type;

	private OfferSearchState(String type, String nameKey) {
	    this.type = type;
	    this.nameKey = nameKey;
	}

	public String getType() {
	    return type;
	}

	@Override
	public String getLocalizedName() {
	    return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, nameKey);
	}

    }

    private OfferSearchState offerSearchState;
    private String processNumber;

    public String getProcessNumber() {
	return processNumber;
    }

    public void setProcessNumber(String processNumber) {
	this.processNumber = processNumber;
    }

    public OfferSearchState getOfferSearchState() {
	return offerSearchState;
    }

    public void setOfferSearchState(OfferSearchState offerSearchState) {
	this.offerSearchState = offerSearchState;
    }

    public SearchOffer() {
	init();
    }

    public void init() {
	setOfferSearchState(OfferSearchState.ALL);
    }

    public Set<JobOfferProcess> doSearch() {
	final User user = UserView.getCurrentUser();
	final Set<JobOfferProcess> jobOfferProcesses = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer jobOffer = object.getJobOffer();
		return  !jobOffer.isCanceled() && isSatisfiedState(jobOffer, user) && isSatisfiedProcessNumber(jobOffer);
	    }
	});
	return jobOfferProcesses;
    }

    private boolean isSatisfiedProcessNumber(JobOffer offer) {
	return StringUtils.isEmpty(getProcessNumber())
		|| offer.getJobOfferProcess().getProcessIdentification().contains(getProcessNumber());
    }

    private boolean isSatisfiedState(JobOffer jobOffer, User user) {
	return isJobOfferAll(jobOffer) || isJobOfferAproved(jobOffer) || isJobOfferPendingToAprove(jobOffer)
		|| isJobOfferOld(jobOffer);
    }

    private boolean isJobOfferAll(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.ALL) && (jobOffer.isApproved() || jobOffer.isPendingToApproval());
    }

    private boolean isJobOfferAproved(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.APPROVE) && jobOffer.isApproved();
    }

    private boolean isJobOfferPendingToAprove(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.PENDING_TO_APPROVE) && jobOffer.isPendingToApproval();
    }

    private boolean isJobOfferOld(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.OLD) && jobOffer.isAfterCompletedCandidancyPeriod();
    }
}
