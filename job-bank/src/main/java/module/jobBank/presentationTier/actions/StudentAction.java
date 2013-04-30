package module.jobBank.presentationTier.actions;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOfferNotificationFilter;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.OfferCandidacy;
import module.jobBank.domain.Student;
import module.jobBank.domain.beans.JobOfferNotificationFilterBean;
import module.jobBank.domain.beans.OfferCandidacyBean;
import module.jobBank.domain.curriculumQualification.CurriculumQualification;
import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;
import module.workflow.domain.WorkflowProcessComment;
import module.workflow.presentationTier.actions.CommentBean;
import module.workflow.presentationTier.actions.ProcessManagement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.utl.ist.fenix.tools.util.ByteArray;

@Mapping(path = "/student")
public class StudentAction extends ContextBaseAction {

    public ActionForward termsResponsibilityStudent(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        Student student = UserView.getCurrentUser().getPerson().getStudent();
        if (student != null && student.getCurriculum() != null) {
            student.getCurriculum().loadExternalData();
        }
        request.setAttribute("student", student);
        return forward(request, "/jobBank/student/termsResponsibilityStudent.jsp");
    }

    public ActionForward acceptResponsibilityTerms(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        try {
            Student student = UserView.getCurrentUser().getPerson().getStudent();
            student.acceptTermsResponsibility();
            CurriculumProcess process = student.getCurriculum().getCurriculumProcess();
            return ProcessManagement.forwardToProcess(process);
        } catch (DomainException e) {
            addLocalizedMessage(request, e.getLocalizedMessage());
            return forward(request, "/jobBank/frontPage.jsp");
        }

    }

    public ActionForward prepareToCreateStudent(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        return null;
        // try {
        // Student student =
        // Student.creatcandidateToJobOffereStudent(UserView.getCurrentUser());
        // CurriculumProcess process =
        // student.getCurriculum().getCurriculumProcess();
        // return ProcessManagement.forwardToProcess(process);
        // } catch (DomainException e) {
        // addLocalizedMessage(request, e.getLocalizedMessage());
        // return forward(request, "/jobBank/frontPage.jsp");
        // }
    }

    public ActionForward personalArea(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        Student student = UserView.getCurrentUser().getPerson().getStudent();
        CurriculumProcess process = student.getCurriculum().getCurriculumProcess();
        return ProcessManagement.forwardToProcess(process);
    }

    public ActionForward viewCurriculum(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        OfferCandidacy offerCandidacy = getDomainObject(request, "candidateOID");
        request.setAttribute("student", offerCandidacy.getStudent());
        return forward(request, "/jobBank/student/viewCurriculum.jsp");
    }

