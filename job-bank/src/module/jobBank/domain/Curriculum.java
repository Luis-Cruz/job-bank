package module.jobBank.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.activity.CurriculumQualificationInformation;
import module.jobBank.domain.beans.CurriculumBean;
import net.sourceforge.fenixedu.domain.RemotePerson;
import net.sourceforge.fenixedu.domain.contacts.RemoteEmailAddress;
import net.sourceforge.fenixedu.domain.contacts.RemoteMobilePhone;
import net.sourceforge.fenixedu.domain.contacts.RemotePartyContact;
import net.sourceforge.fenixedu.domain.contacts.RemotePhone;
import net.sourceforge.fenixedu.domain.contacts.RemotePhysicalAddress;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
import pt.ist.fenixWebFramework.services.Service;

public class Curriculum extends Curriculum_Base {

    public Curriculum(Student student) {
	super();
	setStudent(student);
	loadExternalPersonnalData();
	setCurriculumProcess(new CurriculumProcess(this));
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

    @Service
    public void loadExternalData() {
	loadExternalPersonnalData();
	Set<RemoteRegistration> remoteRegistrations = new HashSet<RemoteRegistration>(getStudent().getRemoteStudent()
		.getSeniorRegistrationsForCurrentExecutionYear());
	Collection<RemoteRegistration> concludedRegistrationsForCurrentExecutionYear = getStudent().getRemoteStudent()
		.getConcludedRegistrationsForCurrentExecutionYear();
	if (concludedRegistrationsForCurrentExecutionYear != null && concludedRegistrationsForCurrentExecutionYear.size() != 0) {
	    remoteRegistrations.addAll(concludedRegistrationsForCurrentExecutionYear);
	}
	for (RemoteRegistration remoteRegistration : remoteRegistrations) {
	    getStudent().addOrUpdateRegistration(remoteRegistration);
	}
    }

    private void loadExternalPersonnalData() {
	RemotePerson remotePerson = getStudent().getRemotePerson();

	setDateOfBirth(remotePerson.getDateOfBirthYearMonthDay().toLocalDate());
	setNationality(remotePerson.getNationality().getName());

	for (RemotePartyContact remotePartyContact : remotePerson.getPartyContacts()) {
	    if (remotePartyContact.isPhysicalAddress()) {
		RemotePhysicalAddress remotePhysicalAddress = (RemotePhysicalAddress) remotePartyContact;
		if (remotePhysicalAddress.getDefaultContact()) {
		    setAddress(remotePhysicalAddress.getAddress());
		    setArea(remotePhysicalAddress.getArea());
		    setAreaCode(remotePhysicalAddress.getAreaCode());
		    setDistrictSubdivision(remotePhysicalAddress.getDistrictSubdivisionOfResidence());
		}
		continue;
	    }
	    if (remotePartyContact.isEmailAddress()) {
		RemoteEmailAddress remoteEmailAddress = (RemoteEmailAddress) remotePartyContact;
		if (remoteEmailAddress.getDefaultContact()) {
		    setEmail(remoteEmailAddress.getValue());
		}
		continue;
	    }
	    if (remotePartyContact.isMobile()) {
		RemoteMobilePhone remoteMobilePhone = (RemoteMobilePhone) remotePartyContact;
		if (remoteMobilePhone.getDefaultContact()) {
		    setMobilePhone(remoteMobilePhone.getNumber());
		}
		continue;
	    }
	    if (remotePartyContact.isPhone()) {
		RemotePhone remotePhone = (RemotePhone) remotePartyContact;
		if (remotePhone.getDefaultContact()) {
		    setPhone(remotePhone.getNumber());
		}
		continue;
	    }
	}
    }

    public boolean hasAnyDocument() {
	if (hasCurriculumProcess()) {
	    CurriculumProcess process = getCurriculumProcess();
	    return process.getFilesCount() > 0;
	}
	return false;
    }
}
