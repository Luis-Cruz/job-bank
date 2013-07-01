package module.jobBank.domain;

import java.util.Comparator;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class StudentRegistration extends StudentRegistration_Base {

    public static final Comparator<StudentRegistration> COMPARATOR_BY_STUDENT_NAME = new Comparator<StudentRegistration>() {

        @Override
        public int compare(StudentRegistration o1, StudentRegistration o2) {
            final Student s1 = o1.getStudent();
            final Student s2 = o2.getStudent();

            return StringNormalizer.normalize(s1.getName()).compareTo(StringNormalizer.normalize(s2.getName()));
        }

    };

    public StudentRegistration(Student student, String remoteRegistrationOid, FenixDegree fenixDegree, Integer number,
            Boolean isConcluded, Integer curricularYear) {
        super();
        setStudent(student);
        setRemoteRegistrationOid(remoteRegistrationOid);
        update(fenixDegree, number, isConcluded, curricularYear);
    }

    public void update(FenixDegree fenixDegree, Integer number, Boolean isConcluded, Integer curricularYear) {
        setNumber(number);
        setFenixDegree(fenixDegree);
        setIsConcluded(isConcluded);
        setCurricularYear(curricularYear);
        setJobBankSystem(JobBankSystem.getInstance());
        if ((!getStudent().getHasPersonalDataAuthorization())) {
            setInactive();
        }
    }

    public static Set<StudentRegistration> readAllStudentRegistrations(IPredicate<StudentRegistration> predicate) {
        JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getActiveStudentRegistrationSet());
    }

    public void setInactive() {
        setJobBankSystem(null);
    }

    public boolean isActive() {
        return hasJobBankSystem();
    }

    public StudentRegistrationCycleType getStudentRegistrationCycleType(FenixCycleType fenixCycleType) {
        for (StudentRegistrationCycleType studentRegistrationCycleType : getStudentRegistrationCycleTypes()) {
            if (studentRegistrationCycleType.getCycleType().equals(fenixCycleType)) {
                return studentRegistrationCycleType;
            }
        }
        return null;
    }

    public StudentRegistrationCycleType getLastStudentRegistrationCycleType() {

        FenixCycleType[] fenixCycleTypes = FenixCycleType.values();
        for (int i = fenixCycleTypes.length - 1; i >= 0; i--) {
            FenixCycleType fenixCycleType = fenixCycleTypes[i];
            StudentRegistrationCycleType studentRegistrationCycleType = getStudentRegistrationCycleType(fenixCycleType);
            if (studentRegistrationCycleType != null) {
                return studentRegistrationCycleType;
            }
        }
        return null;
    }

    public boolean hasMoreThanFirstCycle() {
        for (FenixCycleType fenixCycleType : FenixCycleType.values()) {
            if (!fenixCycleType.equals(FenixCycleType.FIRST_CYCLE)) {
                StudentRegistrationCycleType studentRegistrationCycleType = getStudentRegistrationCycleType(fenixCycleType);
                if (studentRegistrationCycleType != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Deprecated
    public boolean hasNumber() {
        return getNumber() != null;
    }

    @Deprecated
    public boolean hasAverage() {
        return getAverage() != null;
    }

    @Deprecated
    public boolean hasIsConcluded() {
        return getIsConcluded() != null;
    }

    @Deprecated
    public boolean hasCurricularYear() {
        return getCurricularYear() != null;
    }

    @Deprecated
    public boolean hasRemoteRegistrationOid() {
        return getRemoteRegistrationOid() != null;
    }

    @Deprecated
    public boolean hasFenixDegree() {
        return getFenixDegree() != null;
    }

    @Deprecated
    public boolean hasJobBankSystem() {
        return getJobBankSystem() != null;
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.StudentRegistrationCycleType> getStudentRegistrationCycleTypes() {
        return getStudentRegistrationCycleTypesSet();
    }

    @Deprecated
    public boolean hasAnyStudentRegistrationCycleTypes() {
        return !getStudentRegistrationCycleTypesSet().isEmpty();
    }

    @Deprecated
    public boolean hasStudent() {
        return getStudent() != null;
    }

}
