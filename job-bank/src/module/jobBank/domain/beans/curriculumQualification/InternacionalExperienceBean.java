package module.jobBank.domain.beans.curriculumQualification;

import java.io.Serializable;

import module.jobBank.domain.curriculumQualification.CurriculumQualification;
import module.jobBank.domain.curriculumQualification.InternacionalExperience;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class InternacionalExperienceBean extends CurriculumQualificationBean implements Serializable {

    private MultiLanguageString country;
    private MultiLanguageString program;
    private MultiLanguageString mainActivities;

    public MultiLanguageString getCountry() {
	return country;
    }

    public void setCountry(MultiLanguageString country) {
	this.country = country;
    }

    public MultiLanguageString getProgram() {
	return program;
    }

    public void setProgram(MultiLanguageString program) {
	this.program = program;
    }

    public MultiLanguageString getMainActivities() {
	return mainActivities;
    }

    public void setMainActivities(MultiLanguageString mainActivities) {
	this.mainActivities = mainActivities;
    }

    @Override
    public CurriculumQualification create() {
	return new InternacionalExperience(this);

    }

    @Override
    public boolean hasNeededInfo() {
	// TODO Auto-generated method stub
	return true;
    }
}