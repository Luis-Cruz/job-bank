package module.jobBank.presentationTier.renderer;

import pt.ist.fenixWebFramework.rendererExtensions.AutoCompleteInputRenderer;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;
import pt.ist.standards.economic.pt.EconomicActivityClassificationGroup;

public class EconomicClassificationAutoCompleteRenderer extends AutoCompleteInputRenderer {

    @Override
    protected Converter createConverter() {
        return new EconomicClassificationAutoCompleteConverter();
    }

    protected static class EconomicClassificationAutoCompleteConverter extends Converter {

        public EconomicClassificationAutoCompleteConverter() {
            super();

        }

        @Override
        public Object convert(Class type, Object value) {
            if (value == null || "".equals(value)) {
                return null;
            }

            String text = (String) value;

            if (text.equals(TYPING_VALUE)) {
                return null;
            }

            String key = text;
            return EconomicActivityClassificationGroup.importFromString(key);
        }

    }

}
