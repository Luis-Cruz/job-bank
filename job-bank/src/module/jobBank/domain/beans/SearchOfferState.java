package module.jobBank.domain.beans;

import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.utils.IPredicate;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.util.Search;
import myorg.util.BundleUtil;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public class SearchOfferState extends Search<JobOfferProcess> {

    public enum OfferSearchState implements IPresentableEnum {
	ALL("all", "label.jobOfferSearch.all"), APPROVE("aprove", "label.jobOfferSearch.approve"), PENDING_TO_APPROVE(
		"pendingToApprove", "label.jobOfferSearch.pendingToApprove"), SELECTION("selection",
		"label.jobOfferSearch.selection"), PUBLICATION("publication", "label.jobOfferSearch.publication"), OLD("old",
		"label.jobOfferSearch.old"), CANCELED("canceled", "label.jobOfferSearch.canceled");

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
    private String enterprise;

    public SearchOfferState() {
	init();
    }

    public void init() {
	setOfferSearchState(OfferSearchState.ALL);
    }

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

    public void setEnterprise(String enterprise) {
	this.enterprise = enterprise;
    }

    public String getEnterprise() {
	return enterprise;
    }

    private boolean isSatisfiedEnterprise(JobOffer offer) {
	return StringUtils.isEmpty(getEnterprise()) || offer.getEnterpriseName() != null
		&& offer.getEnterpriseName().getContent().toLowerCase().contains(getEnterprise().toLowerCase());
    }

    private boolean isSatisfiedProcessNumber(JobOffer offer) {
	return StringUtils.isEmpty(getProcessNumber())
		|| offer.getJobOfferProcess().getProcessIdentification().contains(getProcessNumber());
    }

    private boolean isSatisfiedState(JobOffer jobOffer, User user) {
	return isJobOfferAll(jobOffer) || isJobOfferAproved(jobOffer) || isJobOfferPendingToAprove(jobOffer)
		|| isJobOfferOld(jobOffer) || isJobOfferSelection(jobOffer) || isJobOfferCanceled(jobOffer)
		|| isJobOfferPublication(jobOffer);
    }

    private boolean isJobOfferAll(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.ALL);
    }

    private boolean isJobOfferAproved(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.APPROVE) && jobOffer.isApproved();
    }

    private boolean isJobOfferPendingToAprove(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.PENDING_TO_APPROVE) && jobOffer.isPendingToApproval();
    }

    private boolean isJobOfferOld(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.OLD) && jobOffer.isConclued();

    }

    private boolean isJobOfferSelection(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.SELECTION) && jobOffer.isSelectionPeriod();
    }

    private boolean isJobOfferCanceled(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.CANCELED) && jobOffer.isCanceled();

    }

    private boolean isJobOfferPublication(JobOffer jobOffer) {
	return getOfferSearchState().equals(OfferSearchState.PUBLICATION) && jobOffer.isCandidancyPeriod();
    }

    @Override
    public Set<JobOfferProcess> search() {
	final User user = UserView.getCurrentUser();
	final Set<JobOfferProcess> jobOfferProcesses = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer jobOffer = object.getJobOffer();
		return isSatisfiedState(jobOffer, user) && isSatisfiedProcessNumber(jobOffer) && isSatisfiedEnterprise(jobOffer);
	    }
	});
	return jobOfferProcesses;
    }

}
