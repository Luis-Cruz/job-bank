package module.jobBank.presentationTier.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseStateType;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferState;
import module.jobBank.domain.beans.SearchEnterprise;
import module.jobBank.domain.beans.SearchOfferState;
import module.jobBank.domain.utils.Utils;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

public class JobBankSearchActionCommons extends ContextBaseAction {

    public void processJobOffersSearch(final HttpServletRequest request) {
	SearchOfferState offerSearch = getRenderedObject("offerSearch");
	if (offerSearch == null) {
	    offerSearch = new SearchOfferState();

	    String offerState = request.getParameter("offerState");
	    String enterprise = request.getParameter("enterprise");
	    String processNumber = request.getParameter("processNumber");

	    if (offerState != null && JobOfferState.getByLocalizedName(offerState) != null) {
		offerSearch.setJobOfferState(JobOfferState.getByLocalizedName(offerState));
		final String pageParameter = request.getParameter("pageNumber");
		final Integer page = StringUtils.isEmpty(pageParameter) ? Integer.valueOf(1) : Integer.valueOf(pageParameter);
		request.setAttribute("pageNumber", page);
	    }

	    if (enterprise != null) {
		offerSearch.setEnterprise(enterprise);
	    }

	    if (processNumber != null) {
		offerSearch.setProcessNumber(processNumber);
	    }
	}

	int resultsPerPage = 25;
	RenderUtils.invalidateViewState();
	Set<JobOfferProcess> processes = offerSearch.search();
	offerSearch.setProcessesCount(processes.size());

	request.setAttribute("offerSearch", offerSearch);
	request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));

	RenderUtils.invalidateViewState();
    }

    public void processEnterprisesSearch(HttpServletRequest request) {
	SearchEnterprise enterpriseSearch = getRenderedObject("enterpriseSearch");
	if (enterpriseSearch == null) {
	    enterpriseSearch = new SearchEnterprise();

	    String enterpriseState = request.getParameter("enterpriseState");
	    String enterpriseName = request.getParameter("enterpriseName");

	    if (enterpriseState != null && EnterpriseStateType.getByLocalizedName(enterpriseState) != null) {
		enterpriseSearch.setEnterpriseState(EnterpriseStateType.getByLocalizedName(enterpriseState));
		final String pageParameter = request.getParameter("pageNumber");
		final Integer page = StringUtils.isEmpty(pageParameter) ? Integer.valueOf(1) : Integer.valueOf(pageParameter);
		request.setAttribute("pageNumber", page);
	    }

	    if (enterpriseName != null) {
		enterpriseSearch.setEnterpriseName(enterpriseName);
	    }
	}

	int resultsPerPage = 25;
	RenderUtils.invalidateViewState();
	Set<Enterprise> processes = enterpriseSearch.search();
	enterpriseSearch.setEnterprisesCount(processes.size());

	request.setAttribute("enterpriseSearch", enterpriseSearch);
	request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));

	RenderUtils.invalidateViewState();
    }

}
