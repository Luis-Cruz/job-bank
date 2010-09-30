package module.jobBank.domain.beans.curriculumQualification;

import java.io.Serializable;

import module.jobBank.domain.curriculumQualification.CurriculumQualification;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public abstract class CurriculumQualificationBean implements Serializable {

    private DateTime beginDate;
    private DateTime endDate;
    private MultiLanguageString institute;
    private MultiLanguageString city;

    public MultiLanguageString getInstitute() {
	return institute;
    }

    public void setInstitute(MultiLanguageString institute) {
	this.institute = institute;
    }

    public MultiLanguageString getCity() {
	return city;
    }

    public void setCity(MultiLanguageString city) {
	this.city = city;
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

    public boolean hasAllneededInfo() {
	// return hasValue(getInstitute()) &&
	// !StringUtils.isEmptyOrWhitespaceOnly(getInstitute().getContent()) &&
	// hasValue(city)
	// && !StringUtils.isEmptyOrWhitespaceOnly(getCity().getContent()) &&
	// hasValue(getCity());

	return hasNeededInfo();
    }

    protected boolean hasValue(MultiLanguageString value) {
	return value != null;
    }

    public abstract CurriculumQualification create();

    /**
     * Specify which fields needed to be filled
     */
    public abstract boolean hasNeededInfo();

}
