package module.jobBank.domain;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/JobBankResources")
public class CurriculumProcessFile extends CurriculumProcessFile_Base {

    public CurriculumProcessFile(String displayName, String filename, byte[] content) {
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