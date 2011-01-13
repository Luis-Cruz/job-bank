package module.jobBank.domain.beans;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import module.organization.domain.Person;
import myorg.domain.MyOrg;
import myorg.domain.util.Search;

public class SearchUsers extends Search<Person> {

    private String username;
    private Person person;

    protected class SearchResult extends SearchResultSet<Person> {

	public SearchResult(final Collection<? extends Person> c) {
	    super(c);
	}

	@Override
	protected boolean matchesSearchCriteria(final Person person) {
	    if (person == null) {
		return false;
	    }
	    return matchCriteria(username, person.getUser().getUsername());
	}
    }

    @Override
    public Set<Person> search() {
	final Person person = getPerson();
	if (person != null) {
	    final Set<Person> persons = new HashSet<Person>();
	    persons.add(person);
	    return persons;
	}
	final Set<Person> users = username != null ? MyOrg.getInstance().getUserSet() : Collections.EMPTY_SET;
	return new SearchResult(users);
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public void setPerson(Person person) {
	this.person = person;
    }

    public Person getPerson() {
	return person;
    }

}
