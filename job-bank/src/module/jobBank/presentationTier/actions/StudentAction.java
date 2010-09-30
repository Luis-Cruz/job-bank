package module.jobBank.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.CandidateOffer;
import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.Student;
import module.jobBank.domain.beans.Search;
import module.jobBank.domain.curriculumQualification.CurriculumQualification;
import module.workflow.activities.ActivityInformation;
import module.workflow.domain.WorkflowProcess;
import module.workflow.presentationTier.WorkflowLayoutContext;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.exceptions.DomainException;
import myorg.presentationTier.Context;
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
	request.setAttribute("student", Student.readStudent(UserView.getCurrentUser()));
	return forward(request, "/jobBank/student/termsResponsibilityStudent.jsp");
    }

    public ActionForward prepareToCreateStudent(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	try {
	    Student student = Student.createStudent(UserView.getCurrentUser());
	    CurriculumProcess process = student.getCurriculum().getCurriculumProcess();
	    return ProcessManagement.forwardToProcess(process);
	} catch (DomainException e) {
	    addLocalizedMessage(request, e.getLocalizedMessage());
	    return prepareToCreateStudent(mapping, form, request, response);
	}

    }

    public ActionForward activityDefaultPostback(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	final CurriculumProcess curriculumProcess = getDomainObject(request, "processId");
	final Context originalContext = getContext(request);
	final WorkflowLayoutContext workflowLayoutContext = curriculumProcess.getLayout();
	workflowLayoutContext.setElements(originalContext.getPath());
	setContext(request, workflowLayoutContext);
	ActivityInformation<WorkflowProcess> information = getRenderedObject("activityBean");
	RenderUtils.invalidateViewState();
	request.setAttribute("information", information);
	return ProcessManagement.performActivityPostback(information, request);
    }

    public ActionForward personalArea(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Student student = Student.readStudent(UserView.getCurrentUser());
	CurriculumProcess process = student.getCurriculum().getCurriculumProcess();
	return ProcessManagement.forwardToProcess(process);
    }

    public ActionForward viewCurriculum(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	CandidateOffer candidateOffer = getDomainObject(request, "candidateOID");
	request.setAttribute("student", candidateOffer.getStudent());
	return forward(request, "/jobBank/student/viewCurriculum.jsp");
    }

    public ActionForward searchOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Search search = getRenderedObject("search");
	if (search == null) {
	    search = new Search();
	}
	RenderUtils.invalidateViewState();
	request.setAttribute("processes", search.doSearch());
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

    public ActionForward viewJobOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/jobBank/student/viewJobOffer.jsp");
    }

    public ActionForward candidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	CandidateOffer.createCandidateOffer(Student.readStudent(UserView.getCurrentUser()), jobOfferProcess.getJobOffer());
	return searchOffers(mapping, form, request, response);
    }

    public ActionForward removeCandidancy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	CandidateOffer candidateOffer = getDomainObject(request, "OID");
	candidateOffer.removeCandidancy();
	CurriculumProcess process = candidateOffer.getStudent().getCurriculum().getCurriculumProcess();
	return ProcessManagement.forwardToProcess(process);
    }
}
