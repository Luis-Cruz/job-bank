package module.jobBank.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.activity.CurriculumQualificationInformation;
import module.jobBank.domain.beans.CurriculumBean;
import module.jobBank.domain.utils.LoginListner;
import module.webserviceutils.domain.HostSystem;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pt.ist.bennu.core.domain.User;
import pt.ist.fenixframework.Atomic;

public class Curriculum extends Curriculum_Base {

    public Curriculum(Student student) {
        super();
        setStudent(student);
        setCurriculumProcess(new CurriculumProcess(this));
    }

    public void createCurriculumQualification(CurriculumQualificationInformation information) {
        addCurriculumQualification(information.getCurriculumQualficationBean().create());
    }

    @Atomic
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

    @Atomic
    public void loadExternalData() {
        Set<StudentRegistration> updatedStudentRegistration = new HashSet<StudentRegistration>();
        String studentInfoForJobBank =
                HostSystem.getFenixJerseyClient().method("readActiveStudentInfoForJobBank")
                        .arg("username", getStudent().getPerson().getUser().getUsername()).get();
        updatedStudentRegistration.addAll(updateStudent(studentInfoForJobBank));

        if (getStudent().getActiveStudentAuthorization() != null) {
            String authorizedStudentInfoForJobBank =
                    HostSystem.getFenixJerseyClient().method("readStudentInfoForJobBank")
                            .arg("username", getStudent().getPerson().getUser().getUsername()).get();
            updatedStudentRegistration.addAll(updateStudent(authorizedStudentInfoForJobBank));
        }

        for (StudentRegistration studentRegistration : getStudent().getStudentRegistration()) {
            if (!updatedStudentRegistration.contains(studentRegistration)) {
                studentRegistration.setInactive();
            }
        }
    }

    protected Set<StudentRegistration> updateStudent(String studentInfoForJobBank) {
        Set<StudentRegistration> updatedStudentRegistration = new HashSet<StudentRegistration>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray studentInfos = (JSONArray) parser.parse(studentInfoForJobBank);
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
                    loadExternalPersonnalData(jsonStudentInfo);
                    updatedStudentRegistration.add(loadExternalCurriculumData(jsonStudentInfo));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedStudentRegistration;
    }

    private StudentRegistration loadExternalCurriculumData(JSONObject jsonStudentInfo) {
        final Integer number = Integer.parseInt((String) jsonStudentInfo.get("number"));
        final String remoteRegistrationOID = (String) jsonStudentInfo.get("remoteRegistrationOID");
        final String remoteDegreeOID = (String) jsonStudentInfo.get("degreeOID");
        FenixDegree fenixDegree = FenixDegree.getFenixDegreeByExternalId(remoteDegreeOID);
        final Boolean isConcluded = Boolean.parseBoolean((String) jsonStudentInfo.get("isConcluded"));
        final Integer curricularYear = Integer.parseInt((String) jsonStudentInfo.get("curricularYear"));

        if (StringUtils.isEmpty(remoteRegistrationOID) || fenixDegree == null) {
            System.out.println(number + "NOT IMPORTED! REGISTRATION OID: " + remoteRegistrationOID + " DEGREE OID: "
                    + remoteDegreeOID);
            return null;
        }

        StudentRegistration studentRegistration = getStudent().getRegistrationFor(remoteRegistrationOID);
        if (studentRegistration == null) {
            studentRegistration =
                    new StudentRegistration(getStudent(), remoteRegistrationOID, fenixDegree, number, isConcluded, curricularYear);
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

    public void loadExternalPersonnalData(JSONObject jsonStudentInfo) {
        final Boolean hasPersonalDataAuthorization =
                Boolean.parseBoolean((String) jsonStudentInfo.get("hasPersonalDataAuthorization"));

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
            return process.getFilesSet().size() > 0;
        }
        return false;
    }

    @Deprecated
    public boolean hasDateOfBirth() {
        return getDateOfBirth() != null;
    }

    @Deprecated
    public boolean hasNationality() {
        return getNationality() != null;
    }

    @Deprecated
    public boolean hasAddress() {
        return getAddress() != null;
    }

    @Deprecated
    public boolean hasArea() {
        return getArea() != null;
    }

    @Deprecated
    public boolean hasAreaCode() {
        return getAreaCode() != null;
    }

    @Deprecated
    public boolean hasDistrictSubdivision() {
        return getDistrictSubdivision() != null;
    }

    @Deprecated
    public boolean hasMobilePhone() {
        return getMobilePhone() != null;
    }

    @Deprecated
    public boolean hasPhone() {
        return getPhone() != null;
    }

    @Deprecated
    public boolean hasEmail() {
        return getEmail() != null;
    }

    @Deprecated
    public boolean hasProfessionalStatus() {
        return getProfessionalStatus() != null;
    }

    @Deprecated
    public boolean hasGeographicAvailability() {
        return getGeographicAvailability() != null;
    }

    @Deprecated
    public boolean hasCurriculumProcess() {
        return getCurriculumProcess() != null;
    }

    @Deprecated
    public boolean hasStudent() {
        return getStudent() != null;
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.curriculumQualification.CurriculumQualification> getCurriculumQualification() {
        return getCurriculumQualificationSet();
    }

    @Deprecated
    public boolean hasAnyCurriculumQualification() {
        return !getCurriculumQualificationSet().isEmpty();
    }

}
