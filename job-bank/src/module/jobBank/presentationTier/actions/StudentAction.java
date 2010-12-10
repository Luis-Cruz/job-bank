package module.jobBank.presentationTier.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.CurriculumProcess;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.OfferCandidacy;
import module.jobBank.domain.Student;
import module.jobBank.domain.beans.SearchOffer;
import module.jobBank.domain.curriculumQualification.CurriculumQualification;
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

    public ActionForward personalArea(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Student student = Student.readStudent(UserView.getCurrentUser());
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

    public ActionForward candidateToJobOffer(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	OfferCandidacy.createOfferCandidacy(Student.readStudent(UserView.getCurrentUser()), jobOfferProcess.getJobOffer());
	return searchOffers(mapping, form, request, response);
    }

    public ActionForward removeCandidancy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	OfferCandidacy offerCandidacy = getDomainObject(request, "OID");
	offerCandidacy.removeCandidacy();
	CurriculumProcess process = offerCandidacy.getStudent().getCurriculum().getCurriculumProcess();
	return ProcessManagement.forwardToProcess(process);
    }
}
