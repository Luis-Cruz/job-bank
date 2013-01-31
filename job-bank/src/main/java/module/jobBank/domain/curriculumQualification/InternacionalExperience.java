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

}
