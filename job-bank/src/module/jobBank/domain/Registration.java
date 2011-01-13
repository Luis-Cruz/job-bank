package module.jobBank.domain;

import java.util.Set;

import net.sourceforge.fenixedu.domain.RemoteStudentCurricularPlan;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
import net.sourceforge.fenixedu.domain.student.RemoteStudent;
import net.sourceforge.fenixedu.domain.student.curriculum.RemoteConclusionProcess;
import net.sourceforge.fenixedu.domain.studentCurriculum.RemoteCycleCurriculumGroup;

import org.joda.time.LocalDate;

import pt.ist.fenixWebFramework.services.Service;

public class Registration {

    private static final int YEARS_SUBSCRIPTION_ACTIVE = 1;

    public static RemoteRegistration getValidRegistrationOnProcessCreation(Student student) {
	generateRemoteDomains(student);
	RemoteStudent remoteStudent = student.getRemoteStudent();
	Set<RemoteRegistration> remoteRegistrations = student.getRemoteStudent().getRegistrations();
	for (RemoteRegistration remoteRegistration : remoteRegistrations) {
	    if (isValidRegistrationOnProcessCreation(remoteStudent, remoteRegistration)) {
		return remoteRegistration;
	    }
	}
	return null;
    }

    public static Boolean isValidRegistrationOnProcessCreation(RemoteStudent remoteStudent, RemoteRegistration remoteRegistration) {
	return remoteRegistration != null && Degree.readRemoteDegrees().contains(remoteRegistration.getDegree())
		&& isValidRegistration(remoteStudent, remoteRegistration);
    }

    public static Boolean hasValidRegistrationsForStudent(Student student) {
	return getValidRegistrationOnProcessCreation(student) == null ? false : true;
    }

    public static Boolean isValidRegistration(RemoteStudent remoteStudent, RemoteRegistration remoteRegistration) {
	if (remoteStudent == null || remoteRegistration == null) {
	    return false;
	}
	return remoteStudent.isSeniorForCurrentExecutionYear() || Student.hasCreditsToAccess(remoteRegistration)
		|| isValidNow(remoteRegistration);
    }

    public static RemoteConclusionProcess readConcluedProcess(RemoteRegistration remoteRegistration) {
	if (remoteRegistration == null) {
	    return null;
	}
	RemoteStudentCurricularPlan curricularPlan = remoteRegistration.getLastStudentCurricularPlan();
	if (curricularPlan != null && curricularPlan.isConclusionProcessed()) {
	    RemoteConclusionProcess conclusionProcess = remoteRegistration.getConclusionProcess();
	    if (conclusionProcess == null) {
		RemoteCycleCurriculumGroup remoteCycleCurriculumGroup = curricularPlan.getLastConcludedCycleCurriculumGroup();
		return remoteCycleCurriculumGroup.getConclusionProcess();
	    }
	    return conclusionProcess;
	}
	return null;
    }

    public static Boolean isConcluedProcessed(RemoteRegistration remoteRegistration) {
	if (remoteRegistration != null) {
	    RemoteStudentCurricularPlan curricularPlan = remoteRegistration.getLastStudentCurricularPlan();
	    ;
	    return curricularPlan != null && curricularPlan.isConclusionProcessed();
	}

	return false;
    }

    private static Boolean isValidNow(RemoteRegistration remoteRegistration) {
	RemoteConclusionProcess conclusionProcess = readConcluedProcess(remoteRegistration);
	LocalDate now = new LocalDate();
	return conclusionProcess != null
		&& conclusionProcess.getConclusionDate().plusYears(YEARS_SUBSCRIPTION_ACTIVE).isAfter(now);

    }

    /* private methods */

    @Service
    private static void generateRemoteDomains(Student student) {
	student.getRemoteStudent();
	student.getRemoteStudent().getRegistrations();
	for (RemoteRegistration remoteRegistration : student.getRemoteStudent().getRegistrations()) {
	    remoteRegistration.getLastStudentCurricularPlan();
	}
    }

}
