package module.jobBank.presentationTier.provider;

import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.curriculumQualification.CurriculumQualificationType;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class CurriculumQualificationProvider implements DataProvider {

    @Override
    public Object provide(final Object source, final Object currentValue) {
	List<CurriculumQualificationType> types = new ArrayList<CurriculumQualificationType>();
	types.add(CurriculumQualificationType.INTERNACIONAL_EXPERIENCE);
	types.add(CurriculumQualificationType.PROFISSIONAL_EXPERIENCE);
	types.add(CurriculumQualificationType.EXTRACURRICULAR);
	types.add(CurriculumQualificationType.FORMATION);
	return types;
    }

    @Override
    public Converter getConverter() {
	return null;
    }

}
