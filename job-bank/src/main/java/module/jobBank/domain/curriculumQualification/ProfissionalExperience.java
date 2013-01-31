package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.beans.curriculumQualification.ProfissionalExperienceBean;

public class ProfissionalExperience extends ProfissionalExperience_Base {

	public ProfissionalExperience(ProfissionalExperienceBean bean) {
		super();
		setBasicFields(bean);
		setFunction(bean.getFunction());
		setMainActivities(bean.getMainActivities());
		setBusinessArea(bean.getBusinessArea());
	}

}
