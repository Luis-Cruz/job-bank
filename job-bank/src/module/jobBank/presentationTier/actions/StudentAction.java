package module.jobBank.presentationTier.actions;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.OfferCandidacy;
import module.jobBank.domain.Student;
import module.jobBank.domain.beans.OfferCandidacyBean;
import module.jobBank.domain.beans.SearchOffer;
import module.jobBank.domain.curriculumQualification.CurriculumQualification;
import module.workflow.domain.WorkflowProcess;
import module.workflow.domain.WorkflowProcessComment;
import module.workflow.presentationTier.actions.CommentBean;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.exceptions.DomainException;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/student")
public class StudentAction extends ContextBaseAction {

    public ActionForward termsResponsibilityStudent(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	request.setAttribute("student", UserView.getCurrentUser().getPerson().getStudent());
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
	// Student student = Student.createStudent(UserView.getCurrentUser());
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
	SearchOffer search = getRenderedObject("search");
	if (search == null) {
	    search = new SearchOffer();
	}
	RenderUtils.invalidateViewState();
	request.setAttribute("processes", search.search());
	request.setAttribute("search", search);
	return forward(request, "/jobBank/student/jobOffers.jsp");
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
	OfferCandidacyBean bean = getRenderedObject("offerCandidacyBean");
	if (bean == null) {
	    bean = new OfferCandidacyBean();
	    bean.setStudent(UserView.getCurrentUser().getPerson().getStudent());
	} else {
	    bean.addAttachFiles(bean.getProcessFile());
	}
	request.setAttribute("offerCandidacyBean", bean);
	request.setAttribute("jobOfferProcess", getDomainObject(request, "OID"));
	return forward(request, "/jobBank/student/attachFilesToOfferCandidacy.jsp");
    }

    public ActionForward clearFilesToOfferCandidacy(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	OfferCandidacyBean bean = new OfferCandidacyBean();
	bean.setStudent(UserView.getCurrentUser().getPerson().getStudent());
	request.setAttribute("offerCandidacyBean", bean);
	request.setAttribute("jobOfferProcess", getDomainObject(request, "OID"));
	return forward(request, "/jobBank/student/attachFilesToOfferCandidacy.jsp");
    }

    public ActionForward candidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	OfferCandidacyBean bean = getRenderedObject("offerCandidacyBean");
	bean.setJobOffer(jobOfferProcess.getJobOffer());
	try {
	    OfferCandidacy.createOfferCandidacy(bean);
	} catch (DomainException e) {
	    addLocalizedMessage(request, e.getLocalizedMessage());
	    return prepareToCreateStudent(mapping, form, request, response);
	}

	return searchOffers(mapping, form, request, response);
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
	return forward(request, "/jobBank/student/viewEnterprise.jsp");
    }
}
