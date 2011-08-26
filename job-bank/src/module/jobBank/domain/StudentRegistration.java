package module.jobBank.domain;

import java.util.Comparator;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
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

    public StudentRegistration(Student student, RemoteRegistration remoteRegistration, FenixDegree fenixDegree,
	    FenixCycleType fenixCycleType) {
	super();
	setStudent(student);
	setRemoteRegistration(remoteRegistration);
	setFenixDegree(fenixDegree);
	setCycleType(fenixCycleType);
	update();
    }

    public void update() {
	setNumber(getRemoteRegistration().getNumber());
	setIsConcluded(getRemoteRegistration().isRegistrationConclusionProcessed());
	setAverage(getRemoteRegistration().getAverage(getCycleType().name()));
	setCurricularYear(getRemoteRegistration().getCurricularYear());
	setJobBankSystem(JobBankSystem.getInstance());
	if ((!getStudent().getHasPersonalDataAuthorization()) || getCycleType().equals(FenixCycleType.FIRST_CYCLE)) {
	    setInactive();
	}
    }

    public static Set<StudentRegistration> readAllStudentRegistrations(IPredicate<StudentRegistration> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getActiveStudentRegistrationSet());
    }

    public void setInactive() {
	removeJobBankSystem();
    }

    public boolean isActive() {
	return hasJobBankSystem();
    }

}
