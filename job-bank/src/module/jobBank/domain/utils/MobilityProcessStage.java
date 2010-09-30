package module.jobBank.domain.utils;

import module.jobBank.domain.JobBankSystem;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum MobilityProcessStage implements IPresentableEnum {
    EDITABLE, PUBLISHED, CANDIDATED;

    private static final String BUNDLE = JobBankSystem.JOB_BANK_RESOURCES;
    private static final String KEY_PREFIX = "label.MobilityProcessStage.";
    private static final String KEY_PREFIX_DESCRIPTION = "label.MobilityProcessStage.description.";

    public String getLocalizedName() {
	final String key = KEY_PREFIX + name();
	return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }

    public String getLocalizedDescription() {
	final String key = KEY_PREFIX_DESCRIPTION + name();
	return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }

}
