package module.jobBank.domain;

import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class FenixDegree extends FenixDegree_Base {
    
    public  FenixDegree() {
        super();
    }

    public FenixDegree(String presentationName) {
	updateName(presentationName);
	setActive(true);
    }

    public void updateName(String presentationName) {
	MultiLanguageString name = new MultiLanguageString();
	name.setContent(Language.pt, presentationName);
	setName(name);
    }
    
}
