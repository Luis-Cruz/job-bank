package module.jobBank.domain;

import java.util.Comparator;
import java.util.Set;

import module.jobBank.domain.beans.OfferCandidacyBean;
import module.jobBank.domain.utils.IPredicate;
import module.jobBank.domain.utils.Utils;
import module.workflow.domain.ProcessFile;

import org.joda.time.DateTime;

import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.fenixframework.Atomic;

public class OfferCandidacy extends OfferCandidacy_Base {

	public static final Comparator<OfferCandidacy> COMPARATOR_BY_OFFER_PROCESS_IDENTIFICATION = new Comparator<OfferCandidacy>() {

		@Override
		public int compare(OfferCandidacy o1, OfferCandidacy o2) {
			final JobOfferProcess p1 = o1.getJobOffer().getJobOfferProcess();
			final JobOfferProcess p2 = o2.getJobOffer().getJobOfferProcess();

			return JobOfferProcess.COMPARATOR_BY_REGISTRATION.compare(p1, p2);
		}

	};

	private OfferCandidacy(Student student, JobOffer jobOffer) {
		super();
		setJobBankSystem(JobBankSystem.getInstance());
		setStudent(student);
		setJobOffer(jobOffer);
		setCreationDate(new DateTime());
		setModifiedDate(new DateTime());
		setCanceled(false);

	}

	private OfferCandidacy(OfferCandidacyBean bean) {
		this(bean.getStudent(), bean.getJobOffer());
		for (ProcessFile file : bean.getAttachFiles()) {
			addProcessFiles(file);
		}

	}

	// If job offer is external then the application process is not managed by
	// system
	public static boolean canCreateOfferCandidacy(Student student, JobOffer jobOffer) {
		for (OfferCandidacy offerCandidacy : student.getActiveOfferCandidacies()) {
			if (offerCandidacy.isCandidacyToOffer(jobOffer)) {
				return false;
			}
		}
		return true;
	}

	@Atomic
	public static void createOfferCandidacy(Student student, JobOffer jobOffer) {
		if (canCreateOfferCandidacy(student, jobOffer)) {
			new OfferCandidacy(student.getPerson().getStudent(), jobOffer);
		} else {
			throw new DomainException("message.error.offerCandidacy.already.applied.for.this.offer",
					DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
		}
	}

	@Atomic
	public static void createOfferCandidacy(OfferCandidacyBean bean) {
		checkConstraints(bean);

		if (canCreateOfferCandidacy(bean.getStudent(), bean.getJobOffer())) {
			new OfferCandidacy(bean);
		} else {
			throw new DomainException("message.error.offerCandidacy.already.applied.for.this.offer",
					DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
		}
	}

	private static void checkConstraints(OfferCandidacyBean bean) {
		if (!bean.getJobOffer().isExternalCandidacy() && bean.getAttachFiles().size() == 0) {
			throw new DomainException("message.no.document.selected.on.joboffer.candidacy",
					DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
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

	@Atomic
	public void removeCandidacy() {
		setCanceled(true);
		setModifiedDate(new DateTime());
	}

	public static Set<OfferCandidacy> readOfferCandidacies(IPredicate<OfferCandidacy> predicate) {
		JobBankSystem jobBankSystem = JobBankSystem.getInstance();
		return Utils.readValuesToSatisfiedPredicate(predicate, jobBankSystem.getOfferCandidaciesSet());
	}

	public boolean getCanSelectCandidacy() {
		JobBankSystem jobBankSystem = JobBankSystem.getInstance();
		return (jobBankSystem.isEnterpriseActiveMember() || jobBankSystem.isNPEMember()) && getJobOffer().hasVacancies()
				&& getJobOfferSelectCandidacy() == null && getJobOffer().isSelectionPeriod();
	}

	public boolean getCanRemoveCandidacy() {
		JobBankSystem jobBankSystem = JobBankSystem.getInstance();
		return (jobBankSystem.isEnterpriseActiveMember() || jobBankSystem.isNPEMember()) && getJobOfferSelectCandidacy() != null
				&& getJobOffer().isSelectionPeriod();
	}

	public static OfferCandidacy getOfferCandidacy(Student student, JobOffer jobOffer) {
		for (OfferCandidacy offerCandidacy : student.getActiveOfferCandidacies()) {
			if (offerCandidacy.isCandidacyToOffer(jobOffer)) {
				return offerCandidacy;
			}
		}
		return null;
	}

	public static Set<ProcessFile> getStudentFilesForJobOfferCandidacy(Student student, JobOffer jobOffer) {
		OfferCandidacy candidacy = getOfferCandidacy(student, jobOffer);
		return candidacy.getProcessFilesSet();
	}

	@Deprecated
	public boolean hasCreationDate() {
		return getCreationDate() != null;
	}

	@Deprecated
	public boolean hasModifiedDate() {
		return getModifiedDate() != null;
	}

	@Deprecated
	public java.util.Set<module.workflow.domain.ProcessFile> getProcessFiles() {
		return getProcessFilesSet();
	}

	@Deprecated
	public boolean hasAnyProcessFiles() {
		return !getProcessFilesSet().isEmpty();
	}

	@Deprecated
	public boolean hasJobOffer() {
		return getJobOffer() != null;
	}

	@Deprecated
	public boolean hasJobBankSystem() {
		return getJobBankSystem() != null;
	}

	@Deprecated
	public boolean hasJobOfferSelectCandidacy() {
		return getJobOfferSelectCandidacy() != null;
	}

	@Deprecated
	public boolean hasStudent() {
		return getStudent() != null;
	}

}
