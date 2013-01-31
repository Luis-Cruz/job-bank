package module.jobBank.domain.task;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.util.BundleUtil;

public class ExpireEnterpriseTermsOfResponsibilityTask extends ExpireEnterpriseTermsOfResponsibilityTask_Base {

	public ExpireEnterpriseTermsOfResponsibilityTask() {
		super();
	}

	@Override
	public void executeTask() {
		for (Enterprise enterprise : JobBankSystem.getInstance().getEnterprises()) {
			enterprise.setAcceptedTermsOfResponsibilityForCurrentYear(false);
		}
	}

	@Override
	public String getLocalizedName() {
		return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
	}

}
