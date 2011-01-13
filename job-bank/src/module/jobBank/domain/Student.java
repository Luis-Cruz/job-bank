package module.jobBank.domain;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import module.organization.domain.Person;
import module.workflow.domain.ProcessFile;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.RemoteDegree;
import net.sourceforge.fenixedu.domain.RemoteEnrolment;
import net.sourceforge.fenixedu.domain.RemoteExecutionYear;
import net.sourceforge.fenixedu.domain.RemotePerson;
import net.sourceforge.fenixedu.domain.RemoteStudentCurricularPlan;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
import net.sourceforge.fenixedu.domain.student.RemoteStudent;
import net.sourceforge.fenixedu.domain.student.curriculum.RemoteConclusionProcess;

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

    public BigDecimal getAverage() {
	RemoteConclusionProcess conclusionProcess = Registration.readConcluedProcess(getRemoteRegistration());
	if (conclusionProcess != null) {
	    conclusionProcess.getFinalAverage();
	}
	return null;
    }

    public Set<OfferCandidacy> getActiveOfferCandidacies() {
	return Utils.readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
	    @Override
	    public boolean evaluate(OfferCandidacy object) {
		return object.isActive();
	    }
	}, getOfferCandidacySet());

    }

    public Boolean isActive() {
	// If not active. Try to registration on Job Bank
	RemoteRegistration remoteRegistration = getRemoteRegistration();
	if (remoteRegistration == null) {
	    remoteRegistration = Registration.getValidRegistrationOnProcessCreation(this);
	    setRemoteRegistration(remoteRegistration);
	    return remoteRegistration != null ? true : false;
	}
	return Registration.isValidRegistration(getRemoteStudent(), remoteRegistration);
    }

    public RemoteDegree getDegree() {
	generateRemoteDegree();
	return hasRemoteRegistration() ? getRemoteRegistration().getDegree() : null;
    }

    public String getPresentationDegreeName() {
	return Registration.hasValidRegistrationsForStudent(this) ? getDegree().getPresentationName() : StringUtils.EMPTY;
    }

    @Service
    private void generateRemoteDegree() {
	if (hasRemoteRegistration())
	    getRemoteRegistration().getDegree();
    }

    /* Static methods */
    @Service
    public static Student createStudent(User user) {
	if (!canCreateStudent(user)) {
	    throw new DomainException("message.error.student.cannot.be.created", ResourceBundle
		    .getBundle(JobBankSystem.JOB_BANK_RESOURCES));
	}
	Student student = new Student(user);
	if (!Registration.hasValidRegistrationsForStudent(student)) {
	    throw new DomainException("message.error.student.cannot.valid.registrations", ResourceBundle
		    .getBundle(JobBankSystem.JOB_BANK_RESOURCES));
	}

	student.setRemoteRegistration(Registration.getValidRegistrationOnProcessCreation(student));
	return student;

    }

    public static boolean canCreateStudent(User user) {
	if (user.hasPerson()) {
	    Person person = user.getPerson();
	    return person.hasRemotePerson() && person.getRemotePerson().getStudent() != null;
	}
	return false;
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

    public boolean canRemoveFile(ProcessFile file) {
	return getCurriculum().getCurriculumProcess().getFiles().contains(file);
    }

    public boolean isConcludedProcessed() {
	return Registration.isConcluedProcessed(getRemoteRegistration());
    }

    public Set<OfferCandidacy> getOfferCandidaciesOfEnterprise(final Enterprise enterprise) {
	return Utils.readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
	    @Override
	    public boolean evaluate(OfferCandidacy object) {
		return object.getJobOffer().getEnterprise().equals(enterprise);
	    }
	}, getActiveOfferCandidacies());
    }

    public static Set<Student> readAllStudents(IPredicate<Student> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getStudentsSet());
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

    public static boolean hasCreditsToAccess(final RemoteRegistration remoteRegistration) {
	RemoteExecutionYear executionYear = RemoteExecutionYear.readCurrentExecutionYear(JobBankSystem.getInstance()
		.readRemoteHost());
	return hasCreditsToAccessForFirstCycle(remoteRegistration, executionYear)
		|| hasCreditsToAccessForSecondCycle(remoteRegistration, executionYear);
    }

    /*
     * public static Boolean hasCreditsToAccessForFirstCycle(final
     * RemoteRegistration remoteRegistration, final RemoteExecutionYear
     * executionYear) { Double floor = new Double(165.00); Double ceiling = new
     * Double(180.00); RemoteStudentCurricularPlan remoteCurricularPlan =
     * remoteRegistration.getStudentCurricularPlanForCurrentExecutionYear();
     * return
     * remoteCurricularPlan.getApprovedEctsCreditsForFirstCycle().compareTo
     * (floor) >= 0 &&
     * remoteCurricularPlan.getApprovedEctsCreditsForFirstCycle()
     * .compareTo(ceiling) < 0; }
     */

    public static Boolean hasCreditsToAccessForFirstCycle(final RemoteRegistration remoteRegistration,
	    final RemoteExecutionYear executionYear) {
	Double floor = new Double(165.00);
	Double ceiling = new Double(180.00);
	RemoteStudentCurricularPlan remoteCurricularPlan = remoteRegistration.getStudentCurricularPlanForCurrentExecutionYear();
	Double approvedEcts = remoteCurricularPlan.getApprovedEctsCredits();
	return remoteCurricularPlan.isFirstCycle() && approvedEcts.compareTo(floor) >= 0 && approvedEcts.compareTo(ceiling) < 0;
    }

    public static Boolean hasCreditsToAccessForSecondCycle(final RemoteRegistration remoteRegistration,
	    final RemoteExecutionYear remoteExecutionYear) {
	RemoteStudentCurricularPlan remoteCurricularPlan = remoteRegistration.getStudentCurricularPlanForCurrentExecutionYear();
	RemoteEnrolment dissertationEnrolment = remoteCurricularPlan.getLatestDissertationEnrolment();
	Double master = 120d;
	Double minimumEctsToFinished = 24d;
	if (dissertationEnrolment == null) {
	    return false;
	}

	if (dissertationEnrolment.getExecutionYear() != remoteExecutionYear && !dissertationEnrolment.isApproved()) {
	    return false;
	}

	Double dissContrib = dissertationEnrolment.isApproved() ? 0.0 : dissertationEnrolment.getEctsCredits();
	Double threshold = master - (minimumEctsToFinished + dissContrib);
	return remoteCurricularPlan.isSecondCycle()
		&& remoteRegistration.getStudentCurricularPlanForCurrentExecutionYear().getApprovedEctsCredits() >= threshold;
    }
}
