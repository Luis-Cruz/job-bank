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
import module.jobBank.domain.utils.IPredicate;
import module.workflow.presentationTier.actions.ProcessManagement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;
import pt.ist.bennu.core.util.VariantBean;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.utl.ist.fenix.tools.util.ByteArray;

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

        request.setAttribute("jobOfferBean", bean);
        return forward(request, "/jobBank/enterprise/createJobOffer.jsp");
    }

    public ActionForward changeCandidacyTypeOnOfferCreation(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferBean bean = getRenderedObject("jobOfferBean");

        if (bean == null) {
            bean = new JobOfferBean();
        } else if (bean.getPreviousCandidacyType() == null || bean.getPreviousCandidacyType() != bean.getCandidacyType()) {
            bean.setPreviousCandidacyType(bean.getCandidacyType());
            RenderUtils.invalidateViewState();
        }

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
        enterprise.addContactInformation(request);
        return ProcessManagement.forwardToProcess(enterprise.getEnterpriseProcess());
    }

    public ActionForward acceptTerms(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        User user = UserView.getCurrentUser();
        if (user == null) {
            return prepareToCreateEmailValidation(mapping, form, request, response);
        }
        if (user.getEnterprise() != null) {
            user.getEnterprise().acceptedTermsOfResponsibilityForCurrentYear();
        }
        request.setAttribute("user", user);
        return forward(request, "/jobBank/frontPage.jsp");
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
        addMessage(request, "message.enterprise.emailValidation.valid");
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

        try {
            Enterprise enterprise = bean.create();
            request.setAttribute("enterprise", enterprise);
        } catch (DomainException e) {
            addLocalizedMessage(request, e.getLocalizedMessage());
            return prepareToCreateEnterprise(mapping, form, request, response);
        }
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
        enterprise.addContactInformation(request);
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
        JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
        commons.processJobOffersSearch(request);
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

        JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
        commons.processStudentsSearch(request);
        return forward(request, "/jobBank/enterprise/searchStudents.jsp");
    }

    public ActionForward viewStudentCurriculum(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        Enterprise enterprise = Enterprise.readCurrentEnterprise();
        Student student = getDomainObject(request, "studentOID");
        request.setAttribute("student", student);
        request.setAttribute("enterprise", enterprise);
        request.setAttribute("offerCandidacies", student.getSortedOfferCandidaciesOfEnterprise(enterprise));
        return forward(request, "/jobBank/enterprise/viewStudentCurriculum.jsp");
    }

    public ActionForward viewStudentCurriculumForOfferCandidacy(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess process = getDomainObject(request, "OID");
        Enterprise enterprise = process.getJobOffer().getEnterprise();
        OfferCandidacy offerCandidacy = getDomainObject(request, "candidateOID");
        request.setAttribute("offercandidacy", offerCandidacy);
        request.setAttribute("enterprise", enterprise);
        request.setAttribute("jobOffer", process.getJobOffer());
        request.setAttribute("offerCandidacies", offerCandidacy.getStudent().getOfferCandidaciesOfEnterprise(enterprise));
        return forward(request, "/jobBank/enterprise/viewStudentCurriculumForOfferCandidacy.jsp");
    }

    public ActionForward processEditEnterprise(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        EnterpriseInformation information = getRenderedObject("activityBean");
        return ProcessManagement.forwardToActivity(information.getProcess(), information.getActivity());
    }

    public ActionForward prepareToPasswordRecover(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        VariantBean email = new VariantBean();
        request.setAttribute("passwordRecover", email);
        return forward(request, "/jobBank/enterprise/passwordRecover.jsp");
    }

    public ActionForward passwordRecover(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        VariantBean email = getRenderedObject("passwordRecover");
        try {
            Enterprise.passwordRecover(email.getString());
            addMessage(request, "message.enterprise.recoverPassword.sucess", new String[] { email.getString() });
        } catch (DomainException e) {
            addMessage(request, "error", e.getKey(), new String[] {});
            request.setAttribute("passwordRecover", email);
        }
        return forward(request, "/jobBank/enterprise/passwordRecover.jsp");
    }

    public ActionForward emailValidation(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        String checksum = getAttribute(request, "checkEmail");
        EmailValidation emailValidation = getDomainObject(request, "OID");
        if (emailValidation != null && emailValidation.isValidChecksum(checksum)) {
            if (emailValidation.isEmailAlreadyValidated(checksum)) {
                addMessage(request, "error", "message.error.enterprise.email.already.registered", new String[] {});
                return forward(request, "/jobBank/enterprise/createEmailValidation.jsp");
            }
            return prepareToCreateEnterprise(mapping, form, request, response);
        }
        addMessage(request, "error", "message.enterprise.emailValidation.invalid", new String[] {});
        return forward(request, "/jobBank/enterprise/createEmailValidation.jsp");
    }

}
