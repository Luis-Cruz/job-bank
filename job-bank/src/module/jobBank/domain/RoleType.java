package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum RoleType implements IPresentableEnum {
    NPE("profissionalStage", "label.jobOfferType.profissionalStage"), STUDENT("profissionalStage",
	    "label.jobOfferType.profissionalStage"), ENTERPRISE("profissionalStage", "label.jobOfferType.profissionalStage");
    private final String type;
    private final String nameKey;
    private final String bundle;

    private RoleType(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private RoleType(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }
}
