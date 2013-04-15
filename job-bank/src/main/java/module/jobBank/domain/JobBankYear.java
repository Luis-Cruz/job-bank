package module.jobBank.domain;

import org.joda.time.DateTime;

public class JobBankYear extends JobBankYear_Base {

    private JobBankYear(final int year) {
        super();
        if (findJobBankYearByYearAux(year) != null) {
            throw new Error("There can only be one! (JobBankYear object for each year)");
        }
        setJobBankSystem(JobBankSystem.getInstance());
        setYear(new Integer(year));
        setCounter(Integer.valueOf(0));
    }

    private static JobBankYear findJobBankYearByYearAux(final int year) {
        final JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        for (final JobBankYear jobBankYear : jobBankSystem.getJobBankYearSet()) {
            if (jobBankYear.getYear().intValue() == year) {
                return jobBankYear;
            }
        }
        return null;
    }

    public static JobBankYear findJobBankYear(final int year) {
        final JobBankYear jobBankYear = findJobBankYearByYearAux(year);
        return jobBankYear == null ? new JobBankYear(year) : jobBankYear;
    }

    public Integer nextNumber() {
        return getNextNumber();
    }

    private Integer getNextNumber() {
        setCounter(getCounter().intValue() + 1);
        return getCounter();
    }

    public static JobBankYear getCurrentYear() {
        final int year = new DateTime().getYear();
        return findJobBankYear(year);
    }

    @Deprecated
    public boolean hasYear() {
        return getYear() != null;
    }

    @Deprecated
    public boolean hasCounter() {
        return getCounter() != null;
    }

    @Deprecated
    public java.util.Set<module.jobBank.domain.JobOffer> getJobOffer() {
        return getJobOfferSet();
    }

    @Deprecated
    public boolean hasAnyJobOffer() {
        return !getJobOfferSet().isEmpty();
    }

    @Deprecated
    public boolean hasJobBankSystem() {
        return getJobBankSystem() != null;
    }

}
