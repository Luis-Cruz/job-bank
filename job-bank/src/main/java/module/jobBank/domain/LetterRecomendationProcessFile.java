package module.jobBank.domain;

import pt.ist.bennu.core.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/JobBankResources")
public class LetterRecomendationProcessFile extends LetterRecomendationProcessFile_Base {

    public LetterRecomendationProcessFile(String displayName, String filename, byte[] content) {
	super();
	if (content != null) {
	    init(displayName, filename, content);
	}
    }

}
