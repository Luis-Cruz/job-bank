package module.jobBank.domain.task;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.FenixCycleType;
import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import module.jobBank.domain.StudentRegistration;
import module.jobBank.domain.StudentRegistrationCycleType;
import module.organization.domain.Person;
import module.organizationIst.domain.listner.LoginListner;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import net.sourceforge.fenixedu.domain.RemoteDegree;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pt.ist.fenixframework.plugins.remote.domain.RemoteHost;

public class UpdateStudents extends UpdateStudents_Base {

    public UpdateStudents() {
	super();
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
    }

    @Override
    public void executeTask() {
	RemoteHost readRemoteHost = JobBankSystem.readRemoteHost();
	String studentsInfoForJobBank = RemoteRegistration.readAllStudentsInfoForJobBank(readRemoteHost);
	Set<StudentRegistration> updatedStudentRegistration = new HashSet<StudentRegistration>();

	JSONParser parser = new JSONParser();
	try {
	    JSONArray studentInfos = (JSONArray) parser.parse(studentsInfoForJobBank);
	    int i = 1;
	    for (Object studentInfo : studentInfos) {
		if (i % 1000 == 0) {
		    System.out.printf("importing %d of %d\n", i, studentInfos.size());
		}
		i++;
		JSONObject jsonStudentInfo = (JSONObject) studentInfo;
		final String username = (String) jsonStudentInfo.get("username");
		LoginListner.importUserInformation(username);
		User user = User.findByUsername(username);
		if (user != null && user.getPerson() != null) {
		    Student student = updateStudent(user.getPerson(), jsonStudentInfo);
		    updatedStudentRegistration.add(updateRegistration(readRemoteHost, student, jsonStudentInfo));
		}
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	for (StudentRegistration studentRegistration : JobBankSystem.getInstance().getStudentRegistration()) {
	    if (!updatedStudentRegistration.contains(studentRegistration)) {
		studentRegistration.setInactive();
	    }
	}

    }

    private Student updateStudent(Person person, JSONObject jsonStudentInfo) {
	Student student = person.getStudent();
	if (student == null) {
	    student = new Student(person.getUser());
	}
	student.getCurriculum().loadExternalPersonnalData(jsonStudentInfo);

	return student;
    }

    public StudentRegistration updateRegistration(RemoteHost readRemoteHost, Student student, JSONObject jsonStudentInfo) {
	final Integer number = Integer.parseInt((String) jsonStudentInfo.get("number"));
	final String remoteRegistrationOID = (String) jsonStudentInfo.get("remoteRegistrationOID");
	RemoteRegistration remoteRegistration = (RemoteRegistration) readRemoteHost.getRemoteDomainObject(remoteRegistrationOID);
	final String remoteDegreeOID = (String) jsonStudentInfo.get("degreeOID");
	RemoteDegree remoteDegree = (RemoteDegree) readRemoteHost.getRemoteDomainObject(remoteDegreeOID);
	FenixDegree fenixDegree = JobBankSystem.getInstance().getFenixDegreeFor(remoteDegree);
	final Boolean isConcluded = Boolean.parseBoolean((String) jsonStudentInfo.get("isConcluded"));
	final Integer curricularYear = Integer.parseInt((String) jsonStudentInfo.get("curricularYear"));

	if (remoteRegistration == null || fenixDegree == null) {
	    System.out.println(number + " NOT IMPORTED! REGISTRATION OID: " + remoteRegistrationOID + " DEGREE OID: "
		    + remoteDegreeOID);
	    return null;
	}

	StudentRegistration studentRegistration = student.getRegistrationFor(remoteRegistration);
	if (studentRegistration == null) {
	    studentRegistration = new StudentRegistration(student, remoteRegistration, fenixDegree, number, isConcluded,
		    curricularYear);
	} else {
	    studentRegistration.update(fenixDegree, number, isConcluded, curricularYear);
	}

	for (FenixCycleType fenixCycleType : FenixCycleType.values()) {
	    String averageString = (String) jsonStudentInfo.get(fenixCycleType.name());
	    StudentRegistrationCycleType studentRegistrationCycleType = studentRegistration
		    .getStudentRegistrationCycleType(fenixCycleType);
	    if (averageString != null) {
		if (studentRegistrationCycleType == null) {
		    studentRegistrationCycleType = new StudentRegistrationCycleType(studentRegistration, fenixCycleType);
		}
		studentRegistrationCycleType.setAverage(new BigDecimal(averageString));
	    } else if (studentRegistrationCycleType != null) {
		studentRegistrationCycleType.delete();
	    }
	}
	return studentRegistration;
    }
}
