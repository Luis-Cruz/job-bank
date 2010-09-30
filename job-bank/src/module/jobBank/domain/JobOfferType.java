package module.jobBank.domain;

import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum JobOfferType implements IPresentableEnum {
    PROFISSIONAL_STAGE("profissionalStage", "label.jobOfferType.profissionalStage"), EXTRACURRICULAR_STAGE(
	    "extracurricularStage", "label.jobOfferType.extracurricularStage"), SUMMER_STAGE("summerStage",
	    "label.jobOfferType.summerStage"), NATIONAL_EMPLOYMENT("nationalEmployment", "label.jobOfferType.nationalEmployment"), INTERNATIONAL_EMPLOYMENT(
	    "InternationalEmployment", "label.jobOfferType.internationalEmployment"), RESEARCH("research",
	    "label.jobOfferType.research");

    private final String type;
    private final String nameKey;
    private final String bundle;

    private JobOfferType(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private JobOfferType(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }
}
