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
}
