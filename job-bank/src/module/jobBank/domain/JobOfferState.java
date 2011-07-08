package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum JobOfferState implements IPresentableEnum {
    ACTIVE("active", "label.jobOfferState.active"), WAITING_FOR_APPROVAL("waitingForApproval",
	    "label.jobOfferState.waitingForApproval"), UNDER_CONSTRUCTION("underConstruction",
	    "label.jobOfferState.underConstruction"), EXPIRED("expired", "label.jobOfferState.expired"), CANCELED("canceled",
	    "label.jobOfferState.canceled");

    private final String type;
    private final String nameKey;
    private final String bundle;

    private JobOfferState(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private JobOfferState(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }
}
