package module.jobBank.domain;

import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.workflow.domain.ProcessFile;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.RemoteDegree;
import net.sourceforge.fenixedu.domain.RemoteExecutionYear;
import net.sourceforge.fenixedu.domain.RemotePerson;
import net.sourceforge.fenixedu.domain.RemoteStudentCurricularPlan;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
import net.sourceforge.fenixedu.domain.student.RemoteStudent;

import org.apache.commons.lang.StringUtils;

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

    public String getName() {
	return getPerson().getName();
    }

    public Integer getNumber() {
	return getRemoteStudent().getNumber();
    }

    public Set<OfferCandidacy> getActiveOfferCandidacies() {
	return JobBankSystem.getInstance().readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
	    @Override
	    public boolean evaluate(OfferCandidacy object) {
		return object.isActive();
	    }
	}, getOfferCandidacySet());

    }

    public Boolean isActive() {
	return Registration.hasValidRegistrionsForStudent(this);
    }

    public Boolean isRegistrationConcluded() {
	return getValidRegistration().isConcluded();
    }

    public RemoteDegree getDegree() {
	generateRemoteDegree();
	return Registration.hasValidRegistrionsForStudent(this) ? getValidRegistration().getDegree() : null;
    }

    public String getPresentationDegreeName() {
	return Registration.hasValidRegistrionsForStudent(this) ? getDegree().getPresentationName() : StringUtils.EMPTY;
    }

    @Service
    private void generateRemoteDegree() {
	if (Registration.hasValidRegistrionsForStudent(this))
	    getValidRegistration().getDegree();
    }

    public RemoteRegistration getValidRegistration() {
	return Registration.getValidRegistration(this);
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

    public boolean isSenior(final RemoteRegistration remoteRegistration, final RemoteExecutionYear executionYear) {
	Double floor = new Double(165.00);
	Double ceiling = new Double(180.00);
	RemoteStudentCurricularPlan remoteCurricularPlan = remoteRegistration.getStudentCurricularPlanForCurrentExecutionYear();
	return remoteCurricularPlan.getApprovedEctsCreditsForFirstCycle().compareTo(floor) >= 0
		&& remoteCurricularPlan.getApprovedEctsCreditsForFirstCycle().compareTo(ceiling) < 0;
    }

    public RemoteStudent getRemoteStudent() {
	return hasRemotePerson() == true ? getRemotePerson().getStudent() : null;
    }

    public RemotePerson getRemotePerson() {
	return hasRemotePerson() == true ? getPerson().getRemotePerson() : null;
    }

    public boolean hasRemotePerson() {
	return getPerson() != null && getPerson().getRemotePerson() != null;
    }

    public Set<OfferCandidacy> getOfferCandidaciesOfEnterprise(final Enterprise enterprise) {
	return getJobBankSystem().readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
	    @Override
	    public boolean evaluate(OfferCandidacy object) {
		return object.getJobOffer().getEnterprise().equals(enterprise);
	    }
	}, getActiveOfferCandidacies());
    }

    public static Student readCurrentStudent() {
	return readStudent(UserView.getCurrentUser());
    }

    public static Student readStudent(User user) {
	return hasStudent(user) ? user.getPerson().getStudent() : null;
    }

    public static boolean hasStudent(User user) {
	return user != null && user.hasPerson() && user.getPerson().getStudent() != null;
    }

    public boolean canRemoveFile(ProcessFile file) {
	return getCurriculum().getCurriculumProcess().getFiles().contains(file);
    }

}
