package module.jobBank.domain.beans;

import java.io.Serializable;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferType;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOfferBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Enterprise enterprise;
    private DateTime creationDate;
    private DateTime beginDate;
    private DateTime endDate;
    private MultiLanguageString reference;
    private MultiLanguageString enterpriseName;
    private MultiLanguageString contactPerson;
    private MultiLanguageString function;
    private MultiLanguageString place;
    private MultiLanguageString descriptionOffer;
    private MultiLanguageString requirements;
    private MultiLanguageString degree;
    private String emailToSubmit;
    private JobOfferType jobOfferType;

    public JobOfferBean() {
	final User currentUser = UserView.getCurrentUser();
	Enterprise enterprise = Enterprise.readEnterprise(currentUser);
	setEnterprise(enterprise);
	setCreationDate(new DateTime());
	setEnterpriseName(enterprise.getName());
	setContactPerson(enterprise.getContactPerson());
    }

    public JobOfferBean(JobOffer jobOffer) {
	setCreationDate(jobOffer.getCreationDate());
	setBeginDate(jobOffer.getBeginDate());
	setEndDate(jobOffer.getEndDate());
	setEnterpriseName(jobOffer.getEnterpriseName());
	setContactPerson(jobOffer.getContactPerson());
	setReference(jobOffer.getReference());
	setFunction(jobOffer.getFunction());
	setPlace(jobOffer.getPlace());
	setDescriptionOffer(jobOffer.getDescriptionOffer());
	setRequirements(jobOffer.getRequirements());
	setEmailToSubmit(jobOffer.getEmailToSubmit());
	// setDegree(jobOffer.getDegree());
	setJobOfferType(jobOffer.getJobOfferType());
    }

    public DateTime getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
	this.creationDate = creationDate;
    }

    public DateTime getBeginDate() {
	return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
	this.beginDate = beginDate;
    }

    public DateTime getEndDate() {
	return endDate;
    }

    public void setEndDate(DateTime endDate) {
	this.endDate = endDate;
    }

    public MultiLanguageString getReference() {
	return reference;
    }

    public void setReference(MultiLanguageString reference) {
	this.reference = reference;
    }

    public MultiLanguageString getEnterpriseName() {
	return enterpriseName;
    }

    public void setEnterpriseName(MultiLanguageString enterpriseName) {
	this.enterpriseName = enterpriseName;
    }

    public MultiLanguageString getContactPerson() {
	return contactPerson;
    }

    public void setContactPerson(MultiLanguageString contactPerson) {
	this.contactPerson = contactPerson;
    }

    public MultiLanguageString getFunction() {
	return function;
    }

    public void setFunction(MultiLanguageString function) {
	this.function = function;
    }

    public MultiLanguageString getPlace() {
	return place;
    }

    public void setPlace(MultiLanguageString place) {
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

    public String getEmailToSubmit() {
	return emailToSubmit;
    }

    public void setEmailToSubmit(String emailToSubmit) {
	this.emailToSubmit = emailToSubmit;
    }

    public MultiLanguageString getDegree() {
	return degree;
    }

    public void setDegree(MultiLanguageString degree) {
	this.degree = degree;
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

    @Service
    public JobOffer create() {
	return new JobOffer(this);
    }
}
