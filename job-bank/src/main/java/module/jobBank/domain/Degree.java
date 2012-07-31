package module.jobBank.domain;

import java.util.Set;

public class Degree {
    // @Service
    // public static Set<RemoteDegree> readRemoteBolonhaDegreesSet() {
    // JobBankSystem.getInstance();
    // RemoteHost remoteHost = JobBankSystem.readRemoteHost();
    // Collection<RemoteDegree> col =
    // RemoteAdministrativeOffice.readDegreeAdministrativeOffice(remoteHost)
    // .getAdministratedBolonhaDegrees();
    // return new HashSet<RemoteDegree>(col);
    // }
    //
    // @Service
    // public static List<RemoteDegree> readRemoteDegrees() {
    // return new ArrayList<RemoteDegree>(readRemoteBolonhaDegreesSet());
    // }
    //
    // public static Boolean Contains(RemoteDegree remoteDegree) {
    // return readRemoteBolonhaDegreesSet().contains(remoteDegree);
    // }

    public static Set<FenixDegree> readActiveFenixDegreeSet() {
	JobBankSystem jobBank = JobBankSystem.getInstance();
	return jobBank.getActiveFenixDegreeSet();
    }

    public static Set<FenixDegree> readActiveMasterFenixDegreeSet() {
	JobBankSystem jobBank = JobBankSystem.getInstance();
	return jobBank.getActiveMasterFenixDegreeSet();
    }

}
