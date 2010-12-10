package module.jobBank.presentationTier.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.EmailValidation;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.OfferCandidacy;
import module.jobBank.domain.Student;
import module.jobBank.domain.activity.EnterpriseInformation;
import module.jobBank.domain.beans.EnterpriseBean;
import module.jobBank.domain.beans.JobOfferBean;
import module.jobBank.domain.beans.SearchStudents;
import module.jobBank.domain.utils.IPredicate;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/enterprise")
public class EnterpriseAction extends ContextBaseAction {
    public ActionForward termsResponsibilityEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	return forward(request, "/jobBank/enterprise/termsResponsibilityEnterprise.jsp");
    }

    public ActionForward prepareToCreateOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferBean bean = getRenderedObject("jobOfferBean");
	if (bean == null) {
	    bean = new JobOfferBean();
	}
	RenderUtils.invalidateViewState();
	request.setAttribute("jobOfferBean", bean);
	return forward(request, "/jobBank/enterprise/createJobOffer.jsp");
    }

    public ActionForward createOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferBean bean = getRenderedObject("jobOfferBean");
	try {
	    JobOffer newJobOffer = bean.create();
	    return ProcessManagement.forwardToProcess(newJobOffer.getJobOfferProcess());
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

    public ActionForward prepareToCreateEmailValidation(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	EnterpriseBean enterpriseBean = new EnterpriseBean();
	request.setAttribute("enterpriseBean", enterpriseBean);
	return forward(request, "/jobBank/enterprise/createEmailValidation.jsp");
    }

    public ActionForward createEmailValidation(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	EnterpriseBean enterpriseBean = getRenderedObject("enterpriseBean");
	EmailValidation emailToValidate = enterpriseBean.createEmailValidation();
	enterpriseBean.setEmailValidation(emailToValidate);
	request.setAttribute("enterpriseBean", enterpriseBean);
	return forward(request, "/jobBank/enterprise/createEmailValidation.jsp");
    }

    public ActionForward prepareToCreateEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	EnterpriseBean enterpriseBeanLogo = getRenderedObject("enterpriseBeanLogo");
	EnterpriseBean enterpriseBean = getRenderedObject("enterpriseBean");
	if (enterpriseBeanLogo == null) {
	    enterpriseBeanLogo = new EnterpriseBean();
	}
	if (enterpriseBean == null) {
	    EmailValidation emailValidation = getDomainObject(request, "OID");
	    enterpriseBean = new EnterpriseBean(emailValidation);
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
		return object.isProcessOwner(UserView.getCurrentUser()) && !object.getJobOffer().isCanceled();
	    }
	});
	request.setAttribute("processes", processes);
	return forward(request, "/jobBank/enterprise/jobOffers.jsp");
    }

    public ActionForward viewAllJobOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Set<JobOfferProcess> processes = JobOfferProcess.readJobOfferProcess(new IPredicate<JobOfferProcess>() {
	    @Override
	    public boolean evaluate(JobOfferProcess object) {
		return object.getJobOffer().isActive();
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

    public ActionForward viewJobOfferProcess(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/jobBank/enterprise/viewJobOfferProcess.jsp");
    }

    public ActionForward selectCandidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	OfferCandidacy offerCandidacy = getDomainObject(request, "candidateOID");
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	jobOfferProcess.getJobOffer().selectCandidacy(offerCandidacy);
	return ProcessManagement.forwardToProcess((JobOfferProcess) getDomainObject(request, "OID"));
    }

    public ActionForward removeCandidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	OfferCandidacy offerCandidacy = getDomainObject(request, "candidateOID");
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	jobOfferProcess.getJobOffer().removeCandidacy(offerCandidacy);
	return ProcessManagement.forwardToProcess((JobOfferProcess) getDomainObject(request, "OID"));
    }

    public ActionForward searchStudents(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = Enterprise.readCurrentEnterprise();
	if (!enterprise.isJobProviderWithPrivilegesAgreement()) {
	    return forward(request, "/jobBank/notHavePermissions.jsp");
	}

	SearchStudents search = getRenderedObject("searchStudents");
	if (search == null) {
	    search = new SearchStudents();
	}
	RenderUtils.invalidateViewState();
	request.setAttribute("searchStudents", search);
	request.setAttribute("results", search.search());
	return forward(request, "/jobBank/enterprise/searchStudents.jsp");
    }

    public ActionForward viewStudentCurriculum(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	Student student = getDomainObject(request, "studentOID");
	Enterprise enterprise = Enterprise.readCurrentEnterprise();
	request.setAttribute("student", student);
	request.setAttribute("enterprise", enterprise);
	request.setAttribute("offerCandidacies", student.getOfferCandidaciesOfEnterprise(enterprise));
	return forward(request, "/jobBank/enterprise/viewStudentCurriculum.jsp");
    }

    public ActionForward processEditEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	EnterpriseInformation information = getRenderedObject("activityBean");
	return ProcessManagement.forwardToActivity(information.getProcess(), information.getActivity());
    }

    public ActionForward emailValidation(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	String checksum = getAttribute(request, "checkEmail");
	EmailValidation emailValidation = getDomainObject(request, "OID");
	if (emailValidation == null) {
	    request.setAttribute("invalidRegistration", true);
	    return forward(request, "/jobBank/enterprise/createEmailValidation.jsp");
	}
	if (emailValidation.isValidChecksum(checksum)) {
	    return prepareToCreateEnterprise(mapping, form, request, response);
	}
	request.setAttribute("invalidRegistration", true);
	return forward(request, "/jobBank/enterprise/createEmailValidation.jsp");
    }

}
