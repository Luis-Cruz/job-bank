package module.jobBank.domain;

import myorg.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/JobBankResources")
public class LetterRecomendationProcessFile extends CoverLetterProcessFile_Base {

    public LetterRecomendationProcessFile(String displayName, String filename, byte[] content) {
	super();
	if (content != null) {
	    init(displayName, filename, content);
	}
    }

}