    public ActionForward searchOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
        commons.processStudentJobOfferSearch(request);
        return forward(request, "/jobBank/student/jobOffers.jsp");
    }

    public ActionForward processNotifications(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferNotificationFilterBean filter = getRenderedObject("filter");
        boolean filterPresent = (filter != null);
        Student student = UserView.getCurrentUser().getPerson().getStudent();

        if (!filterPresent) {
            filter = new JobOfferNotificationFilterBean();
        } else if (student != null) {
            JobOfferNotificationFilter.createNotification(filter, student);
        }

        if (student != null && student.getJobOfferNotificationFilterSet().size() > 0) {
            List<JobOfferNotificationFilter> filters =
                    new LinkedList<JobOfferNotificationFilter>(student.getJobOfferNotificationFilter());
            Collections.sort(filters, JobOfferNotificationFilter.COMPARATOR_BY_DEGREE);
            request.setAttribute("filters", filters);
        }

        request.setAttribute("filter", filter);
        return forward(request, "/jobBank/student/notifications.jsp");
    }

    public ActionForward viewCurriculumQualification(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        CurriculumQualification curriculumQualification = getDomainObject(request, "OID");
        request.setAttribute("curriculumQualification", curriculumQualification);
        return forward(request, "/jobBank/student/viewCurriuculumQualification.jsp");
    }

    public ActionForward deleteCurriculumQualification(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        CurriculumQualification curriculumQualification = getDomainObject(request, "OID");
        curriculumQualification.removeQualification();
        return personalArea(mapping, form, request, response);
    }

    public ActionForward attachFilesToOfferCandidacy(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        Student student = UserView.getCurrentUser().getPerson().getStudent();
        OfferCandidacyBean bean = getRenderedObject("offerCandidacyBean");

        if (!student.hasCurriculum() || !student.getCurriculum().hasAnyDocument()) {
            addLocalizedMessage(request, BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "message.no.curriculum.documents.available"));
            return viewJobOffer(mapping, form, request, response);
        }

        if (bean == null) {
            bean = new OfferCandidacyBean();
            bean.setStudent(student);
        } else {
            bean.addAttachFiles(bean.getProcessFile());
        }
        request.setAttribute("offerCandidacyBean", bean);
        request.setAttribute("jobOfferProcess", getDomainObject(request, "OID"));
        return forward(request, "/jobBank/student/attachFilesToOfferCandidacy.jsp");
    }

    // public ActionForward clearFilesToOfferCandidacy(final ActionMapping
    // mapping, final ActionForm form,
    // final HttpServletRequest request, final HttpServletResponse response) {
    // OfferCandidacyBean bean = new OfferCandidacyBean();
    // bean.setStudent(UserView.getCurrentUser().getPerson().getStudent());
    // request.setAttribute("offerCandidacyBean", bean);
    // request.setAttribute("jobOfferProcess", getDomainObject(request, "OID"));
    // return forward(request,
    // "/jobBank/student/attachFilesToOfferCandidacy.jsp");
    // }

    public ActionForward candidateToJobOffer(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        Student student = UserView.getCurrentUser().getPerson().getStudent();
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        OfferCandidacyBean bean = getRenderedObject("offerCandidacyBean");
        bean.setJobOffer(jobOfferProcess.getJobOffer());

        if (OfferCandidacy.getOfferCandidacy(student, bean.getJobOffer()) != null) {
            return viewJobOffer(mapping, form, request, response);
        }

        try {
            OfferCandidacy.createOfferCandidacy(bean);
        } catch (DomainException e) {
            addLocalizedMessage(request, e.getLocalizedMessage());
            return attachFilesToOfferCandidacy(mapping, form, request, response);
        }

        return viewJobOffer(mapping, form, request, response);
    }

    public ActionForward candidateToExternalJobOffer(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        Student student = UserView.getCurrentUser().getPerson().getStudent();

        if (OfferCandidacy.getOfferCandidacy(student, jobOfferProcess.getJobOffer()) != null) {
            return viewJobOffer(mapping, form, request, response);
        }

        OfferCandidacyBean bean = new OfferCandidacyBean();
        bean.setStudent(student);
        bean.setJobOffer(jobOfferProcess.getJobOffer());
        try {
            OfferCandidacy.createOfferCandidacy(bean);
        } catch (DomainException e) {
            addLocalizedMessage(request, e.getLocalizedMessage());
        }

        return viewJobOffer(mapping, form, request, response);
    }

    public ActionForward viewJobOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        request.setAttribute("process", jobOfferProcess);
        return forward(request, "/jobBank/student/viewJobOffer.jsp");
    }

    public ActionForward removeNotificationFilter(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferNotificationFilter filter = getDomainObject(request, "OID");
        filter.deleteFilter();
        return processNotifications(mapping, form, request, response);
    }

    public ActionForward removeJobOfferCandidancy(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        OfferCandidacy candidacyForThisUser = jobOfferProcess.getJobOffer().getCandidacyForThisUser(UserView.getCurrentUser());
        if (candidacyForThisUser != null) {
            candidacyForThisUser.removeCandidacy();
        }
        return viewJobOffer(mapping, form, request, response);
    }

    public ActionForward removeCandidancy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        OfferCandidacy offerCandidacy = getDomainObject(request, "OID");
        offerCandidacy.removeCandidacy();
        CurriculumProcess process = offerCandidacy.getStudent().getCurriculum().getCurriculumProcess();
        return ProcessManagement.forwardToProcess(process);
    }

    public ActionForward viewJobOfferComments(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {

        final WorkflowProcess process = getDomainObject(request, "processId");
        request.setAttribute("process", process);

        Set<WorkflowProcessComment> comments = new TreeSet<WorkflowProcessComment>(WorkflowProcessComment.COMPARATOR);
        comments.addAll(process.getComments());

        process.markCommentsAsReadForUser(UserView.getCurrentUser());
        request.setAttribute("comments", comments);
        request.setAttribute("bean", new CommentBean(process));

        return forward(request, "/jobBank/student/viewJobOfferComments.jsp");
    }

    public ActionForward viewEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        Enterprise enterprise = getDomainObject(request, "OID");
        request.setAttribute("enterprise", enterprise);
        enterprise.addContactInformation(request);
        return forward(request, "/jobBank/student/viewEnterprise.jsp");
    }

    public ActionForward viewEnterpriseLogo(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final Enterprise enterprise = getDomainObject(request, "enterpriseId");
        final ByteArray logo = enterprise.getLogo();
        return JobBankAction.outputImage(response, logo);
    }

    public ActionForward downloadFile(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        final ProcessFile file = getDomainObject(request, "fileId");
        return download(response, file.getFilename(), file.getStream(), file.getContentType());
    }
}
