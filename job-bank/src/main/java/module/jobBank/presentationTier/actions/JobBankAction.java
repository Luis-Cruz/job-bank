package module.jobBank.presentationTier.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.beans.SearchUsers;
import module.organization.domain.Person;
import module.workflow.presentationTier.actions.ProcessManagement;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.utl.ist.fenix.tools.util.ByteArray;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/jobBank")
public class JobBankAction extends ContextBaseAction {

    public ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	request.setAttribute("user", UserView.getCurrentUser());
	return forward(request, "/jobBank/frontPage.jsp");
    }

    public ActionForward viewJobOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	if (jobOfferProcess.getCanManageJobProcess()) {
	    return viewJobOfferProcessToManage(mapping, form, request, response);
	}
	request.setAttribute("process", jobOfferProcess);
	return forward(request, "/jobBank/viewJobOffer.jsp");
    }

    public ActionForward viewJobOfferProcessToManage(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
	return ProcessManagement.forwardToProcess(jobOfferProcess);
    }

    public ActionForward manageRoles(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	SearchUsers searchUsers = getRenderedObject("searchUsers");
	if (searchUsers == null) {
	    searchUsers = new SearchUsers();
	}
	request.setAttribute("searchUsers", searchUsers);
	request.setAttribute("managementUsers", JobBankSystem.getInstance().getManagementUsers());
	return forward(request, "/jobBank/manageRoles.jsp");
    }

    public ActionForward addManagement(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	try {
	    SearchUsers searchUsers = getRenderedObject("searchUsers");
	    Person person = searchUsers.getPerson();
	    JobBankSystem.getInstance().addManagementUsers(person.getUser());
	} catch (DomainException e) {
	    addLocalizedMessage(request, e.getLocalizedMessage());
	}

	return manageRoles(mapping, form, request, response);
    }

    public ActionForward removeManagement(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {

	try {
	    User user = getDomainObject(request, "OID");
	    JobBankSystem.getInstance().removeManagementUsers(user);
	} catch (DomainException e) {
	    addLocalizedMessage(request, e.getLocalizedMessage());
	}

	return manageRoles(mapping, form, request, response);
    }

    public static ActionForward outputImage(final HttpServletResponse response, final ByteArray byteArray) throws Exception {
	OutputStream outputStream = null;
	try {
	    outputStream = response.getOutputStream();
	    if (byteArray != null) {
		outputStream.write(byteArray.getBytes());
	    }
	} finally {
	    if (outputStream != null) {
		outputStream.close();
	    }
	}
	return null;
    }
}
