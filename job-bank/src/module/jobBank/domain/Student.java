package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import module.organization.domain.Person;
import module.workflow.domain.ProcessFile;
import myorg.domain.User;
import net.sourceforge.fenixedu.domain.RemotePerson;
import net.sourceforge.fenixedu.domain.student.RemoteRegistration;
import net.sourceforge.fenixedu.domain.student.RemoteStudent;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class Student extends Student_Base {

    public Student(User user) {
	super();
	setJobBankSystem(JobBankSystem.getInstance());
	setPerson(user.getPerson());
	setCurriculum(new Curriculum(this));
	setHasPersonalDataAuthorization(false);
    }

    public User getUser() {
	return getPerson().getUser();
    }

    public String getName() {
	return getPerson().getName();
    }

    public Integer getNumber() {
	return 0;
    }

    public Set<OfferCandidacy> getActiveOfferCandidacies() {
	return Utils.readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
	    @Override
	    public boolean evaluate(OfferCandidacy object) {
		return object.isActive();
	    }
	}, getOfferCandidacySet());
    }

    public ArrayList<OfferCandidacy> getSortedActiveOfferCandidacies() {
	ArrayList<OfferCandidacy> results = new ArrayList<OfferCandidacy>(getActiveOfferCandidacies());
	Collections.sort(results, OfferCandidacy.COMPARATOR_BY_OFFER_PROCESS_IDENTIFICATION);
	return results;
    }

    public ArrayList<OfferCandidacy> getSortedOfferCandidacySet() {
	ArrayList<OfferCandidacy> results = new ArrayList<OfferCandidacy>(getOfferCandidacySet());
	Collections.sort(results, OfferCandidacy.COMPARATOR_BY_OFFER_PROCESS_IDENTIFICATION);
	return results;
    }

    public Boolean isActive() {
	if (getHasPersonalDataAuthorization() && getAcceptedTermsResponsibilityDate() != null) {
	    for (StudentRegistration studentRegistration : getStudentRegistrationSet()) {
		if (studentRegistration.isActive()) {
		    return true;
		}
	    }
	}
	return false;
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

    public Set<OfferCandidacy> getOfferCandidaciesOfEnterprise(final Enterprise enterprise) {
	return Utils.readValuesToSatisfiedPredicate(new IPredicate<OfferCandidacy>() {
	    @Override
	    public boolean evaluate(OfferCandidacy object) {
		return object.isActive() && object.getJobOffer().getEnterprise().equals(enterprise);
	    }
	}, getActiveOfferCandidacies());
    }

    public ArrayList<OfferCandidacy> getSortedOfferCandidaciesOfEnterprise(final Enterprise enterprise) {
	ArrayList<OfferCandidacy> results = new ArrayList<OfferCandidacy>(getOfferCandidaciesOfEnterprise(enterprise));
	Collections.sort(results, OfferCandidacy.COMPARATOR_BY_OFFER_PROCESS_IDENTIFICATION);
	return results;
    }

    public static Set<Student> readAllStudents(IPredicate<Student> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getStudentsSet());
    }

    public StudentRegistration getRegistrationFor(RemoteRegistration remoteRegistration, FenixCycleType cycleType) {
	for (StudentRegistration studentRegistration : getStudentRegistrationSet()) {
	    if (studentRegistration.getRemoteRegistration().equals(remoteRegistration)
		    && cycleType.equals(studentRegistration.getCycleType())) {
		return studentRegistration;
	    }
	}
	return null;
    }

    public Set<StudentRegistration> getActiveStudentRegistrationSet() {
	Set<StudentRegistration> result = new HashSet<StudentRegistration>();
	for (StudentRegistration studentRegistration : getStudentRegistrationSet()) {
	    if (studentRegistration.isActive()) {
		result.add(studentRegistration);
	    }
	}
	return result;
    }

    public boolean hasAnyConcludedRegistration() {
	for (StudentRegistration studentRegistration : getActiveStudentRegistrationSet()) {
	    if (studentRegistration.getIsConcluded()) {
		return true;
	    }
	}
	return false;
    }

    public boolean hasAnyRegistrationWithDegree(FenixDegree degree, boolean checkConclusion) {
	for (StudentRegistration studentRegistration : getActiveStudentRegistrationSet()) {
	    FenixDegree fenixDegree = studentRegistration.getFenixDegree();
	    if (fenixDegree != null && fenixDegree.equals(degree)) {
		if (checkConclusion && !studentRegistration.getIsConcluded()) {
		    continue;
		}
		return true;
	    }
	}
	return false;
    }

    @Service
    public void acceptTermsResponsibility() {
	setAcceptedTermsResponsibilityDate(new DateTime());
    }

    public StudentRegistration addOrUpdateRegistration(RemoteRegistration remoteRegistration) {
	Boolean hasPersonalDataAuthorization = remoteRegistration.getStudent()
		.hasPersonalDataAuthorizationForProfessionalPurposesAt();
	setHasPersonalDataAuthorization(hasPersonalDataAuthorization == null ? false : hasPersonalDataAuthorization);
	FenixDegree fenixDegree = JobBankSystem.getInstance().getFenixDegreeFor(remoteRegistration.getDegree());
	FenixCycleType fenixCycleType = FenixCycleType.getFenixCycleType(remoteRegistration.getCurrentCycleTypeName());
	if (fenixDegree != null && fenixCycleType != null) {
	    if (fenixDegree.getDegreeType().equals(FenixDegreeType.BOLONHA_INTEGRATED_MASTER_DEGREE)
		    && fenixCycleType.equals(FenixCycleType.SECOND_CYCLE)) {
		addOrUpdateRegistration(remoteRegistration, fenixDegree, FenixCycleType.FIRST_CYCLE);
	    }
	    return addOrUpdateRegistration(remoteRegistration, fenixDegree, fenixCycleType);
	}
	return null;
    }

    public StudentRegistration addOrUpdateRegistration(RemoteRegistration remoteRegistration, FenixDegree fenixDegree,
	    FenixCycleType fenixCycleType) {
	StudentRegistration studentRegistration = getRegistrationFor(remoteRegistration, fenixCycleType);
	if (studentRegistration == null) {
	    studentRegistration = new StudentRegistration(this, remoteRegistration, fenixDegree, fenixCycleType);
	} else {
	    studentRegistration.update();
	}
	return studentRegistration;
    }
}
