package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import module.webserviceutils.client.JerseyRemoteUser;
import module.workflow.domain.ProcessFile;

import org.joda.time.DateTime;

import pt.ist.bennu.core.domain.User;
import pt.ist.fenixframework.Atomic;

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
            if (getStudentRegistrationSet().size() != 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean canCreateStudent(User user) {
        if (user.getPerson() != null) {
            return new JerseyRemoteUser(user).hasStudent();
        }
        return false;
    }

    public boolean hasRemotePerson() {
        return new JerseyRemoteUser(getPerson().getUser()).hasRemotePerson();
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

    public StudentRegistration getRegistrationFor(String remoteRegistrationOid) {
        for (StudentRegistration studentRegistration : super.getStudentRegistrationSet()) {
            if (studentRegistration.getRemoteRegistrationOid().equals(remoteRegistrationOid)) {
                return studentRegistration;
            }
        }
        return null;
    }

    public Set<StudentRegistration> getStudentRegistration() {
        return new HashSet<StudentRegistration>(super.getStudentRegistrationSet());
    }

    public Set<StudentRegistration> getStudentRegistrationWithoutFiltering() {
        return super.getStudentRegistrationSet();
    }

    @Override
    public Set<StudentRegistration> getStudentRegistrationSet() {
        Set<StudentRegistration> result = new HashSet<StudentRegistration>();
        for (StudentRegistration studentRegistration : super.getStudentRegistrationSet()) {
            if (studentRegistration.hasJobBankSystem()) {
                result.add(studentRegistration);
            }
        }
        return result;
    }

    public Set<StudentRegistrationCycleType> getStudentRegistrationCycleTypes() {
        Set<StudentRegistrationCycleType> result = new HashSet<StudentRegistrationCycleType>();
        for (StudentRegistration studentRegistration : super.getStudentRegistrationSet()) {
            if (studentRegistration.hasJobBankSystem()) {
                result.addAll(studentRegistration.getStudentRegistrationCycleTypes());
            }
        }
        return result;
    }

    public boolean hasAnyConcludedRegistration() {
        for (StudentRegistration studentRegistration : getStudentRegistrationSet()) {
            if (studentRegistration.getIsConcluded()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyRegistrationWithDegree(FenixDegree degree, boolean checkConclusion) {
        for (StudentRegistration studentRegistration : getStudentRegistrationSet()) {
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

    @Atomic
    public void acceptTermsResponsibility() {
        setAcceptedTermsResponsibilityDate(new DateTime());
    }

    public StudentAuthorization getActiveStudentAuthorization() {
        for (StudentAuthorization studentAuthorization : getStudentAuthorizationSet()) {
            if (studentAuthorization.getIsActive()) {
                return studentAuthorization;
            }
        }
        return null;
    }

    @Deprecated
    public boolean hasAcceptedTermsResponsibilityDate() {
        return getAcceptedTermsResponsibilityDate() != null;
    }

    @Deprecated
    public boolean hasHasPersonalDataAuthorization() {
        return getHasPersonalDataAuthorization() != null;
    }

    @Deprecated
    public boolean hasCurriculum() {
        return getCurriculum() != null;
    }

    @Deprecated
    public boolean hasJobBankSystem() {
        return getJobBankSystem() != null;
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.JobOfferNotificationFilter> getJobOfferNotificationFilter() {
        return getJobOfferNotificationFilterSet();
    }

    @Deprecated
    public boolean hasAnyJobOfferNotificationFilter() {
        return !getJobOfferNotificationFilterSet().isEmpty();
    }

    @Deprecated
    public boolean hasAnyStudentRegistration() {
        return !getStudentRegistrationSet().isEmpty();
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.JobOffer> getJobOffer() {
        return getJobOfferSet();
    }

    @Deprecated
    public boolean hasAnyJobOffer() {
        return !getJobOfferSet().isEmpty();
    }

    @Deprecated
    public boolean hasPerson() {
        return getPerson() != null;
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.StudentAuthorization> getStudentAuthorization() {
        return getStudentAuthorizationSet();
    }

    @Deprecated
    public boolean hasAnyStudentAuthorization() {
        return !getStudentAuthorizationSet().isEmpty();
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.OfferCandidacy> getOfferCandidacy() {
        return getOfferCandidacySet();
    }

    @Deprecated
    public boolean hasAnyOfferCandidacy() {
        return !getOfferCandidacySet().isEmpty();
    }

}
