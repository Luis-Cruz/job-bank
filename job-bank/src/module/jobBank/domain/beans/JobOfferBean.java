package module.jobBank.domain.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.Degree;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferExternal;
import module.jobBank.domain.JobOfferInternal;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.enums.CandidacyType;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import net.sourceforge.fenixedu.domain.RemoteDegree;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;
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
    private MultiLanguageString function;
    private String place;
    private MultiLanguageString descriptionOffer;
    private MultiLanguageString requirements;
    private JobOfferType jobOfferType;
    private List<RemoteDegree> remoteDegrees;

    private String externalLink;
    private CandidacyType candidacyType;

    public JobOfferBean() {
	setBasicFields();
	setCandidacyType(CandidacyType.Internal);
	setRemoteDegrees(Degree.readRemoteDegrees());
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

    public MultiLanguageString getDescriptionOffer() {
	return descriptionOffer;
    }

    public void setDescriptionOffer(MultiLanguageString descriptionOffer) {
	this.descriptionOffer = descriptionOffer;
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

    public void setRemoteDegrees(List<RemoteDegree> remoteDegrees) {
	this.remoteDegrees = new ArrayList(remoteDegrees);
    }

    public List<RemoteDegree> getRemoteDegrees() {
	return remoteDegrees;
    }

    @Service
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
	setPlace(jobOffer.getPlace());
	setDescriptionOffer(jobOffer.getDescriptionOffer());
	setRequirements(jobOffer.getRequirements());
	setRemoteDegrees(jobOffer.getRemoteDegrees());
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
	    return new JobOfferBean((JobOfferInternal) jobOffer);
	}
	if (jobOffer instanceof JobOfferExternal) {
	    return new JobOfferBean((JobOfferExternal) jobOffer);
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

}
