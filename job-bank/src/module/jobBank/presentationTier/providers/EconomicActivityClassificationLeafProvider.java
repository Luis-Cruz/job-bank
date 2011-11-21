package module.jobBank.presentationTier.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import myorg.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import pt.ist.jpdafinance.pt.EconomicActivityClassification;
import pt.ist.jpdafinance.pt.EconomicActivityClassificationGroup;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class EconomicActivityClassificationLeafProvider implements AutoCompleteProvider {

    @Override
    public Collection getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
	final List<EconomicActivityClassification> classifications = new ArrayList<EconomicActivityClassification>();

	final String trimmedValue = value.trim();
	if (trimmedValue.length() > 2) {
	    final String[] input = trimmedValue.split(" ");
	    StringNormalizer.normalize(input);

	    for (final EconomicActivityClassificationGroup group : EconomicActivityClassificationGroup.getCassificationGroups()) {
		if (match(input, group)) {
		    classifications.addAll(group.getClassifications());
		} else {
		    for (final EconomicActivityClassification classification : group.getClassifications()) {
			if (match(input, classification)) {
			    classifications.add(classification);
			}
		    }
		}
	    }
	}

	Collections.sort(classifications);
	return classifications;
    }

    private boolean match(final String[] input, final EconomicActivityClassification classification) {
	if (input.length == 0) {
	    return false;
	}
	final String description = StringNormalizer.normalize(classification.description);
	for (final String string : input) {
	    if (description.indexOf(string) < 0) {
		return false;
	    }
	}
	return true;
    }

}
