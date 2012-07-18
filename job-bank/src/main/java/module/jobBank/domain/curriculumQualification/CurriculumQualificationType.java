package module.jobBank.domain.curriculumQualification;

import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum CurriculumQualificationType implements IPresentableEnum {
    PROFISSIONAL_EXPERIENCE("ProfissionalExperience", "label.curriculum.profissionalExperience"), INTERNACIONAL_EXPERIENCE(
	    "InternacionalExperience", "label.curriculum.internacionalExperience"), FORMATION("Formation",
	    "label.curriculum.formation"), EXTRACURRICULAR("Extracurricular", "label.curriculum.extracurricular");

    private final String type;
    private final String nameKey;
    private final String bundle;

    private CurriculumQualificationType(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private CurriculumQualificationType(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    public String getLocalizedName() {
	return BundleUtil.getMultilanguageString(bundle, nameKey).getContent();
    }
}
