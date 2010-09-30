package module.jobBank.domain.beans;

import java.io.Serializable;
import java.util.Set;

import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.utils.IPredicate;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

import org.apache.commons.lang.StringUtils;

public class Search implements Serializable {

    private String reference;
    private String enterprise;
    private JobOfferType jobOfferType;
    private String function;

    // private String degree;

    public String getReference() {
	return reference;
    }

    public void setReference(String reference) {
	this.reference = reference;
    }

    public String getEnterprise() {
	return enterprise;
    }

    public void setEnterprise(String enterprise) {
	this.enterprise = enterprise;
    }

    public JobOfferType getJobOfferType() {
	return jobOfferType;
    }

    public void setJobOfferType(JobOfferType jobOfferType) {
	this.jobOfferType = jobOfferType;
    }

    public String getFunction() {
	return function;
    }

    public void setFunction(String function) {
	this.function = function;
    }

    public Set<JobOfferProcess> doSearch() {
	final User user = UserView.getCurrentUser();
	final Set<JobOfferProcess> jobOfferProcesses = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer offer = object.getJobOffer();
		return !offer.isCanceled() && offer.isCandidancyPeriod() && isSatisfiedProcessNumber(offer) && isSatisfiedEnterprise(offer)
			&& isSatisfiedFunction(offer) && isSatisfiedJobOfferType(offer);
	    }
	});
	return jobOfferProcesses;
    }

    private boolean isSatisfiedProcessNumber(JobOffer offer) {
	return StringUtils.isEmpty(getReference())
		|| offer.getJobOfferProcess().getProcessIdentification().contains(getReference());
    }

    /*
     * private boolean isSatisfiedCreationDate(JobOffer offer) { return
     * getCreationDate() == null || (getCreationDate() != null &&
     * offer.getCreationDate().equals(getCreationDate())); }
     */

    private boolean isSatisfiedEnterprise(JobOffer offer) {
	return StringUtils.isEmpty(getEnterprise()) || offer.getEnterpriseName() != null
		&& offer.getEnterpriseName().getContent().contains(getEnterprise());
    }

    private boolean isSatisfiedJobOfferType(JobOffer offer) {
	return getJobOfferType() == null || offer.getJobOfferType() == getJobOfferType();
    }

    private boolean isSatisfiedFunction(JobOffer offer) {
	return StringUtils.isEmpty(getFunction()) || offer.getFunction() != null
		&& offer.getFunction().getContent().contains(getFunction());
    }
}
