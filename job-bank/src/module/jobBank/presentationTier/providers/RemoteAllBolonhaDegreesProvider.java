package module.jobBank.presentationTier.providers;

import module.jobBank.domain.Degree;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class RemoteAllBolonhaDegreesProvider implements DataProvider {

    @Override
    public Object provide(final Object source, final Object currentValue) {
	return Degree.readActiveFenixDegreeSet();
    }

    @Override
    public Converter getConverter() {
	return null;
    }

}
