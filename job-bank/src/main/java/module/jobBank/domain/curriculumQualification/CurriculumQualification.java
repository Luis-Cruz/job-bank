package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.beans.curriculumQualification.CurriculumQualificationBean;
import pt.ist.fenixframework.Atomic;

public abstract class CurriculumQualification extends CurriculumQualification_Base {

    public CurriculumQualification() {
        super();
    }

    public CurriculumQualification(CurriculumQualificationBean bean) {
        super();
        setBasicFields(bean);
    }

    @Atomic
    public void removeQualification() {
        setCurriculum(null);
    }

    public void setBasicFields(CurriculumQualificationBean bean) {
        setBeginDate(bean.getBeginDate());
        setEndDate(bean.getEndDate());
        setCity(bean.getCity());
        setInstitute(bean.getInstitute());
    }

    @Deprecated
    public boolean hasBeginDate() {
        return getBeginDate() != null;
    }

    @Deprecated
    public boolean hasEndDate() {
        return getEndDate() != null;
    }

    @Deprecated
    public boolean hasInstitute() {
        return getInstitute() != null;
    }

    @Deprecated
    public boolean hasCity() {
        return getCity() != null;
    }

    @Deprecated
    public boolean hasCurriculum() {
        return getCurriculum() != null;
    }

}
