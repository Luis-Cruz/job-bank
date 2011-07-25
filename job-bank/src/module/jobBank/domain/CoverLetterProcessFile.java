package module.jobBank.domain;

import myorg.applicationTier.Authenticate.UserView;
import myorg.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/JobBankResources")
public class CoverLetterProcessFile extends CoverLetterProcessFile_Base {

    public CoverLetterProcessFile(String displayName, String filename, byte[] content) {
	super();
	if (content != null) {
	    init(displayName, filename, content);
	}
    }

    @Override
    public boolean isPossibleToArchieve() {
	Student student = UserView.getCurrentUser().getPerson().getStudent();
	if (student != null && student.canRemoveFile(this)) {
	    return true;
	}
	return false;
    }

}
