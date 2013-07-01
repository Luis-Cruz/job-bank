package module.jobBank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import module.jobBank.domain.beans.JobOfferNotificationFilterBean;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.VirtualHost;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;
import pt.ist.fenixframework.Atomic;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class JobOfferNotificationFilter extends JobOfferNotificationFilter_Base {

    public static final Comparator<JobOfferNotificationFilter> COMPARATOR_BY_DEGREE =
            new Comparator<JobOfferNotificationFilter>() {

                @Override
                public int compare(JobOfferNotificationFilter o1, JobOfferNotificationFilter o2) {
                    final FenixDegree degree1 = o1.getFenixDegree();
                    final FenixDegree degree2 = o2.getFenixDegree();

                    if (degree1 == null || degree2 == null) {
                        return -1;
                    }

                    return String.CASE_INSENSITIVE_ORDER.compare(degree1.getName().getContent(Language.getUserLanguage()),
                            degree2.getName().getContent(Language.getUserLanguage()));
                }

            };

    public JobOfferNotificationFilter() {
        super();
    }

    public void sendNotification(JobOffer jobOffer) {
        if (!checkFilterMatch(jobOffer)) {
            return;
        }

        final VirtualHost virtualHost = VirtualHost.getVirtualHostForThread();
        User user = getStudent().getUser();
        String subject, body;

        subject =
                BundleUtil.getStringFromResourceBundle("JOB_BANK_RESOURCES",
                        "message.jobbank.message.jobbank.email.notification.subject");
        body =
                BundleUtil.getFormattedStringFromResourceBundle("JOB_BANK_RESOURCES",
                        "message.jobbank.message.jobbank.email.notification.body", jobOffer.getEnterpriseName().getContent(),
                        jobOffer.getJobOfferType().getLocalizedName(), jobOffer.getFunctionDescription().getContent(), jobOffer
                                .getRequirements().getContent(), jobOffer.getTerms().getContent(), jobOffer.getVacancies()
                                .toString(), jobOffer.getPresentationPeriod());

        if (user.getEmail() != null) {
            List<String> toAddresses = new ArrayList<String>();
            toAddresses.add(user.getEmail());
            new Email(virtualHost.getApplicationSubTitle().getContent(), virtualHost.getSystemEmailAddress(), new String[] {},
                    toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, subject, body);
        }
    }

    private boolean checkFilterMatch(JobOffer jobOffer) {
        return ((jobOffer.getDegreeSet().contains(getFenixDegree()) || getFenixDegree() == null) && (jobOffer.getJobOfferType()
                .equals(getJobOfferType()) || getJobOfferType() == null));
    }

    @Atomic
    public static JobOfferNotificationFilter createNotification(JobOfferNotificationFilterBean bean, Student student) {
        JobOfferNotificationFilter filter = new JobOfferNotificationFilter();
        filter.setFenixDegree(bean.getDegree());
        filter.setJobOfferType(bean.getJobOfferType());
        filter.setStudent(student);
        filter.setJobBankSystem(JobBankSystem.getInstance());
        return filter;
    }

    @Atomic
    public void deleteFilter() {
        getStudent().removeJobOfferNotificationFilter(this);
        setStudent(null);
        if (getFenixDegree() != null) {
            getFenixDegree().removeJobOfferNotificationFilter(this);
            setFenixDegree(null);
        }
        getJobBankSystem().removeJobOfferNotificationFilters(this);
        setJobBankSystem(null);
        deleteDomainObject();
    }

    @Deprecated
    public boolean hasJobOfferType() {
        return getJobOfferType() != null;
    }

    @Deprecated
    public boolean hasFenixDegree() {
        return getFenixDegree() != null;
    }

    @Deprecated
    public boolean hasJobBankSystem() {
        return getJobBankSystem() != null;
    }

    @Deprecated
    public boolean hasStudent() {
        return getStudent() != null;
    }

}
