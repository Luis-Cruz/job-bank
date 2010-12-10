package module.jobBank.domain;

import java.util.Set;

import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
import net.sourceforge.fenixedu.domain.student.RemoteStudent;

import org.joda.time.DateTime;

public class Registration {

    private static final int YEARS_SUBSCRIPTION_ACTIVE = 1;

    public static RemoteRegistration getValidRegistration(Student student) {
	RemoteStudent remoteStudent = student.getRemoteStudent();
	Set<RemoteRegistration> remoteRegistrations = student.getRemoteStudent().getRegistrations();
	for (RemoteRegistration remoteRegistration : remoteRegistrations) {
	    if (isValidRegistration(remoteStudent, remoteRegistration)) {
		return remoteRegistration;
	    }
	}
	return null;
    }

    public static Boolean hasValidRegistrionsForStudent(Student student) {
	return getValidRegistration(student) == null ? false : true;
    }

    public static Boolean isValidRegistration(RemoteStudent remoteStudent, RemoteRegistration remoteRegistration) {
	if (true) {
	    return true;
	}
	DateTime conclusionDate = remoteRegistration.getConclusionProcessCreationDateTime();
	DateTime now = new DateTime();
	return Degree.readRemoteDegrees().contains(remoteRegistration.getDegree())
		&& remoteStudent.isSeniorForCurrentExecutionYear()
		|| (remoteRegistration.isConcluded() && conclusionDate != null && conclusionDate.plusYears(
			YEARS_SUBSCRIPTION_ACTIVE).isBefore(now));
    }
    /* private methods */

    /*
     * protected boolean hasConditionsToFinishMasterDegree(final
     * RemoteRegistration remoteRegistration, final RemoteExecutionYear
     * remoteExecutionYear) { Enrolment dissertationEnrolment =
     * remoteRegistration.getStudentCurricularPlan(remoteExecutionYear)
     * .getLatestDissertationEnrolment();
     * 
     * if (dissertationEnrolment == null) { return false; }
     * 
     * if (dissertationEnrolment.getExecutionYear() != remoteExecutionYear &&
     * !dissertationEnrolment.isApproved()) { return false; }
     * 
     * Double dissContrib = dissertationEnrolment.isApproved() ? 0.0 :
     * dissertationEnrolment.getEctsCredits(); Double threshold = 120.00 -
     * (15.00 + dissContrib); return
     * remoteRegistration.getStudentCurricularPlan(
     * remoteExecutionYear).getApprovedEctsCredits(CycleType.SECOND_CYCLE) >=
     * threshold; }
     */
}
