package module.jobBank.domain;

import java.util.Set;

public class Degree {

    public static Set<FenixDegree> readActiveFenixDegreeSet() {
        JobBankSystem jobBank = JobBankSystem.getInstance();
        return jobBank.getActiveFenixDegreeSet();
    }

    public static Set<FenixDegree> readActiveMasterFenixDegreeSet() {
        JobBankSystem jobBank = JobBankSystem.getInstance();
        return jobBank.getActiveMasterFenixDegreeSet();
    }

}
