package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.beans.curriculumQualification.InternacionalExperienceBean;

public class InternacionalExperience extends InternacionalExperience_Base {

    public InternacionalExperience(InternacionalExperienceBean bean) {
        super();
        setBasicFields(bean);
        setCountry(bean.getCountry());
        setMainActivities(bean.getMainActivities());
        setProgram(bean.getProgram());
    }

    @Deprecated
    public boolean hasCountry() {
        return getCountry() != null;
    }

    @Deprecated
    public boolean hasProgram() {
        return getProgram() != null;
    }

    @Deprecated
    public boolean hasMainActivities() {
        return getMainActivities() != null;
    }

}
