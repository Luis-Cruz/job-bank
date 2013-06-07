package module.jobBank.domain.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.Degree;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferExternal;
import module.jobBank.domain.JobOfferInternal;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.enums.CandidacyType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.fenixframework.Atomic;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOfferBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Enterprise enterprise;
	private DateTime creationDate;
	private LocalDate beginDate;
	private LocalDate endDate;
	private Integer vacancies;
	private String reference;
	private String contactPerson;
	private String place;
	private MultiLanguageString function;
	private MultiLanguageString functionDescription;
	private MultiLanguageString requirements;
	private MultiLanguageString terms;
	private JobOfferType jobOfferType;
	private List<FenixDegree> degrees;

	private String externalLink;
	private CandidacyType candidacyType;
	private CandidacyType previousCandidacyType;

	public JobOfferBean() {
		setBasicFields();
		setCandidacyType(CandidacyType.Internal);
		ArrayList<FenixDegree> degrees = new ArrayList<FenixDegree>();
		degrees.addAll(Degree.readActiveFenixDegreeSet());
		setDegrees(degrees);
		// min
		setVacancies(1);
	}

	public JobOfferBean(JobOfferInternal jobOffer) {
		setForm(jobOffer);
	}

	public JobOfferBean(JobOfferExternal jobOffer) {
		setForm(jobOffer);
		setExternalLink(jobOffer.getExternalLink());
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public MultiLanguageString getFunction() {
		return function;
	}

	public void setFunction(MultiLanguageString function) {
		this.function = function;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public MultiLanguageString getTerms() {
		return terms;
	}

	public void setTerms(MultiLanguageString terms) {
		this.terms = terms;
	}

	public MultiLanguageString getRequirements() {
		return requirements;
	}

	public void setRequirements(MultiLanguageString requirements) {
		this.requirements = requirements;
	}

	public JobOfferType getJobOfferType() {
		return jobOfferType;
	}

	public void setJobOfferType(JobOfferType jobOfferType) {
		this.jobOfferType = jobOfferType;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public void setCandidacyType(CandidacyType candidacyType) {
		this.candidacyType = candidacyType;
	}

	public CandidacyType getCandidacyType() {
		return candidacyType;
	}

	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}

	public String getExternalLink() {
		return externalLink;
	}

	@Atomic
	public JobOffer create() {
		if (getCandidacyType() == null) {
			return new JobOfferInternal(this);
		}
		if (getCandidacyType() == CandidacyType.Internal) {
			return new JobOfferInternal(this);
		}
		if (getCandidacyType() == CandidacyType.External) {
			return new JobOfferExternal(this);
		}
		return null;
	}

	protected void setForm(JobOffer jobOffer) {
		setCreationDate(jobOffer.getCreationDate());
		setBeginDate(jobOffer.getBeginDate());
		setEndDate(jobOffer.getEndDate());
		setVacancies(jobOffer.getVacancies());
		setContactPerson(jobOffer.getContactPerson());
		setReference(jobOffer.getReference());
		setFunction(jobOffer.getFunction());
		setFunctionDescription(jobOffer.getFunctionDescription());
		setPlace(jobOffer.getPlace());
		setTerms(jobOffer.getTerms());
		setRequirements(jobOffer.getRequirements());
		setDegrees(new ArrayList<FenixDegree>(jobOffer.getDegreeSet()));
		setJobOfferType(jobOffer.getJobOfferType());
	}

	protected void setBasicFields() {
		final User currentUser = UserView.getCurrentUser();
		Enterprise enterprise = Enterprise.readEnterprise(currentUser);
		setEnterprise(enterprise);
		setCreationDate(new DateTime());
		setContactPerson(enterprise.getContactPerson());
	}

	public static JobOfferBean createJobOfferBean(JobOffer jobOffer) {
		if (jobOffer instanceof JobOfferInternal) {
			JobOfferBean bean = new JobOfferBean((JobOfferInternal) jobOffer);
			bean.setCandidacyType(CandidacyType.Internal);
			return bean;
		}
		if (jobOffer instanceof JobOfferExternal) {
			JobOfferBean bean = new JobOfferBean((JobOfferExternal) jobOffer);
			bean.setCandidacyType(CandidacyType.External);
			return bean;
		}
		return null;
	}

	public void setVacancies(Integer vacancies) {
		this.vacancies = vacancies;
	}

	public Integer getVacancies() {
		return vacancies;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

	public void setDegrees(List<FenixDegree> degrees) {
		this.degrees = degrees;
	}

	public List<FenixDegree> getDegrees() {
		return degrees;
	}

	public void setPreviousCandidacyType(CandidacyType previousCandidacyType) {
		this.previousCandidacyType = previousCandidacyType;
	}

	public CandidacyType getPreviousCandidacyType() {
		return previousCandidacyType;
	}

	public MultiLanguageString getFunctionDescription() {
		return functionDescription;
	}

	public void setFunctionDescription(MultiLanguageString functionDescription) {
		this.functionDescription = functionDescription;
	}

}
