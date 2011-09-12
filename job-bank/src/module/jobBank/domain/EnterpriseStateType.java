package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

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

    public int compareto(EnterpriseStateType state) {
	if (equals(state)) {
	    return 0;
	}

	if (equals(PENDING_REGISTER)) {
	    return -1;
	}

	if (state.equals(PENDING_REGISTER)) {
	    return 1;
	}

	if (equals(REQUEST_CHANGE_AGREEMENT)) {
	    return -1;
	}

	if (state.equals(REQUEST_CHANGE_AGREEMENT)) {
	    return 1;
	}

	return StringNormalizer.normalize(getType()).compareTo(StringNormalizer.normalize(state.getType()));
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }

    public static EnterpriseStateType getByName(String pattern) {
	for (EnterpriseStateType est : EnterpriseStateType.values()) {
	    if (est.name().equals(pattern)) {
		return est;
	    }
	}
	return null;
    }
}
