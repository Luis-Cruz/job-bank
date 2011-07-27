package module.jobBank.domain.task;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.Degree;
import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.FenixDegreeType;
import module.jobBank.domain.JobBankSystem;
import myorg.util.BundleUtil;
import net.sourceforge.fenixedu.domain.RemoteDegree;

public class CreateOrUpdateFenixDegreesTask extends UpdateExpiredEnterpriseAgreementsTask_Base {
    
    public  CreateOrUpdateFenixDegreesTask() {
        super();
    }
    
    @Override
    public void executeTask() {

	JobBankSystem jobBank = JobBankSystem.getInstance();
	Set<RemoteDegree> remoteDegrees = Degree.readRemoteBolonhaDegreesSet();
	Set<RemoteDegree> localDegrees = jobBank.getRemoteDegreesFromLocalDegrees();
	
	updateIntersectingDegrees(jobBank, remoteDegrees, localDegrees);
	createInexistentFenixDegrees(jobBank, remoteDegrees, localDegrees);
	updateNotActiveDegrees(jobBank, remoteDegrees, localDegrees);

    }

    private void updateIntersectingDegrees(JobBankSystem jobBank, Set<RemoteDegree> remoteDegrees, Set<RemoteDegree> localDegrees) {
	Set<RemoteDegree> intersection = new HashSet<RemoteDegree>();
	intersection.addAll(remoteDegrees);
	intersection.retainAll(localDegrees);
	
	for (RemoteDegree remote : intersection) {
	    FenixDegree degree = jobBank.getFenixDegreeFor(remote);
	    if (degree != null) {
		degree.updateName(remote.getPresentationName());
		degree.setDegreeType(FenixDegreeType.getByFenixDegreeTypeByName(remote.getDegreeTypeName()));
		degree.setActive(true);
	    }
	}
    }

    private void updateNotActiveDegrees(JobBankSystem jobBank, Set<RemoteDegree> remoteDegrees, Set<RemoteDegree> localDegrees) {
	Set<RemoteDegree> notActiveDegrees = new HashSet<RemoteDegree>();
	notActiveDegrees.addAll(localDegrees);
	notActiveDegrees.removeAll(remoteDegrees);

	for (RemoteDegree remote : notActiveDegrees) {
	    FenixDegree degree = jobBank.getFenixDegreeFor(remote);
	    if (degree != null) {
		degree.setActive(false);
	    }
	}
    }

    private void createInexistentFenixDegrees(JobBankSystem jobBank, Set<RemoteDegree> remoteDegrees,
	    Set<RemoteDegree> localDegrees) {
	Set<RemoteDegree> inexistentFD = new HashSet<RemoteDegree>();
	inexistentFD.addAll(remoteDegrees);
	inexistentFD.removeAll(localDegrees);

	for (RemoteDegree remote : inexistentFD) {
	    FenixDegree fenixDegree = new FenixDegree(remote.getPresentationName(), remote.getDegreeTypeName());
	    jobBank.addFenixDegree(fenixDegree);
	    fenixDegree.setRemoteDegree(remote);
	}
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
    }
}
