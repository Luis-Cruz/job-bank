package module.jobBank.domain;

import java.util.Set;

import module.jobBank.domain.utils.IPredicate;
import myorg.domain.exceptions.DomainException;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class OfferCandidacy extends OfferCandidacy_Base {

    private OfferCandidacy(Student student, JobOffer jobOffer) {
	super();
	setJobBankSystem(JobBankSystem.getInstance());
	setStudent(student);
	setJobOffer(jobOffer);
	setCreationDate(new DateTime());
	setModifiedDate(new DateTime());
	setCanceled(false);
    }

    // If job offer is external then the application process is not managed by
    // system
    public static boolean canCreateOfferCandidacy(Student student, JobOffer jobOffer) {
	for (OfferCandidacy offerCandidacy : student.getActiveOfferCandidacies()) {
	    if (offerCandidacy.isCandidacyToOffer(jobOffer) || jobOffer.isExternalCandidacy()) {
		return false;
	    }
	}
	return true;
    }

    @Service
    public static void createOfferCandidacy(Student student, JobOffer jobOffer) {
	if (canCreateOfferCandidacy(student, jobOffer)) {
	    new OfferCandidacy(student.getPerson().getStudent(), jobOffer);
	} else {
	    throw new DomainException("message.error.offerCandidacy.already.applied.for.this.offer", DomainException
		    .getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	}
    }

    public boolean isCandidacyToOffer(JobOffer jobOffer) {
	return hasJobOffer() && getJobOffer().equals(jobOffer);
    }

    public boolean isCanceled() {
	return getCanceled();

    }

    public boolean isActive() {
	return !isCanceled();
    }

    public boolean getSelected() {
	return isSelected();
    }

    public boolean isSelected() {
	return getJobOfferSelectCandidacy() != null;
    }

    @Service
    public void removeCandidacy() {
	setCanceled(true);
	setModifiedDate(new DateTime());
    }

    public static Set<OfferCandidacy> readOfferCandidacies(IPredicate<OfferCandidacy> predicate) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	return jobBankSystem.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getOfferCandidaciesSet());
    }

    public boolean getCanSelectCandidacy() {
	return getJobOffer().hasVacancies() && getJobOfferSelectCandidacy() == null && getJobOffer().isSelectionPeriod();
    }

    public boolean getCanRemoveCandidacy() {
	return getJobOfferSelectCandidacy() != null && getJobOffer().isSelectionPeriod();
    }

}
