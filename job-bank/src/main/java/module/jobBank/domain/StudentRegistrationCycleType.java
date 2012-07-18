package module.jobBank.domain;

public class StudentRegistrationCycleType extends StudentRegistrationCycleType_Base {

    public StudentRegistrationCycleType(StudentRegistration studentRegistration, FenixCycleType cycleType) {
	super();
	setStudentRegistration(studentRegistration);
	setCycleType(cycleType);
    }

    public void delete() {
	removeStudentRegistration();
	super.deleteDomainObject();
    }

}
