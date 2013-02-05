package module.jobBank.domain;

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum FenixCycleType implements IPresentableEnum {
    FIRST_CYCLE,

    SECOND_CYCLE;

    private static final String BUNDLE = "resources.JobBankResources";
    private static final String KEY_PREFIX = "label.JobBank.FenixCycleType.";

    @Override
    public String getLocalizedName() {
        final String key = KEY_PREFIX + name();
        return BundleUtil.getStringFromResourceBundle(BUNDLE, key);
    }

    public static FenixCycleType getFenixCycleType(String cycleTypeName) {
        try {
            return valueOf(cycleTypeName);
        } catch (IllegalArgumentException e) {
        }
        return null;
    }
}
