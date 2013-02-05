package module.jobBank.domain.beans.curriculumQualification;

import java.io.Serializable;

import module.jobBank.domain.curriculumQualification.CurriculumQualification;
import module.jobBank.domain.curriculumQualification.ProfissionalExperience;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class ProfissionalExperienceBean extends CurriculumQualificationBean implements Serializable {

    private MultiLanguageString businessArea;
    private MultiLanguageString function;
    private MultiLanguageString mainActivities;

    public MultiLanguageString getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(MultiLanguageString businessArea) {
        this.businessArea = businessArea;
    }

    public void setFunction(MultiLanguageString function) {
        this.function = function;
    }

    public MultiLanguageString getFunction() {
        return function;
    }

    public void setMainActivities(MultiLanguageString mainActivities) {
        this.mainActivities = mainActivities;
    }

    public MultiLanguageString getMainActivities() {
        return mainActivities;
    }

    @Override
    public CurriculumQualification create() {
        return new ProfissionalExperience(this);

    }

    @Override
    public boolean hasNeededInfo() {
        // TODO Auto-generated method stub
        return getBusinessArea() != null;
    }
}