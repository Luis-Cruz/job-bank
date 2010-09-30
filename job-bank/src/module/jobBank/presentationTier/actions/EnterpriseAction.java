package module.jobBank.presentationTier.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.CandidateOffer;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.beans.EnterpriseBean;
import module.jobBank.domain.beans.JobOfferBean;
import module.jobBank.domain.utils.IPredicate;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/enterprise")
public class EnterpriseAction extends ContextBaseAction {
    public ActionForward termsResponsibilityEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	return forward(request, "/jobBank/enterprise/termsResponsibilityEnterprise.jsp");
    }

    public ActionForward prepareToCreateOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferBean bean = new JobOfferBean();
	request.setAttribute("jobOfferBean", bean);
	return forward(request, "/jobBank/enterprise/createJobOffer.jsp");
    }

    public ActionForward createOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferBean bean = getRenderedObject("jobOfferBean");
	try {
	    bean.create();
	    return jobOffers(mapping, form, request, response);
	} catch (DomainException e) {
	    addLocalizedMessage(request, e.getLocalizedMessage());
	    return prepareToCreateOffer(mapping, form, request, response);
	}

    }

    public ActionForward enterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = Enterprise.readEnterprise(UserView.getCurrentUser());
	return ProcessManagement.forwardToProcess(enterprise.getEnterpriseProcess());
    }

    public ActionForward prepareToCreateEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	EnterpriseBean enterpriseBeanLogo = getRenderedObject("enterpriseBeanLogo");
	EnterpriseBean enterpriseBean = getRenderedObject("enterpriseBean");
	if (enterpriseBeanLogo == null) {
	    enterpriseBeanLogo = new EnterpriseBean();
	}
	if (enterpriseBean == null) {
	    enterpriseBean = new EnterpriseBean();
	}
	request.setAttribute("enterpriseBean", enterpriseBean);
	request.setAttribute("enterpriseBeanLogo", enterpriseBeanLogo);
	return forward(request, "/jobBank/enterprise/createEnterprise.jsp");
    }

    public ActionForward prepareToEditEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	Enterprise enterprise = Enterprise.readEnterprise(UserView.getCurrentUser());
	request.setAttribute("enterpriseBean", new EnterpriseBean(enterprise));
	request.setAttribute("enterprise", enterprise);
	return forward(request, "/jobBank/enterprise/editEnterprise.jsp");
    }

    public ActionForward createEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	EnterpriseBean bean = getRenderedObject("enterpriseBean");
	if (bean.hasInputStreamLogo()) {
	    byte[] logo = consumeInputStream(bean.getLogoInputStream());
	    bean.setLogo(new ByteArray(logo));
	}
	Enterprise enterprise = bean.create();
	request.setAttribute("enterprise", enterprise);
	return forward(request, "/jobBank/enterprise/viewCredentialsEnterprise.jsp");
    }

    public ActionForward editEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	EnterpriseBean bean = getRenderedObject("enterpriseBean");
	Enterprise enterprise = getDomainObject(request, "enterpriseOID");
	enterprise.setForm(bean);
	request.setAttribute("enterprise", enterprise);
	return forward(request, "/jobBank/enterprise/editEnterprise.jsp");
    }

    public ActionForward viewEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = getDomainObject(request, "enterpriseOID");
	request.setAttribute("enterprise", enterprise);
	return forward(request, "/jobBank/enterprise/viewEnterprise.jsp");
    }

    public ActionForward jobOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Set<JobOfferProcess> processes = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {
	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		return object.isProcessOwner(UserView.getCurrentUser()) && !object.getJobOffer().isActive();
	    }
	});
	request.setAttribute("processes", processes);
	return forward(request, "/jobBank/enterprise/jobOffers.jsp");
    }

    public ActionForward viewlogo(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws Exception {
	final Enterprise enterprise = getDomainObject(request, "enterpriseId");
	final ByteArray logo = enterprise.getLogo();
	return JobBankAction.outputImage(response, logo);
    }

    public ActionForward viewCandidatesToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	request.setAttribute("candidates", jobOfferProcess.getJobOffer().getActiveCandidateOffers());
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/jobBank/enterprise/viewCandidatesToJobOffer.jsp");
    }

    public ActionForward viewJobOfferProcess(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/jobBank/enterprise/viewJobOfferProcess.jsp");
    }

    public ActionForward selectCandidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	CandidateOffer candidateOffer = getDomainObject(request, "candidateOID");
	candidateOffer.selectCandidate();
	return viewCandidatesToJobOffer(mapping, form, request, response);
    }

    public ActionForward removeCandidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	CandidateOffer candidateOffer = getDomainObject(request, "candidateOID");
	candidateOffer.removeCandidate();
	return viewCandidatesToJobOffer(mapping, form, request, response);
    }

}
