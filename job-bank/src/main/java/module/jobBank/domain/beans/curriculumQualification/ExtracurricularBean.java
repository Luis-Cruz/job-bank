package module.jobBank.domain.beans.curriculumQualification;

import java.io.Serializable;

import module.jobBank.domain.curriculumQualification.Extracurricular;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class ExtracurricularBean extends CurriculumQualificationBean implements Serializable {

    private MultiLanguageString businessArea;
    private MultiLanguageString function;
    private MultiLanguageString mainActivities;

    public MultiLanguageString getBusinessArea() {
	return businessArea;
    }

    public void setBusinessArea(MultiLanguageString businessArea) {
	this.businessArea = businessArea;
    }

    public MultiLanguageString getFunction() {
	return function;
    }

    public void setFunction(MultiLanguageString function) {
	this.function = function;
    }

    public MultiLanguageString getMainActivities() {
	return mainActivities;
    }

    public void setMainActivities(MultiLanguageString mainActivities) {
	this.mainActivities = mainActivities;
    }

    @Override
    public Extracurricular create() {
	return new Extracurricular(this);

    }

    @Override
    public boolean hasNeededInfo() {
	// TODO Auto-generated method stub
	return true;
    }
}