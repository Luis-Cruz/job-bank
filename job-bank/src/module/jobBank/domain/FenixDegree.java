package module.jobBank.domain;

import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class FenixDegree extends FenixDegree_Base {
    
    public  FenixDegree() {
        super();
    }

    public FenixDegree(String presentationName, String degreeTypeName) {
	updateName(presentationName);
	setActive(true);
	setDegreeType(FenixDegreeType.getByFenixDegreeTypeByName(degreeTypeName));
    }

    public void updateName(String presentationName) {
	MultiLanguageString name = new MultiLanguageString();
	name.setContent(Language.pt, presentationName);
	setName(name);
    }

    public boolean isBolonhaMasterDegree() {
	return getDegreeType().equals(FenixDegreeType.BOLONHA_MASTER_DEGREE)
		|| getDegreeType().equals(FenixDegreeType.BOLONHA_INTEGRATED_MASTER_DEGREE);
    }
    
}
