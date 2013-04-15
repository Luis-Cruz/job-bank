package module.jobBank.domain;

import module.jobBank.domain.utils.LoginListner;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.fenixframework.Atomic;

public class StudentAuthorization extends StudentAuthorization_Base {

    public StudentAuthorization(String username, LocalDate beginDate, LocalDate endDate) {
        super();
        LoginListner.importUserInformation(username);
        User user = User.findByUsername(username);
        if (user == null || user.getPerson() == null) {
            throw new DomainException("message.error.studentAuthorization.invalidUser",
                    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
        }
        Student student = user.getPerson().getStudent();

        if (student == null) {
            student = new Student(user.getPerson().getUser());
        }
        checkValues(student, beginDate, endDate);
        setJobBankSystem(JobBankSystem.getInstance());
        setStudent(student);
        setBeginDate(beginDate);
        setEndDate(endDate);
        setStudentAuthorizationProcess(new StudentAuthorizationProcess(this));
        getStudent().getCurriculum().loadExternalData();
    }

    private void checkValues(Student student, LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null || endDate.isBefore(new LocalDate())) {
            throw new DomainException("message.error.studentAuthorization.invalidDates",
                    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
        }
        if (!beginDate.isBefore(endDate)) {
            throw new DomainException("message.jobOffer.beginDate.isAfter.endDate",
                    DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
        }

        Interval thisInterval = new Interval(beginDate.toDateTimeAtStartOfDay(), endDate.plusDays(1).toDateTimeAtStartOfDay());
        for (StudentAuthorization studentAuthorization : student.getStudentAuthorizationSet()) {
            if ((!studentAuthorization.equals(this)) && thisInterval.overlaps(studentAuthorization.getInterval())) {
                throw new DomainException("message.error.studentAuthorization.overlapingDates",
                        DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
            }
        }
    }

    private Interval getInterval() {
        return new Interval(getBeginDate().toDateTimeAtStartOfDay(), getEndDate().plusDays(1).toDateTimeAtStartOfDay());
    }

    public boolean getIsActive() {
        Interval interval =
                new Interval(getBeginDate().toDateTimeAtStartOfDay(), getEndDate().plusDays(1).toDateTimeAtStartOfDay());
        return interval.containsNow();
    }

    @Atomic
    public void editEndDate(LocalDate endDate) {
        checkValues(getStudent(), getBeginDate(), endDate);
        setEndDate(endDate);
    }

    @Deprecated
    public boolean hasBeginDate() {
        return getBeginDate() != null;
    }

    @Deprecated
    public boolean hasEndDate() {
        return getEndDate() != null;
    }

    @Deprecated
    public boolean hasJobBankSystem() {
        return getJobBankSystem() != null;
    }

    @Deprecated
    public boolean hasStudentAuthorizationProcess() {
        return getStudentAuthorizationProcess() != null;
    }

    @Deprecated
    public boolean hasStudent() {
        return getStudent() != null;
    }

}
