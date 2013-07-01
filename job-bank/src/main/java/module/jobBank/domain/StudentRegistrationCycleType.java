package module.jobBank.domain;

public class StudentRegistrationCycleType extends StudentRegistrationCycleType_Base {

    public StudentRegistrationCycleType(StudentRegistration studentRegistration, FenixCycleType cycleType) {
        super();
        setStudentRegistration(studentRegistration);
        setCycleType(cycleType);
    }

    public void delete() {
        setStudentRegistration(null);
        super.deleteDomainObject();
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
    public boolean hasCycleType() {
        return getCycleType() != null;
    }

    @Deprecated
    public boolean hasStudentRegistration() {
        return getStudentRegistration() != null;
    }

}
