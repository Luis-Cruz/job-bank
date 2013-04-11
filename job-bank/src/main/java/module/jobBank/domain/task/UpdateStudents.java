package module.jobBank.domain.task;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.FenixCycleType;
import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import module.jobBank.domain.StudentAuthorization;
import module.jobBank.domain.StudentRegistration;
import module.jobBank.domain.StudentRegistrationCycleType;
import module.jobBank.domain.beans.StudentAuthorizationBean;
import module.jobBank.domain.utils.LoginListner;
import module.organization.domain.Person;
import module.webserviceutils.domain.HostSystem;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;

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
        Set<StudentRegistration> updatedStudentRegistration = new HashSet<StudentRegistration>();
        String studentsInfoForJobBank = HostSystem.getFenixJerseyClient().method("readAllStudentsInfoForJobBank").get();
        try {
            updatedStudentRegistration.addAll(updateStudents(studentsInfoForJobBank));

            for (StudentAuthorization studentAuthorization : new StudentAuthorizationBean().search()) {
                String authorizedStudentInfoForJobBank =
                        HostSystem.getFenixJerseyClient().method("readStudentInfoForJobBank")
                                .arg("username", studentAuthorization.getStudent().getPerson().getUser().getUsername()).get();
                updatedStudentRegistration.addAll(updateStudents(authorizedStudentInfoForJobBank));
            }

            for (StudentRegistration studentRegistration : JobBankSystem.getInstance().getStudentRegistration()) {
                if (!updatedStudentRegistration.contains(studentRegistration)) {
                    studentRegistration.setInactive();
                }
            }
        } finally {
        }
    }

    protected Set<StudentRegistration> updateStudents(String studentsInfoForJobBank) {
        Set<StudentRegistration> updatedStudentRegistration = new HashSet<StudentRegistration>();
        try {
            JSONParser parser = new JSONParser();
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
                    updatedStudentRegistration.add(updateRegistration(student, jsonStudentInfo));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedStudentRegistration;
    }

    private Student updateStudent(Person person, JSONObject jsonStudentInfo) {
        Student student = person.getStudent();
        if (student == null) {
            student = new Student(person.getUser());
        }
        student.getCurriculum().loadExternalPersonnalData(jsonStudentInfo);

        return student;
    }

    public StudentRegistration updateRegistration(Student student, JSONObject jsonStudentInfo) {
        final Integer number = Integer.parseInt((String) jsonStudentInfo.get("number"));
        final String remoteRegistrationOID = (String) jsonStudentInfo.get("remoteRegistrationOID");
        final String remoteDegreeOID = (String) jsonStudentInfo.get("degreeOID");
        FenixDegree fenixDegree = FenixDegree.getFenixDegreeByExternalId(remoteDegreeOID);
        final Boolean isConcluded = Boolean.parseBoolean((String) jsonStudentInfo.get("isConcluded"));
        final Integer curricularYear = Integer.parseInt((String) jsonStudentInfo.get("curricularYear"));

        if (StringUtils.isEmpty(remoteRegistrationOID) || fenixDegree == null) {
            System.out.println(number + " NOT IMPORTED! REGISTRATION OID: " + remoteRegistrationOID + " DEGREE OID: "
                    + remoteDegreeOID);
            return null;
        }

        StudentRegistration studentRegistration = student.getRegistrationFor(remoteRegistrationOID);
        if (studentRegistration == null) {
            studentRegistration =
                    new StudentRegistration(student, remoteRegistrationOID, fenixDegree, number, isConcluded, curricularYear);
        } else {
            studentRegistration.update(fenixDegree, number, isConcluded, curricularYear);
        }

        for (FenixCycleType fenixCycleType : FenixCycleType.values()) {
            String averageString = (String) jsonStudentInfo.get(fenixCycleType.name());
            StudentRegistrationCycleType studentRegistrationCycleType =
                    studentRegistration.getStudentRegistrationCycleType(fenixCycleType);
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
