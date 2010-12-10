package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum EnterpriseStateType implements IPresentableEnum {
    PENDING("pending", "label.enterpriseStateType.pending"), ACTIVE("active", "label.enterpriseStateType.active"), INACTIVE(
	    "inactive", "label.enterpriseStateType.inactive"), REQUEST_CHANGE_AGREEMENT("requestChangeAgreement",
	    "label.enterpriseStateType.changeToRequestAgreement"), EXPIRED("expired", "label.enterpriseStateType.expired");

    private final String type;
    private final String nameKey;
    private final String bundle;

    private EnterpriseStateType(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private EnterpriseStateType(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }
}
