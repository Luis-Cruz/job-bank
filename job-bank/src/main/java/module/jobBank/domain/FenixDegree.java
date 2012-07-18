package module.jobBank.domain;

import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class FenixDegree extends FenixDegree_Base {

    public FenixDegree() {
	super();
    }

    public FenixDegree(String presentationName, String degreeTypeName) {
	updateName(presentationName);
	setActive(true);
	setDegreeType(FenixDegreeType.getByFenixDegreeTypeByName(degreeTypeName));
    }

    public void updateName(String presentationName) {
	setName(new MultiLanguageString(Language.pt, presentationName));
    }

    public boolean isBolonhaMasterDegree() {
	return getDegreeType().equals(FenixDegreeType.BOLONHA_MASTER_DEGREE)
		|| getDegreeType().equals(FenixDegreeType.BOLONHA_INTEGRATED_MASTER_DEGREE);
    }

    public static FenixDegree getFenixDegreeByIdInternal(int IdInternal) {
	JobBankSystem jobBank = JobBankSystem.getInstance();

	for (FenixDegree degree : jobBank.getActiveFenixDegreeSet()) {
	    if (degree.getIdInternal() == IdInternal) {
		return degree;
	    }
	}

	return null;
    }

}
