package module.jobBank.domain;

import org.joda.time.LocalDate;
import org.json.simple.JSONObject;

import pt.ist.fenixWebFramework.services.Service;

import module.jobBank.domain.activity.CurriculumQualificationInformation;
import module.jobBank.domain.beans.CurriculumBean;

public class Curriculum extends Curriculum_Base {

    public Curriculum(Student student) {
	super();
	setStudent(student);
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
	// Set<StudentRegistration> updatedStudentRegistration = new
	// HashSet<StudentRegistration>();
	// String studentInfoForJobBank =
	// getStudent().getRemoteStudent().readAllStudentInfoForJobBank();
	//
	// JSONParser parser = new JSONParser();
	// try {
	// JSONArray studentInfos = (JSONArray)
	// parser.parse(studentInfoForJobBank);
	// int i = 1;
	// for (Object studentInfo : studentInfos) {
	// if (i % 1000 == 0) {
	// System.out.printf("importing %d of %d\n", i, studentInfos.size());
	// }
	// i++;
	// JSONObject jsonStudentInfo = (JSONObject) studentInfo;
	// final String username = (String) jsonStudentInfo.get("username");
	// LoginListner.importUserInformation(username);
	// User user = User.findByUsername(username);
	// if (user != null && user.getPerson() != null) {
	// loadExternalPersonnalData(jsonStudentInfo);
	// updatedStudentRegistration.add(loadExternalCurriculumData(jsonStudentInfo));
	// }
	// }
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	//
	// for (StudentRegistration studentRegistration :
	// getStudent().getStudentRegistration()) {
	// if (!updatedStudentRegistration.contains(studentRegistration)) {
	// studentRegistration.setInactive();
	// }
	// }
    }

    private StudentRegistration loadExternalCurriculumData(JSONObject jsonStudentInfo) {
	// RemoteHost readRemoteHost =
	// JobBankSystem.getInstance().readRemoteHost();
	// final Integer number = Integer.parseInt((String)
	// jsonStudentInfo.get("number"));
	// final String remoteRegistrationOID = (String)
	// jsonStudentInfo.get("remoteRegistrationOID");
	// RemoteRegistration remoteRegistration = (RemoteRegistration)
	// readRemoteHost.getRemoteDomainObject(remoteRegistrationOID);
	//
	// final String remoteDegreeOID = (String)
	// jsonStudentInfo.get("degreeOID");
	// RemoteDegree remoteDegree = (RemoteDegree)
	// readRemoteHost.getRemoteDomainObject(remoteDegreeOID);
	// FenixDegree fenixDegree =
	// JobBankSystem.getInstance().getFenixDegreeFor(remoteDegree);
	// final Boolean isConcluded = Boolean.parseBoolean((String)
	// jsonStudentInfo.get("isConcluded"));
	// final Integer curricularYear = Integer.parseInt((String)
	// jsonStudentInfo.get("curricularYear"));
	//
	// if (remoteRegistration == null || fenixDegree == null) {
	// System.out.println(number + "NOT IMPORTED! REGISTRATION OID: " +
	// remoteRegistrationOID + " DEGREE OID: "
	// + remoteDegreeOID);
	// return null;
	// }
	//
	// StudentRegistration studentRegistration =
	// getStudent().getRegistrationFor(remoteRegistration);
	// if (studentRegistration == null) {
	// studentRegistration = new StudentRegistration(getStudent(),
	// remoteRegistration, fenixDegree, number, isConcluded,
	// curricularYear);
	// } else {
	// studentRegistration.update(fenixDegree, number, isConcluded,
	// curricularYear);
	// }
	//
	// for (FenixCycleType fenixCycleType : FenixCycleType.values()) {
	// String averageString = (String)
	// jsonStudentInfo.get(fenixCycleType.name());
	// StudentRegistrationCycleType studentRegistrationCycleType =
	// studentRegistration
	// .getStudentRegistrationCycleType(fenixCycleType);
	// if (averageString != null) {
	// if (studentRegistrationCycleType == null) {
	// studentRegistrationCycleType = new
	// StudentRegistrationCycleType(studentRegistration, fenixCycleType);
	// }
	// studentRegistrationCycleType.setAverage(new
	// BigDecimal(averageString));
	// } else if (studentRegistrationCycleType != null) {
	// studentRegistrationCycleType.delete();
	// }
	// }
	// return studentRegistration;
	return null;
    }

    public void loadExternalPersonnalData(JSONObject jsonStudentInfo) {
	final Boolean hasPersonalDataAuthorization = Boolean.parseBoolean((String) jsonStudentInfo
		.get("hasPersonalDataAuthorization"));

	final String dateOfBirthString = (String) jsonStudentInfo.get("dateOfBirth");
	final LocalDate dateOfBirth = dateOfBirthString == null ? null : new LocalDate(dateOfBirthString);
	final String nationality = (String) jsonStudentInfo.get("nationality");

	final String address = (String) jsonStudentInfo.get("address");
	final String area = (String) jsonStudentInfo.get("area");
	final String areaCode = (String) jsonStudentInfo.get("areaCode");
	final String districtSubdivisionOfResidence = (String) jsonStudentInfo.get("districtSubdivisionOfResidence");

	final String email = (String) jsonStudentInfo.get("email");
	final String mobilePhone = (String) jsonStudentInfo.get("mobilePhone");
	final String phone = (String) jsonStudentInfo.get("phone");

	getStudent().setHasPersonalDataAuthorization(hasPersonalDataAuthorization);
	setDateOfBirth(dateOfBirth);
	setNationality(nationality);
	setAddress(address);
	setArea(area);
	setAreaCode(areaCode);
	setDistrictSubdivision(districtSubdivisionOfResidence);
	setPhone(phone);
	setMobilePhone(mobilePhone);
	setEmail(email);
    }

    public boolean hasAnyDocument() {
	if (hasCurriculumProcess()) {
	    CurriculumProcess process = getCurriculumProcess();
	    return process.getFilesCount() > 0;
	}
	return false;
    }
}
