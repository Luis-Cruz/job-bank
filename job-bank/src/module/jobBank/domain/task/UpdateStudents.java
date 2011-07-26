package module.jobBank.domain.task;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import module.jobBank.domain.StudentRegistration;
import myorg.domain.User;
import myorg.util.BundleUtil;
import net.sourceforge.fenixedu.domain.RemoteExecutionYear;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;

import org.joda.time.DateTime;

import pt.ist.fenixframework.plugins.remote.domain.exception.RemoteException;

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

	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	RemoteExecutionYear executionYear = RemoteExecutionYear.readCurrentExecutionYear(jobBankSystem.readRemoteHost());
	System.out.println("ExecutionYear: " + executionYear.getName());

	Collection<RemoteRegistration> seniorRegistrationsForExecutionYear = executionYear
		.getSeniorRegistrationsForExecutionYear();
	System.out.println("TOTAL registrations: " + seniorRegistrationsForExecutionYear == null ? "NULL"
		: seniorRegistrationsForExecutionYear.size());
	Set<StudentRegistration> updatedRegistrations = new HashSet<StudentRegistration>();


	for (RemoteRegistration remoteRegistration : seniorRegistrationsForExecutionYear) {

	    String userUId = remoteRegistration.getStudent().getPerson().getUser().getUserUId();
	    User user = User.findByUsername(userUId);

	    if (user != null && user.getPerson() != null) {
		Student student = user.getPerson().getStudent();

		try {
		    Boolean hasPersonalDataAuthorization = remoteRegistration.getStudent()
			    .hasPersonalDataAuthorizationForProfessionalPurposesAt();

		    if (student == null) {
			student = new Student(user);
		    }

		    student.setHasPersonalDataAuthorization(hasPersonalDataAuthorization == null ? false
			    : hasPersonalDataAuthorization);

		    if (student.getHasPersonalDataAuthorization()) {
			FenixDegree fenixDegreeFor = jobBankSystem.getFenixDegreeFor(remoteRegistration.getDegree());

			Boolean registrationConclusionProcessed = remoteRegistration.isRegistrationConclusionProcessed();
			BigDecimal average = null;

			if (registrationConclusionProcessed != null && registrationConclusionProcessed) {
			    average = new BigDecimal(remoteRegistration.getFinalAverageOfLastConcludedCycle());
			}

			StudentRegistration registration = student.getRegistrationFor(remoteRegistration);

			if (registration == null) {
			    registration = new StudentRegistration(student, remoteRegistration, fenixDegreeFor,
				    registrationConclusionProcessed, average);
			}

			registration.update(registrationConclusionProcessed, average, fenixDegreeFor);
			updatedRegistrations.add(registration);
		    }
		} catch (RemoteException e) {
		    System.out.println("ERROR: " + userUId + " " + e);
		}
	    } else {
		System.out.println("Invalid User: " + userUId);
	    }
	}
	// TODO os alunos que terminaram o curso este ano ou ano passado

	for (StudentRegistration registration : jobBankSystem.getStudentRegistration()) {
	    if (registration.isActive() && !updatedRegistrations.contains(registration)) {
		registration.setActiveEndDate(new DateTime());
	    }
	}
    }
}
