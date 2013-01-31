package module.jobBank.domain.enums;

import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum CandidacyType implements IPresentableEnum {
	Internal("internal", "label.jobOfferCandidancyType.internal"), External("external", "label.jobOfferCandidancyType.external");
	private final String type;
	private final String nameKey;
	private final String bundle;

	private CandidacyType(final String type, final String nameKey, final String bundle) {
		this.type = type;
		this.nameKey = nameKey;
		this.bundle = bundle;
	}

	private CandidacyType(final String type, final String nameKey) {
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
