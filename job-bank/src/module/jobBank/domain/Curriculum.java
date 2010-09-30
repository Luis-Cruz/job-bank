package module.jobBank.domain;

import module.jobBank.domain.activity.CurriculumQualificationInformation;
import module.jobBank.domain.beans.CurriculumBean;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class Curriculum extends Curriculum_Base {

    public Curriculum(Student student) {
	super();
	setStudent(student);
	setJobBankYear(JobBankYear.findJobBankYear(new DateTime().getYear()));
	new CurriculumProcess(this);
    }

    public void createCurriculumQualification(CurriculumQualificationInformation information) {
	addCurriculumQualification(information.getCurriculumQualficationBean().create());
    }

    @Service
    public void edit(CurriculumBean curriculumBean) {
	setDateOfBirth(curriculumBean.getDateOfBirth());
	setNationality(curriculumBean.getNationality());
	setAddress(curriculumBean.getAddress());
	setArea(curriculumBean.getArea());
	setAreaCode(curriculumBean.getAreaCode());
	setDistrictSubdivision(curriculumBean.getDistrictSubdivision());
	setMobilePhone(curriculumBean.getMobilePhone());
	setPhone(curriculumBean.getPhone());
	setEmail(curriculumBean.getEmail());
	setProfessionalStatus(curriculumBean.getProfessionalStatus());
	setGeographicAvailability(curriculumBean.getGeographicAvailability());

    }
}
