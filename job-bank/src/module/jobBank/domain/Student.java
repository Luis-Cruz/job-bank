package module.jobBank.domain;

import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.student.RemoteStudent;
import pt.ist.fenixWebFramework.services.Service;

public class Student extends Student_Base {

    private Student(User user) {
	super();
	setJobBankSystem(JobBankSystem.getInstance());
	setPerson(user.getPerson());
	setCurriculum(new Curriculum(this));
    }

    public User getUser() {
	return getPerson().getUser();
    }

    public static Student readStudent(User user) {

	if (hasStudent(user)) {
	    return user.getPerson().getStudent();
	}
	return null;
    }

    public static boolean hasStudent(User user) {
	return user != null && user.hasPerson() && user.getPerson().getStudent() != null;
    }

    public String getName() {
	return getPerson().getName();
    }

    public Integer getStudentNumber() {
	return getRemoteStudent().getNumber();
    }

    public Set<CandidateOffer> getActiveCandidateOffers() {
	return JobBankSystem.getInstance().readValuesToSatisfiedPredicate(new IPredicate<CandidateOffer>() {
	    @Override
	    public boolean evaluate(CandidateOffer object) {
		return object.isActive();
	    }
	}, getCandidateOfferSet());

    }

    /* Static methods */
    @Service
    public static Student createStudent(User user) {
	if (canCreateStudent(user)) {
	    return new Student(user);
	} else {
	    throw new DomainException("message.error.student.cannot.be.created", ResourceBundle
		    .getBundle(JobBankSystem.JOB_BANK_RESOURCES));
	}
    }

    public static boolean canCreateStudent(User user) {
	return user.hasPerson() && !user.getPerson().hasStudent();
    }

    public static Set<Student> readAllStudents(IPredicate<Student> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getStudentsSet());
    }

    /* private methods */

    private RemoteStudent getRemoteStudent() {
	return getPerson().getRemotePerson().getStudent();
    }

}
