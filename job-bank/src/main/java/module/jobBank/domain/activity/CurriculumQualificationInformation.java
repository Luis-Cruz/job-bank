package module.jobBank.domain.activity;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.beans.curriculumQualification.CurriculumQualificationBean;
import module.jobBank.domain.beans.curriculumQualification.ExtracurricularBean;
import module.jobBank.domain.beans.curriculumQualification.FormationBean;
import module.jobBank.domain.beans.curriculumQualification.InternacionalExperienceBean;
import module.jobBank.domain.beans.curriculumQualification.ProfissionalExperienceBean;
import module.jobBank.domain.curriculumQualification.CurriculumQualificationType;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class CurriculumQualificationInformation extends ActivityInformation<CurriculumProcess> {

    private static final long serialVersionUID = 1L;

    private CurriculumQualificationBean curriculumQualficationBean;
    private CurriculumQualificationType qualificationType;

    public CurriculumQualificationInformation(final CurriculumProcess curriculumProcess,
	    WorkflowActivity<CurriculumProcess, ? extends ActivityInformation<CurriculumProcess>> activity) {
	super(curriculumProcess, activity);
	createBean();
    }

    private void createBean() {
	if (!hasQualficationType()) {
	    setCurriculumQualficationBean(new ProfissionalExperienceBean());
	    setQualificationType(CurriculumQualificationType.PROFISSIONAL_EXPERIENCE);
	    return;
	}

	if (getQualificationType().equals(CurriculumQualificationType.PROFISSIONAL_EXPERIENCE)) {
	    setCurriculumQualficationBean(new ProfissionalExperienceBean());
	}
	if (getQualificationType().equals(CurriculumQualificationType.INTERNACIONAL_EXPERIENCE)) {
	    setCurriculumQualficationBean(new InternacionalExperienceBean());
	}
	if (getQualificationType().equals(CurriculumQualificationType.EXTRACURRICULAR)) {
	    setCurriculumQualficationBean(new ExtracurricularBean());
	}
	if (getQualificationType().equals(CurriculumQualificationType.FORMATION)) {
	    setCurriculumQualficationBean(new FormationBean());
	}
    }

    public CurriculumQualificationBean getCurriculumQualficationBean() {
	return curriculumQualficationBean;
    }

    public void setCurriculumQualficationBean(CurriculumQualificationBean curriculumQualficationBean) {
	this.curriculumQualficationBean = curriculumQualficationBean;
    }

    public CurriculumQualificationType getQualificationType() {
	return qualificationType;
    }

    public void setQualificationType(CurriculumQualificationType qualificationType) {
	if ((this.qualificationType != null && this.qualificationType != qualificationType || curriculumQualficationBean == null)) {
	    this.qualificationType = qualificationType;
	    createBean();
	} else {
	    this.qualificationType = qualificationType;
	}
    }

    public boolean hasQualficationType() {
	return getQualificationType() != null;
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput() && curriculumQualficationBean.hasAllneededInfo();
    }

    @Override
    public String getUsedSchema() {
	return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
    }

    public void create() {
	getProcess().getCurriculum().createCurriculumQualification(this);
    }

}
