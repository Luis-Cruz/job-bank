package module.jobBank.domain;

import java.math.BigDecimal;

import net.sourceforge.fenixedu.domain.student.RemoteRegistration;

import org.joda.time.DateTime;

public class StudentRegistration extends StudentRegistration_Base {

    public StudentRegistration(Student student, RemoteRegistration remoteRegistration, FenixDegree fenixDegree,
	    Boolean isConcluded, BigDecimal average) {
	super();
	setStudent(student);
	setRemoteRegistration(remoteRegistration);
	setNumber(remoteRegistration.getNumber());
	update(isConcluded, average, fenixDegree);
	setActiveBeginDate(new DateTime());
    }

    public void update(Boolean isConcluded, BigDecimal average, FenixDegree fenixDegree) {
	setIsConcluded(isConcluded);
	setAverage(average);
	setFenixDegree(fenixDegree);
    }

    public boolean isActive() {
	return getActiveEndDate() == null || getActiveEndDate().isAfterNow();
    }

}
