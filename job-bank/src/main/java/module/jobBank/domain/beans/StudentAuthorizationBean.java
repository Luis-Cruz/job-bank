package module.jobBank.domain.beans;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.StudentAuthorization;
import module.webserviceutils.domain.HostSystem;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.util.Search;
import pt.ist.fenixframework.Atomic;

public class StudentAuthorizationBean extends Search<StudentAuthorization> {

    private String username;

    private LocalDate beginDate;

    private LocalDate endDate;

    public StudentAuthorizationBean() {
        super();
        setBeginDate(new LocalDate());
        setEndDate(getBeginDate().plusYears(1));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = StringUtils.trimToNull(username);
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public Set<StudentAuthorization> search() {
        final Set<StudentAuthorization> results = new HashSet<StudentAuthorization>();
        for (StudentAuthorization studentAuthorization : JobBankSystem.getInstance().getStudentAuthorizationSet()) {
            if (studentAuthorization.getIsActive() && satisfiedCondition(studentAuthorization)) {
                results.add(studentAuthorization);
            }
        }
        return results;
    }

    protected boolean satisfiedCondition(StudentAuthorization studentAuthorization) {
        return StringUtils.isEmpty(getUsername())
                || studentAuthorization.getStudent().getPerson().getUser().getUsername().equals(getUsername());
    }

    @Atomic
    public StudentAuthorization createStudentAuthorization() {
        return new StudentAuthorization(getUsername(), getBeginDate(), getEndDate());
    }

    public Boolean getIsStudent() {
        if (!StringUtils.isBlank(getUsername())) {
            User user = User.findByUsername(getUsername());
            if (user != null && user.getPerson() != null && user.getPerson().getStudent() != null) {
                return true;
            }
            return Boolean.parseBoolean(HostSystem.getFenixJerseyClient().method("remotePerson").arg("username", getUsername())
                    .arg("method", "hasStudent").get());
        }
        return false;
    }

}
