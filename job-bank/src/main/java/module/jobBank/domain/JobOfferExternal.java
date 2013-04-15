package module.jobBank.domain;

import module.jobBank.domain.beans.JobOfferBean;

public class JobOfferExternal extends JobOfferExternal_Base {

    public JobOfferExternal(JobOfferBean bean) {
        setCreateJobOffer(bean);
        setExternalLink(bean.getExternalLink());
    }

    @Override
    public boolean isExternalCandidacy() {
        return true;
    }

    @Override
    public JobOfferExternal getJobOfferExternal() {
        return this;
    }

    @Deprecated
    public boolean hasExternalLink() {
        return getExternalLink() != null;
    }

}
