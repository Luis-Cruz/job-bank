package module.jobBank.domain.utils;

import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum JobBankProcessStage implements IPresentableEnum {
	UNDER_CONTRUCTION, UNDER_APPROVAL, APROVED, PUBLISHED, SELECTION;

	private static final String BUNDLE = JobBankSystem.JOB_BANK_RESOURCES;
	private static final String KEY_PREFIX = "label.JobBankProcessStage.";
	private static final String KEY_PREFIX_DESCRIPTION = "label.JobBankProcessStage.description.";

	@Override
	public String getLocalizedName() {
		final String key = KEY_PREFIX + name();
		return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
	}

	public String getLocalizedDescription() {
		final String key = KEY_PREFIX_DESCRIPTION + name();
		return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
	}

}
