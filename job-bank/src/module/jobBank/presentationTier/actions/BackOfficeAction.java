package module.jobBank.presentationTier.actions;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.beans.SearchOffer;
import module.jobBank.domain.utils.IPredicate;
import module.organization.domain.OrganizationalModel;
import module.organization.presentationTier.actions.OrganizationModelAction.OrganizationalModelChart;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.domain.MyOrg;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/backOffice")
public class BackOfficeAction extends ContextBaseAction {

    public ActionForward jobOffers(final ActionMapping mapping, final ActionForm foStudentrm, final HttpServletRequest request,
	    final HttpServletResponse response) {

	SearchOffer offerSearch = getRenderedObject("offerSearch");
	if (offerSearch == null) {
	    offerSearch = new SearchOffer();
	}
	request.setAttribute("offerSearch", offerSearch);
	request.setAttribute("processes", offerSearch.doSearch());

	RenderUtils.invalidateViewState();
	return forward(request, "/jobBank/backOffice/jobOffers.jsp");
    }

    public ActionForward viewEnterprises(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Set<Enterprise> pendingEnterprises = Enterprise.readAllEnterprises(new IPredicate<Enterprise>() {
	    @Override
	    public boolean evaluate(Enterprise object) {
		return object.isPendingAccountabilityType() && !object.isCanceled();
	    }
	});
	request.setAttribute("pendingEnterprises", pendingEnterprises);
	return forward(request, "/jobBank/backOffice/viewEnterprises.jsp");
    }

    public ActionForward Enterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	EnterpriseProcess process = getDomainObject(request, "OID");
	return ProcessManagement.forwardToProcess(process);
    }

    public ActionForward viewActiveEnterprises(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	Set<Enterprise> activeEnterprises = Enterprise.readAllActiveEnterprises();
	request.setAttribute("enterprises", activeEnterprises);
	return forward(request, "/jobBank/backOffice/viewActiveEnterprises.jsp");
    }

    public ActionForward viewRequestsToChangeEnterprises(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	Set<Enterprise> enterprises = Enterprise.readAllRequestToChangeEnterprises();
	request.setAttribute("enterprises", enterprises);
	return forward(request, "/jobBank/backOffice/viewRequestsToChangeEnterprises.jsp");
    }

    public ActionForward approveEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = getDomainObject(request, "enterpriseOID");
	enterprise.changeAgreement(enterprise.getAccountabilityTypeForApproval());
	return viewEnterprises(mapping, form, request, response);
    }

    public ActionForward rejectEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = getDomainObject(request, "enterpriseOID");
	enterprise.reject();
	return viewEnterprises(mapping, form, request, response);
    }

    /* Configuration */

    public ActionForward configuration(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	request.setAttribute("jobBankSystem", JobBankSystem.getInstance());
	return forward(request, "/jobBank/backOffice/configuration.jsp");
    }

    public ActionForward prepareSelectOrganizationalModel(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	viewModels(request);
	return forward(request, "/jobBank/backOffice/selectOrganizationalModel.jsp");
    }

    public ActionForward selectOrganizationalModel(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
	jobBankSystem.setOrganizationalModel(organizationalModel);
	return configuration(mapping, form, request, response);
    }

    public static void viewModels(final HttpServletRequest request) {
	final Set<OrganizationalModel> organizationalModels = new TreeSet<OrganizationalModel>(
		OrganizationalModel.COMPARATORY_BY_NAME);
	organizationalModels.addAll(MyOrg.getInstance().getOrganizationalModelsSet());
	request.setAttribute("organizationalModels", organizationalModels);
	final OrganizationalModelChart organizationalModelChart = new OrganizationalModelChart(organizationalModels);
	request.setAttribute("organizationalModelChart", organizationalModelChart);
    }

    /* End Configuration */

}
