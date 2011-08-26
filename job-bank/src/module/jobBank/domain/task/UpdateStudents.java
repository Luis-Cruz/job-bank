package module.jobBank.domain.task;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import module.jobBank.domain.StudentRegistration;
import module.organizationIst.domain.listner.LoginListner;
import myorg.domain.User;
import myorg.util.BundleUtil;
import net.sourceforge.fenixedu.domain.RemoteExecutionYear;
import net.sourceforge.fenixedu.domain.RemotePerson;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
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
	RemoteExecutionYear executionYear = RemoteExecutionYear.readCurrentExecutionYear(JobBankSystem.readRemoteHost());
	Set<StudentRegistration> updatedRegistrations = new HashSet<StudentRegistration>();
	updatedRegistrations.addAll(updateRegistrations(executionYear.getAllBolonhaRegistrationsForExecutionYear()));
	RemoteExecutionYear previousExecutionYear = executionYear.getPreviousExecutionYear();
	updatedRegistrations.addAll(updateRegistrations(previousExecutionYear.getConcludedRegistrationsForExecutionYear()));
	for (StudentRegistration registration : JobBankSystem.getInstance().getStudentRegistration()) {
	    if (registration.isActive() && !updatedRegistrations.contains(registration)) {
		registration.setInactive();
	    }
	}
    }

    private Set<StudentRegistration> updateRegistrations(Collection<RemoteRegistration> remoteRegistrations) {
	final Set<StudentRegistration> updatedRegistrations = new HashSet<StudentRegistration>();
	for (final RemoteRegistration remoteRegistration : remoteRegistrations) {
	    final RemotePerson remotePerson = remoteRegistration.getStudent().getPerson();
	    final String userId = remotePerson.getUser().getUserUId();
	    LoginListner.importUserInformation(userId);
	    User user = User.findByUsername(userId);
	    if (user != null && user.getPerson() != null) {
		Student student = user.getPerson().getStudent();
		try {
		    if (student == null) {
			student = new Student(user);
		    }
		    StudentRegistration studentRegistration = student.addOrUpdateRegistration(remoteRegistration);
		    if (studentRegistration != null) {
			updatedRegistrations.add(studentRegistration);
		    }
		} catch (RemoteException e) {
		    System.out.println("ERROR: " + userId + " " + e);
		}
	    } else {
		System.out.println("Invalid User: " + userId);
	    }
	}
	return updatedRegistrations;
    }
}
