package module.jobBank.domain;

import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import myorg.domain.exceptions.DomainException;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class CandidateOffer extends CandidateOffer_Base {

    private CandidateOffer(Student student, JobOffer jobOffer) {
	super();
	setJobBankSystem(JobBankSystem.getInstance());
	setStudent(student);
	setJobOffer(jobOffer);
	setCreationDate(new DateTime());
	setModifiedDate(new DateTime());
	setSelected(false);
	setCanceled(false);
    }

    public static boolean canCreateCandidateOffer(Student student, JobOffer jobOffer) {
	for (CandidateOffer candidateOffer : student.getActiveCandidateOffers()) {
	    if (candidateOffer.isCandidateToOffer(jobOffer)) {
		return false;
	    }
	}
	return true;
    }

    @Service
    public static void createCandidateOffer(Student student, JobOffer jobOffer) {
	if (canCreateCandidateOffer(student, jobOffer)) {
	    new CandidateOffer(student.getPerson().getStudent(), jobOffer);
	} else {
	    throw new DomainException("message.error.candidateOffer.already.applied.for.this.offer", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
    }

    public boolean isCandidateToOffer(JobOffer jobOffer) {
	return hasJobOffer() && getJobOffer().equals(jobOffer);
    }

    public boolean isSelected() {
	return getSelected();
    }

    @Service
    public void selectCandidate() {
	setSelected(true);
    }

    @Service
    public void removeCandidate() {
	setSelected(false);

    }

    public boolean isCanceled() {
	return getCanceled();

    }

    public boolean isActive() {
	return !isCanceled();

    }

    @Service
    public void removeCandidancy() {
	setCanceled(true);
	setModifiedDate(new DateTime());
    }

    public static Set<CandidateOffer> readCandidateOffers(IPredicate<CandidateOffer> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getCandidateOffersSet());
    }

    public boolean getCanSelectCandidate() {
	return getJobOffer().isCandidancyPeriod() && !isSelected();
    }
}
