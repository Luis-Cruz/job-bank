package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum JobOfferState implements IPresentableEnum {
    UNDER_CONSTRUCTION("underConstruction", "label.jobOfferState.underConstruction"), WAITING_FOR_APPROVAL("waitingForApproval",
	    "label.jobOfferState.waitingForApproval"), APPROVED("approved", "label.jobOfferState.approved"), PUBLISHED(
	    "published", "label.jobOfferState.published"), UNDER_SELECTION("underselection", "label.jobOfferState.underselection"), ARCHIVED(
	    "archived", "label.jobOfferState.archived"), CANCELED("canceled", "label.jobOfferState.canceled");

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

    public static JobOfferState getByLocalizedName(String pattern) {
	for (JobOfferState jos : JobOfferState.values()) {
	    if (jos.getLocalizedName().equals(pattern)) {
		return jos;
	    }
	}
	return null;
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }
}
