package module.jobBank.presentationTier.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.Student;
import module.organization.domain.Party;
import module.organization.domain.Person;
import pt.ist.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class StudentsAutoCompleteProvider implements AutoCompleteProvider {

    public Collection getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
	final List<Person> persons = new ArrayList<Person>();

	final String trimmedValue = value.trim();
	final String[] input = trimmedValue.split(" ");
	StringNormalizer.normalize(input);

	for (final Student student : JobBankSystem.getInstance().getStudents()) {
	    if (student.hasPerson()) {
		final Person person = student.getPerson();
		final String unitName = StringNormalizer.normalize(person.getPartyName().getContent());
		if (hasMatch(input, unitName)) {
		    persons.add(person);
		}
	    }
	}
	Collections.sort(persons, Party.COMPARATOR_BY_NAME);
	return persons;
    }

    private boolean hasMatch(final String[] input, final String unitNameParts) {
	for (final String namePart : input) {
	    if (unitNameParts.indexOf(namePart) == -1) {
		return false;
	    }
	}
	return true;
    }

}
