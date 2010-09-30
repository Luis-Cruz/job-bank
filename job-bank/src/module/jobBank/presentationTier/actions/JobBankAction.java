package module.jobBank.presentationTier.actions;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.beans.SearchUsers;
import module.organization.domain.Person;
import module.workflow.presentationTier.actions.ProcessManagement;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.domain.util.ByteArray;
import myorg.presentationTier.actions.ContextBaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/jobBank")
public class JobBankAction extends ContextBaseAction {

    public ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	return forward(request, "/jobBank/frontPage.jsp");
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
