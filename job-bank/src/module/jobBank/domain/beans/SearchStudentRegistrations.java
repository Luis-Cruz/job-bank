package module.jobBank.domain.beans;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.StudentRegistration;
import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.Person;
import myorg.domain.util.Search;

public class SearchStudentRegistrations extends Search<StudentRegistration> {

    private boolean registrationConclued;
    private FenixDegree degree;
    private String username;

    public SearchStudentRegistrations() {
	super();
	setRegistrationConclued(false);
    }

    protected class SearchResult extends SearchResultSet<StudentRegistration> {

	public SearchResult(final Collection<? extends StudentRegistration> c) {
	    super(c);
	}

	@Override
	protected boolean matchesSearchCriteria(final StudentRegistration registration) {
	    Person person = registration.getStudent().getPerson();

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
    public Set<StudentRegistration> search() {
	return new SearchResult(readStudentRegistrationsToSatisfizedPredicate());
    }

    private Set<StudentRegistration> readStudentRegistrationsToSatisfizedPredicate() {
	final Set<StudentRegistration> results = new HashSet<StudentRegistration>();
	StudentRegistration.readAllStudentRegistrations(new IPredicate<StudentRegistration>() {
	    @Override
	    public boolean evaluate(StudentRegistration registration) {
		if (isSatisfiedConditions(registration)) {
		    results.add(registration);
		    return true;
		}
		return false;
	    }
	});
	return results;
    }

    private boolean isSatisfiedConditions(StudentRegistration registration) {
	if (hasSelectedDegree()) {
	    return isSatisfiedRegistration(registration, hasSelectedRegistrationConlued());
	} else {
	    return isSatisfiedRegistrationProcess(registration);
	}
    }

    private boolean isSatisfiedRegistrationProcess(StudentRegistration registration) {
	return !hasSelectedRegistrationConlued() || registration.getIsConcluded();
    }

    private boolean isSatisfiedRegistration(StudentRegistration registration, boolean concluded) {
	return registration.getFenixDegree().equals(getDegree()) && (!concluded || registration.getIsConcluded());
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