package module.jobBank.presentationTier.actions;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.OfferCandidacy;
import module.jobBank.domain.Student;
import module.jobBank.domain.activity.EnterpriseInformation;
import module.organization.domain.OrganizationalModel;
import module.organization.presentationTier.actions.OrganizationModelAction.OrganizationalModelChart;
import module.workflow.presentationTier.actions.ProcessManagement;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;
import pt.ist.bennu.core.util.VariantBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.utl.ist.fenix.tools.util.excel.Spreadsheet;

@Mapping(path = "/backOffice")
public class BackOfficeAction extends ContextBaseAction {

    public ActionForward jobOffers(final ActionMapping mapping, final ActionForm foStudentrm, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
	commons.processJobOffersSearch(request);
	return forward(request, "/jobBank/backOffice/jobOffers.jsp");
    }

    public ActionForward enterprises(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
	commons.processEnterprisesSearch(request);
	return forward(request, "/jobBank/backOffice/enterprises.jsp");
    }

    public ActionForward Enterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	RenderUtils.invalidateViewState();
	EnterpriseProcess process = getDomainObject(request, "OID");
	process.getEnterprise().addContactInformation(request);
	return ProcessManagement.forwardToProcess(process);
    }

    public ActionForward approveEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = getDomainObject(request, "enterpriseOID");
	enterprise.changeAgreement(enterprise.getAgreementForApproval());
	return enterprises(mapping, form, request, response);
    }

    public ActionForward rejectEnterprise(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	Enterprise enterprise = getDomainObject(request, "enterpriseOID");
	enterprise.reject();
	return enterprises(mapping, form, request, response);
    }

    public ActionForward processEditEnterprise(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	EnterpriseInformation information = getRenderedObject("activityBean");
	return ProcessManagement.forwardToActivity(information.getProcess(), information.getActivity());
    }

    public ActionForward searchStudents(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
	commons.processStudentsSearch(request);
	return forward(request, "/jobBank/backOffice/searchStudents.jsp");
    }

    public ActionForward viewStudentCurriculum(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	Student student = getDomainObject(request, "studentOID");
	request.setAttribute("student", student);
	request.setAttribute("offerCandidacies", student.getSortedActiveOfferCandidacies());
	return forward(request, "/jobBank/backOffice/viewStudentCurriculum.jsp");
    }

    public ActionForward viewStudentCurriculumForOfferCandidacy(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobOfferProcess process = getDomainObject(request, "OID");
	Enterprise enterprise = process.getJobOffer().getEnterprise();
	OfferCandidacy offerCandidacy = getDomainObject(request, "candidateOID");
	request.setAttribute("offercandidacy", offerCandidacy);
	request.setAttribute("enterprise", enterprise);
	request.setAttribute("offerCandidacies", offerCandidacy.getStudent().getOfferCandidaciesOfEnterprise(enterprise));
	return forward(request, "/jobBank/backOffice/viewStudentCurriculumForOfferCandidacy.jsp");
    }

    public ActionForward exportStudents(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException {
	JobBankSearchActionCommons commons = new JobBankSearchActionCommons();
	Spreadsheet resultSheet = commons.exportStudentsSearch(request);
	response.setContentType("application/xls ");
	String filename = "alunos" + new DateTime().toString("yyyy-MM-dd_HH:mm") + ".xls";
	response.setHeader("Content-disposition", "attachment; filename=" + filename);
	ServletOutputStream outputStream = response.getOutputStream();
	resultSheet.exportToXLSSheet(outputStream);
	outputStream.flush();
	outputStream.close();
	return null;
    }

    /* Configuration */

    public ActionForward configuration(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
	    final HttpServletResponse response) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	VariantBean beanUrlEmailValidation = getUrlEmailValidationBean();
	beanUrlEmailValidation.setString(jobBankSystem.getUrlEmailValidation());
	request.setAttribute("beanUrlEmailValidation", beanUrlEmailValidation);
	request.setAttribute("jobBankSystem", jobBankSystem);
	return forward(request, "/jobBank/backOffice/configuration.jsp");
    }

    public ActionForward updateUrlEmailValidation(final ActionMapping mapping, final ActionForm form,
	    final HttpServletRequest request, final HttpServletResponse response) {
	JobBankSystem jobBankSystem = JobBankSystem.getInstance();
	VariantBean variantBean = getRenderedObject("beanUrlEmailValidation");
	if (variantBean != null) {
	    jobBankSystem.setUrlEmailValidation(variantBean.getString());
	}
	return configuration(mapping, form, request, response);
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

    private VariantBean getUrlEmailValidationBean() {
	VariantBean beanUrlEmailValidation = getRenderedObject("beanUrlEmailValidation");
	if (beanUrlEmailValidation == null) {
	    beanUrlEmailValidation = new VariantBean();
	}
	return beanUrlEmailValidation;
    }
    /* End Configuration */

}
