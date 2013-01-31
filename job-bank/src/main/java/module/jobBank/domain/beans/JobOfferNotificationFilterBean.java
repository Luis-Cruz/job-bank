package module.jobBank.domain.beans;

import java.io.Serializable;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobOfferType;

public class JobOfferNotificationFilterBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FenixDegree degree;
	private JobOfferType jobOfferType;

	public JobOfferNotificationFilterBean() {
		degree = null;
		jobOfferType = null;
	}

	public void setDegree(FenixDegree degree) {
		this.degree = degree;
	}

	public FenixDegree getDegree() {
		return degree;
	}

	public void setJobOfferType(JobOfferType jobOfferType) {
		this.jobOfferType = jobOfferType;
	}

	public JobOfferType getJobOfferType() {
		return jobOfferType;
	}

}
