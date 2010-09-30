package module.jobBank.domain.beans;

/*
 * @(#)SearchUsers.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Bennu Web Application Infrastructure.
 *
 *   The Bennu Web Application Infrastructure is free software: you can 
 *   redistribute it and/or modify it under the terms of the GNU Lesser General 
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.*
 *
 *   Bennu is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with Bennu. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

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
