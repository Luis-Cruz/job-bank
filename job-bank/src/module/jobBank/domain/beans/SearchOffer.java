package module.jobBank.domain.beans;

import java.util.Set;
import java.util.StringTokenizer;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.util.Search;

import org.apache.commons.lang.StringUtils;

import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class SearchOffer extends Search<JobOfferProcess> {

    private String query;
    private JobOfferType jobOfferType;
    private FenixDegree degree;

    public SearchOffer() {
	setQuery(StringUtils.EMPTY);
    }

    public JobOfferType getJobOfferType() {
	return jobOfferType;
    }

    public void setJobOfferType(JobOfferType jobOfferType) {
	this.jobOfferType = jobOfferType;
    }

    public void setDegrees(FenixDegree degree) {
	this.degree = degree;
    }

    public FenixDegree getDegrees() {
	return degree;
    }

    @Override
    public Set<JobOfferProcess> search() {
	final User user = UserView.getCurrentUser();
	Set<JobOfferProcess> jobOfferProcesses = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer offer = object.getJobOffer();
		return offer.isActive() && offer.isCandidancyPeriod() && isSatisfiedJobOfferType(offer)
			&& isSatisfiedDegres(offer);
	    }
	});

	// Search Query
	StringTokenizer tokens = getTokens();
	while (tokens.hasMoreElements()) {
	    jobOfferProcesses = matchQuery(tokens.nextElement().toString(), jobOfferProcesses);
	}

	return jobOfferProcesses;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public String getQuery() {
	return query;
    }

    public StringTokenizer getTokens() {
	String delim = " ";
	return new StringTokenizer(getQuery() == null ? StringUtils.EMPTY : getQuery(), delim);
    }

    public boolean isEmptyQuery() {
	return !getTokens().hasMoreElements();
    }

    private Set<JobOfferProcess> matchQuery(final String key, Set<JobOfferProcess> jobOfferProcesses) {
	return Utils.readValuesToSatisfiedPredicate(new IPredicate<JobOfferProcess>() {

	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		JobOffer jobOffer = object.getJobOffer();
		return isSatisfiedQuery(key, jobOffer);
	    }
	}, jobOfferProcesses);

    }

    private boolean isSatisfiedQuery(String key, JobOffer offer) {
	return isEmptyQuery() || match(key, offer.getEnterpriseName().getContent())
		|| match(key, offer.getFunction().getContent()) || match(key, offer.getPlace());
    }

    private boolean isSatisfiedJobOfferType(JobOffer offer) {
	return getJobOfferType() == null || offer.getJobOfferType() == getJobOfferType();
    }

    private boolean isSatisfiedDegres(JobOffer offer) {
	return getDegrees() == null || offer.getDegree().contains(getDegrees());
    }

    private boolean match(String key, String value) {
	return StringNormalizer.normalize(value).contains(StringNormalizer.normalize(key));
    }
}
