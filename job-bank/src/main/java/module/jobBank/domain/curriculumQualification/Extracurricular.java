package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.beans.curriculumQualification.ExtracurricularBean;

public class Extracurricular extends Extracurricular_Base {

    public Extracurricular(ExtracurricularBean bean) {
        super();
        setBasicFields(bean);
        setBusinessArea(bean.getBusinessArea());
        setFunction(bean.getFunction());
        setMainActivities(bean.getMainActivities());
    }

    @Override
    public void removeQualification() {

    }
    @Deprecated
    public boolean hasBusinessArea() {
        return getBusinessArea() != null;
    }

    @Deprecated
    public boolean hasFunction() {
        return getFunction() != null;
    }

    @Deprecated
    public boolean hasMainActivities() {
        return getMainActivities() != null;
    }

}
