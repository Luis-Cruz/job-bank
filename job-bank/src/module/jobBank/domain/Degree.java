package module.jobBank.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sourceforge.fenixedu.domain.RemoteDegree;
import net.sourceforge.fenixedu.domain.administrativeOffice.RemoteAdministrativeOffice;
import pt.ist.fenixWebFramework.services.Service;
import pt.ist.fenixframework.plugins.remote.domain.RemoteHost;

public class Degree {
    @Service
    public static Set<RemoteDegree> readRemoteDegreesSet() {
	JobBankSystem.getInstance();
	RemoteHost remoteHost = JobBankSystem.readRemoteHost();
	return RemoteAdministrativeOffice.readDegreeAdministrativeOffice(remoteHost).getAdministratedDegrees();
    }

    @Service
    public static List<RemoteDegree> readRemoteDegrees() {
	return new ArrayList<RemoteDegree>(readRemoteDegreesSet());
    }

    public static Boolean Contains(RemoteDegree remoteDegree) {
	return readRemoteDegreesSet().contains(remoteDegree);
    }

}
