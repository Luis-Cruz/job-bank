package module.jobBank.domain;

import myorg.util.ClassNameBundle;

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
	Student student = Student.readCurrentStudent();
	if (student != null && student.canRemoveFile(this)) {
	    return true;
	}
	return false;
    }

}
