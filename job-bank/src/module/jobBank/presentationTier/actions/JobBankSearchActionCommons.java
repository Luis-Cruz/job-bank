package module.jobBank.presentationTier.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseStateType;
import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferState;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.StudentRegistration;
import module.jobBank.domain.beans.SearchEnterprise;
import module.jobBank.domain.beans.SearchOffer;
import module.jobBank.domain.beans.SearchOfferState;
import module.jobBank.domain.beans.SearchStudentRegistrations;
import module.jobBank.domain.utils.Utils;
import myorg.presentationTier.actions.ContextBaseAction;

public class JobBankSearchActionCommons extends ContextBaseAction {


    public void processJobOffersSearch(final HttpServletRequest request) {
	SearchOfferState offerSearch = getRenderedObject("offerSearch");
	if (offerSearch == null) {
	    offerSearch = new SearchOfferState();

	    String offerState = request.getParameter("offerState");
	    String offerType = request.getParameter("offerType");
	    String enterprise = request.getParameter("enterprise");
	    String processNumber = request.getParameter("processNumber");

	    if (offerState != null && JobOfferState.getByName(offerState) != null) {
		offerSearch.setJobOfferState(JobOfferState.getByName(offerState));
	    }

	    if (offerType != null && JobOfferType.getByName(offerType) != null) {
		offerSearch.setJobOfferType(JobOfferType.getByName(offerType));
	    }

	    if (enterprise != null) {
		offerSearch.setEnterprise(enterprise);
	    }

	    if (processNumber != null) {
		offerSearch.setProcessNumber(processNumber);
	    }
	}

	int resultsPerPage = 25;
	Set<JobOfferProcess> processes = offerSearch.search();
	offerSearch.setProcessesCount(processes.size());

	request.setAttribute("offerSearch", offerSearch);
	request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));
    }


    public void processStudentJobOfferSearch(final HttpServletRequest request) {
	SearchOffer search = getRenderedObject("search");
	boolean firstSearch = (search == null);

	if (search == null) {
	    search = new SearchOffer();

	    String query = request.getParameter("query");
	    String degreeIdInternal = request.getParameter("degrees");
	    String jobOfferType = request.getParameter("jobOfferType");

	    if (query != null) {
		search.setQuery(query);
	    }

	    if (degreeIdInternal != null) {
		if (degreeIdInternal != null) {
		    try {
			int id = Integer.valueOf(degreeIdInternal);
			FenixDegree degree = FenixDegree.getFenixDegreeByIdInternal(id);
			if (degree != null) {
			    search.setDegrees(degree);
			}
		    } catch (NumberFormatException e) {
			// Do Nothing
		    }
		}
	    }

	    if (jobOfferType != null && JobOfferType.getByName(jobOfferType) != null) {
		search.setJobOfferType(JobOfferType.getByName(jobOfferType));
	    }

	    if (request.getParameter("pageNumber") != null) {
		firstSearch = false;
	    }
	}

	if (!firstSearch) {
	    int resultsPerPage = 25;
	    Set<JobOfferProcess> processes = search.search();
	    request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));
	}

	request.setAttribute("search", search);
    }


    public void processEnterprisesSearch(HttpServletRequest request) {
	SearchEnterprise enterpriseSearch = getRenderedObject("enterpriseSearch");
	if (enterpriseSearch == null) {
	    enterpriseSearch = new SearchEnterprise();

	    String enterpriseState = request.getParameter("enterpriseState");
	    String enterpriseName = request.getParameter("enterpriseName");

	    if (enterpriseState != null && EnterpriseStateType.getByName(enterpriseState) != null) {
		enterpriseSearch.setEnterpriseState(EnterpriseStateType.getByName(enterpriseState));
	    }

	    if (enterpriseName != null) {
		enterpriseSearch.setEnterpriseName(enterpriseName);
	    }
	}

	int resultsPerPage = 25;
	Set<Enterprise> processes = enterpriseSearch.search();
	enterpriseSearch.setEnterprisesCount(processes.size());

	request.setAttribute("enterpriseSearch", enterpriseSearch);
	request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));
    }


    public void processStudentsSearch(final HttpServletRequest request) {
	SearchStudentRegistrations search = getRenderedObject("searchStudents");
	boolean firstSearch = (search == null);

	if (search == null) {
	    search = new SearchStudentRegistrations();

	    String username = request.getParameter("username");
	    if (username != null) {
		search.setUsername(username);
	    }

	    String degreeIdInternal = request.getParameter("degree");
	    if (degreeIdInternal != null) {
		try {
		    int id = Integer.valueOf(degreeIdInternal);
		    FenixDegree degree = FenixDegree.getFenixDegreeByIdInternal(id);
		    if (degree != null) {
			search.setDegree(degree);
		    }
		} catch (NumberFormatException e) {
		    // Do Nothing
		}
	    }

	    Boolean registrationConclued = Boolean.valueOf(request.getParameter("registrationConclued"));
	    if (registrationConclued != null) {
		search.setRegistrationConclued(registrationConclued);
	    }

	    if (request.getParameter("pageNumber") != null) {
		firstSearch = false;
	    }
	}

	if (!firstSearch) {
	    int resultsPerPage = 25;
	    Set<StudentRegistration> registrations = search.search();
	    request.setAttribute("results", Utils.doPagination(request, registrations, resultsPerPage));
	    request.setAttribute("resultsCount", registrations.size());
	}

	request.setAttribute("searchStudents", search);
    }

}
