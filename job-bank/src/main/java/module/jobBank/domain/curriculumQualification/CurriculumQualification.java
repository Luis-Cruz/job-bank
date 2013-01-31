package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.beans.curriculumQualification.CurriculumQualificationBean;
import pt.ist.fenixWebFramework.services.Service;

public abstract class CurriculumQualification extends CurriculumQualification_Base {

	public CurriculumQualification() {
		super();
	}

	public CurriculumQualification(CurriculumQualificationBean bean) {
		super();
		setBasicFields(bean);
	}

	@Service
	public void removeQualification() {
		removeCurriculum();
	}

	public void setBasicFields(CurriculumQualificationBean bean) {
		setBeginDate(bean.getBeginDate());
		setEndDate(bean.getEndDate());
		setCity(bean.getCity());
		setInstitute(bean.getInstitute());
	}

}
