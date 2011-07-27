package module.jobBank.domain;

import java.math.BigDecimal;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;

import org.joda.time.DateTime;

public class StudentRegistration extends StudentRegistration_Base {

    public StudentRegistration(Student student, RemoteRegistration remoteRegistration, FenixDegree fenixDegree,
	    Boolean isConcluded, BigDecimal average) {
	super();
	setStudent(student);
	setRemoteRegistration(remoteRegistration);
	setNumber(remoteRegistration.getNumber());
	update(isConcluded, average, fenixDegree);
	setActiveBeginDate(new DateTime());
    }

    public void update(Boolean isConcluded, BigDecimal average, FenixDegree fenixDegree) {
	setIsConcluded(isConcluded);
	setAverage(average);
	setFenixDegree(fenixDegree);
	setJobBankSystem(JobBankSystem.getInstance());
    }

    public boolean isActive() {
	return getActiveEndDate() == null || getActiveEndDate().isAfterNow();
    }

    public static Set<StudentRegistration> readAllStudentRegistrations(IPredicate<StudentRegistration> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getActiveStudentRegistrationSet());
    }

}
