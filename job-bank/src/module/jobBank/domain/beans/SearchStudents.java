package module.jobBank.domain.beans;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.Student;
import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.Person;
import myorg.domain.util.Search;

public class SearchStudents extends Search<Person> {

    private boolean registrationConclued;
    private FenixDegree degree;
    private String username;

    public SearchStudents() {
	super();
	setRegistrationConclued(false);
    }

    protected class SearchResult extends SearchResultSet<Person> {

	public SearchResult(final Collection<? extends Person> c) {
	    super(c);
	}

	@Override
	protected boolean matchesSearchCriteria(final Person person) {
	    if (person == null) {
		return false;
	    }

	    return personMatchesSearch(person);
	}

	private boolean personMatchesSearch(Person person) {
	    String[] result = username.toLowerCase().split(" ");
	    String personName = person.getName().toLowerCase();

	    boolean ret = true;
	    
	    for (int i = 0; i < result.length; i++) {
		if (!personName.contains(result[i])) {
		    ret &= false;
		}
	    }

	    return ret;
	}
    }

    @Override
    public Set<Person> search() {
	return new SearchResult(readPersonsToSatisfizedPredicate());
    }

    private Set<Person> readPersonsToSatisfizedPredicate() {
	final Set<Person> results = new HashSet<Person>();
	Student.readAllStudents(new IPredicate<Student>() {
	    @Override
	    public boolean evaluate(Student student) {
		if (isSatisfiedConditions(student)) {
		    results.add(student.getPerson());
		    return true;
		}
		return false;
	    }
	});
	return results;
    }

    private boolean isSatisfiedConditions(Student student) {
	if (hasSelectedDegree()) {
	    return isSatisfiedDegree(student, hasSelectedRegistrationConlued());
	} else {
	    return isSatisfiedRegistrationProcess(student);
	}
    }

    private boolean isSatisfiedRegistrationProcess(Student student) {
	return !hasSelectedRegistrationConlued() || student.hasAnyConcludedRegistration();
    }

    private boolean isSatisfiedDegree(Student student, boolean concluded) {
	return student.hasAnyRegistrationWithDegree(getDegree(), concluded);
    }

    public boolean isRegistrationConclued() {
	return registrationConclued;
    }

    public void setRegistrationConclued(boolean completedDegree) {
	this.registrationConclued = completedDegree;
    }

    public void setDegree(FenixDegree degree) {
	this.degree = degree;
    }

    public FenixDegree getDegree() {
	return degree;
    }

    public boolean hasSelectedDegree() {
	return getDegree() != null;
    }

    public boolean hasSelectedRegistrationConlued() {
	return isRegistrationConclued();
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getUsername() {
	return username;
    }
}
