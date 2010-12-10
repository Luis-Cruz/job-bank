package module.jobBank.domain.beans;

import java.io.Serializable;

import module.jobBank.domain.Curriculum;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class CurriculumBean implements Serializable {

    private DateTime dateOfBirth;
    private String nationality;
    private String address;
    private String area;
    private String areaCode;
    private String districtSubdivision;
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

    public String getNationality() {
	return nationality;
    }

    public void setNationality(String nationality) {
	this.nationality = nationality;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getArea() {
	return area;
    }

    public void setArea(String area) {
	this.area = area;
    }

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public String getDistrictSubdivision() {
	return districtSubdivision;
    }

    public void setDistrictSubdivision(String districtSubdivision) {
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
