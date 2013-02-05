package module.jobBank.domain;

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum JobOfferType implements IPresentableEnum {
    ALL("all", "label.jobOfferType.all"), PROFISSIONAL_STAGE("profissionalStage", "label.jobOfferType.profissionalStage"),
    EXTRACURRICULAR_STAGE("extracurricularStage", "label.jobOfferType.extracurricularStage"), SUMMER_STAGE("summerStage",
            "label.jobOfferType.summerStage"),
    NATIONAL_EMPLOYMENT("nationalEmployment", "label.jobOfferType.nationalEmployment"), INTERNATIONAL_EMPLOYMENT(
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

    @Override
    public String getLocalizedName() {
        return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }

    public static JobOfferType getByName(String pattern) {
        for (JobOfferType jot : JobOfferType.values()) {
            if (jot.name().equals(pattern)) {
                return jot;
            }
        }
        return null;
    }
}
