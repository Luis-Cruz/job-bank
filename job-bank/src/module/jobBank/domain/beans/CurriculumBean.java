package module.jobBank.domain.beans;

import java.io.Serializable;

import module.jobBank.domain.Curriculum;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class CurriculumBean implements Serializable {

    private DateTime dateOfBirth;
    private MultiLanguageString nationality;
    private MultiLanguageString address;
    private MultiLanguageString area;
    private MultiLanguageString areaCode;
    private MultiLanguageString districtSubdivision;
    private String mobilePhone;
    private String phone;
    private String email;
    private MultiLanguageString professionalStatus;
    private MultiLanguageString geographicAvailability;

    public CurriculumBean(Curriculum curriculum) {
	setDateOfBirth(curriculum.getDateOfBirth());
	setNationality(curriculum.getNationality());
	setAddress(curriculum.getAddress());
	setArea(curriculum.getArea());
	setAreaCode(curriculum.getAreaCode());
	setDistrictSubdivision(curriculum.getDistrictSubdivision());
	setMobilePhone(curriculum.getMobilePhone());
	setPhone(curriculum.getPhone());
	setEmail(curriculum.getEmail());
	setProfessionalStatus(curriculum.getProfessionalStatus());
	setGeographicAvailability(curriculum.getGeographicAvailability());
    }

    public DateTime getDateOfBirth() {
	return dateOfBirth;
    }

    public void setDateOfBirth(DateTime dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    public MultiLanguageString getNationality() {
	return nationality;
    }

    public void setNationality(MultiLanguageString nationality) {
	this.nationality = nationality;
    }

    public MultiLanguageString getAddress() {
	return address;
    }

    public void setAddress(MultiLanguageString address) {
	this.address = address;
    }

    public MultiLanguageString getArea() {
	return area;
    }

    public void setArea(MultiLanguageString area) {
	this.area = area;
    }

    public MultiLanguageString getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(MultiLanguageString areaCode) {
	this.areaCode = areaCode;
    }

    public MultiLanguageString getDistrictSubdivision() {
	return districtSubdivision;
    }

    public void setDistrictSubdivision(MultiLanguageString districtSubdivision) {
	this.districtSubdivision = districtSubdivision;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public MultiLanguageString getProfessionalStatus() {
	return professionalStatus;
    }

    public void setProfessionalStatus(MultiLanguageString professionalStatus) {
	this.professionalStatus = professionalStatus;
    }

    public MultiLanguageString getGeographicAvailability() {
	return geographicAvailability;
    }

    public void setGeographicAvailability(MultiLanguageString geographicAvailability) {
	this.geographicAvailability = geographicAvailability;
    }

}
