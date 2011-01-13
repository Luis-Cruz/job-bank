package module.jobBank.presentationTier.providers;

import module.jobBank.domain.beans.OfferCandidacyBean;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class OfferCandidacyAttachFilesProvider implements DataProvider {

    @Override
    public Object provide(final Object source, final Object currentValue) {
	return ((OfferCandidacyBean) source).getStudent().getCurriculum().getCurriculumProcess().getFiles();
    }

    @Override
    public Converter getConverter() {
	return null;
    }

}
