package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.beans.curriculumQualification.FormationBean;

public class Formation extends Formation_Base {

    public Formation(FormationBean bean) {
        super();
        setBasicFields(bean);
        setFormationArea(bean.getFormationArea());
        setDegree(bean.getDegree());
        setAcquiredSkills(bean.getAcquiredSkills());
    }

    @Deprecated
    public boolean hasFormationArea() {
        return getFormationArea() != null;
    }

    @Deprecated
    public boolean hasDegree() {
        return getDegree() != null;
    }

    @Deprecated
    public boolean hasAcquiredSkills() {
        return getAcquiredSkills() != null;
    }

}
