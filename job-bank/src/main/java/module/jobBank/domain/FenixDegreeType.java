package module.jobBank.domain;

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;

public enum FenixDegreeType implements IPresentableEnum {
    BOLONHA_DEGREE("bolonhaDegree", "label.fenixDegreeType.bolonhaDegree"), BOLONHA_MASTER_DEGREE("bolonhaMasterDegree",
	    "label.fenixDegreeType.bolonhaMasterDegree"), BOLONHA_INTEGRATED_MASTER_DEGREE("bolonhaIntegratedMasterDegree",
	    "label.fenixDegreeType.bolonhaIntegratedMasterDegree");

    private final String type;
    private final String nameKey;
    private final String bundle;

    private FenixDegreeType(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private FenixDegreeType(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }

    public static FenixDegreeType getByFenixDegreeTypeByName(String name) {

	if (name.equals("BOLONHA_DEGREE")) {
	    return BOLONHA_DEGREE;
	} else if (name.equals("BOLONHA_MASTER_DEGREE")) {
	    return BOLONHA_MASTER_DEGREE;
	} else if (name.equals("BOLONHA_INTEGRATED_MASTER_DEGREE")) {
	    return BOLONHA_INTEGRATED_MASTER_DEGREE;
	}

	return null;
    }
}
