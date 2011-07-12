package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum EnterpriseStateType implements IPresentableEnum {
    ALL("all", "label.enterpriseStateType.all"), PENDING_REGISTER("pendingRegister", "label.enterpriseStateType.pendingRegister"), ACTIVE(
	    "active",
	    "label.enterpriseStateType.active"), INACTIVE("inactive", "label.enterpriseStateType.inactive"), REQUEST_CHANGE_AGREEMENT(
	    "requestChangeAgreement", "label.enterpriseStateType.changeToRequestAgreement"), REJECTED("rejected",
	    "label.enterpriseStateType.rejected");

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

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }

    public static EnterpriseStateType getByLocalizedName(String pattern) {
	for (EnterpriseStateType est : EnterpriseStateType.values()) {
	    if (est.getLocalizedName().equals(pattern)) {
		return est;
	    }
	}
	return null;
    }
}
