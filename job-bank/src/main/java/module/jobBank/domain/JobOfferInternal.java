package module.jobBank.domain;

import module.jobBank.domain.beans.JobOfferBean;

public class JobOfferInternal extends JobOfferInternal_Base {

    public JobOfferInternal(JobOfferBean bean) {
        setCreateJobOffer(bean);
    }

    @Override
    public JobOfferExternal getJobOfferExternal() {
        return null;
    }

    @Override
    public boolean isExternalCandidacy() {
        return false;
    }

}
