package module.jobBank.domain.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.FenixDegreeType;
import module.jobBank.domain.StudentRegistration;
import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.Person;
import pt.ist.bennu.core.domain.util.Search;
import pt.utl.ist.fenix.tools.util.StringNormalizer;

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
	    if (username == null) {
		return false;
	    }

	    String[] result = StringNormalizer.normalize(username).split(" ");
	    String personName = StringNormalizer.normalize(person.getName());

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
	normalizeStrings();
	return new SearchResult(readStudentRegistrationsToSatisfizedPredicate());
    }

    private void normalizeStrings() {
	if (getUsername() != null) {
	    setUsername(StringNormalizer.normalize(getUsername()));
	}
    }

    public List<StudentRegistration> sortedSearchByStudentName() {
	ArrayList<StudentRegistration> results = new ArrayList<StudentRegistration>(search());
	Collections.sort(results, StudentRegistration.COMPARATOR_BY_STUDENT_NAME);
	return results;
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
	    return isSatisfiedRegistrationProcess(registration) && checkBolonhaDegreeRestriction(registration.getFenixDegree());
	}
    }

    private boolean isSatisfiedRegistrationProcess(StudentRegistration registration) {
	return !hasSelectedRegistrationConlued() || registration.getIsConcluded();
    }

    private boolean isSatisfiedRegistration(StudentRegistration registration, boolean concluded) {
	return registration.getFenixDegree().equals(getDegree()) && (!concluded || registration.getIsConcluded());
    }

    private boolean checkBolonhaDegreeRestriction(FenixDegree degree) {
	return !degree.getDegreeType().equals(FenixDegreeType.BOLONHA_DEGREE);
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
