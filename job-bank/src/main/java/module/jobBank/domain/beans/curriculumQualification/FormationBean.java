package module.jobBank.domain.beans.curriculumQualification;

import java.io.Serializable;

import module.jobBank.domain.curriculumQualification.CurriculumQualification;
import module.jobBank.domain.curriculumQualification.Formation;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class FormationBean extends CurriculumQualificationBean implements Serializable {

    private MultiLanguageString formationArea;
    private MultiLanguageString degree;
    private MultiLanguageString acquiredSkills;

    public void setFormationArea(MultiLanguageString formationArea) {
        this.formationArea = formationArea;
    }

    public MultiLanguageString getFormationArea() {
        return formationArea;
    }

    public void setDegree(MultiLanguageString degree) {
        this.degree = degree;
    }

    public MultiLanguageString getDegree() {
        return degree;
    }

    public void setAcquiredSkills(MultiLanguageString acquiredSkills) {
        this.acquiredSkills = acquiredSkills;
    }

    public MultiLanguageString getAcquiredSkills() {
        return acquiredSkills;
    }

    @Override
    public CurriculumQualification create() {
        return new Formation(this);

    }

    @Override
    public boolean hasNeededInfo() {
        // TODO Auto-generated method stub
        return true;
    }

}