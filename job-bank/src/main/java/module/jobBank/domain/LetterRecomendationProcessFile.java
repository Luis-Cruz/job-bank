package module.jobBank.domain;

import module.workflow.domain.ProcessDocumentMetaDataResolver;
import module.workflow.domain.ProcessFile;
import pt.ist.bennu.core.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/JobBankResources")
public class LetterRecomendationProcessFile extends LetterRecomendationProcessFile_Base {

	@Override
	public ProcessDocumentMetaDataResolver<? extends ProcessFile> getMetaDataResolver() {
		return new CurriculumProcess.CurriculumRelatedFileMetadataResolver();
	}

	public LetterRecomendationProcessFile(String displayName, String filename, byte[] content) {
		super();
		if (content != null) {
			init(displayName, filename, content);
		}
	}

}
