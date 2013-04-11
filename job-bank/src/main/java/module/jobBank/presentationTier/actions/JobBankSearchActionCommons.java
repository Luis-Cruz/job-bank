package module.jobBank.presentationTier.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseStateType;
import module.jobBank.domain.FenixCycleType;
import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOfferProcess;
import module.jobBank.domain.JobOfferState;
import module.jobBank.domain.JobOfferType;
import module.jobBank.domain.StudentRegistration;
import module.jobBank.domain.StudentRegistrationCycleType;
import module.jobBank.domain.beans.SearchEnterprise;
import module.jobBank.domain.beans.SearchOffer;
import module.jobBank.domain.beans.SearchOfferState;
import module.jobBank.domain.beans.SearchStudentRegistrations;
import module.jobBank.domain.utils.Utils;

import org.apache.commons.lang.StringUtils;

import pt.ist.bennu.core.presentationTier.actions.ContextBaseAction;
import pt.ist.bennu.core.util.BundleUtil;
import pt.utl.ist.fenix.tools.util.excel.Spreadsheet;
import pt.utl.ist.fenix.tools.util.excel.Spreadsheet.Row;

public class JobBankSearchActionCommons extends ContextBaseAction {

    public void processJobOffersSearch(final HttpServletRequest request) {
        SearchOfferState offerSearch = getRenderedObject("offerSearch");
        if (offerSearch == null) {
            offerSearch = new SearchOfferState();

            String offerState = request.getParameter("offerState");
            String offerType = request.getParameter("offerType");
            String enterprise = request.getParameter("enterprise");
            String processNumber = request.getParameter("processNumber");

            if (offerState != null && JobOfferState.getByName(offerState) != null) {
                offerSearch.setJobOfferState(JobOfferState.getByName(offerState));
            }

            if (offerType != null && JobOfferType.getByName(offerType) != null) {
                offerSearch.setJobOfferType(JobOfferType.getByName(offerType));
            }

            if (enterprise != null) {
                offerSearch.setEnterprise(enterprise);
            }

            if (processNumber != null) {
                offerSearch.setProcessNumber(processNumber);
            }
        }

        int resultsPerPage = 25;
        List<JobOfferProcess> processes = offerSearch.sortedSearchByRegistration();
        offerSearch.setProcessesCount(processes.size());

        request.setAttribute("offerSearch", offerSearch);
        request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));
    }

    public void processStudentJobOfferSearch(final HttpServletRequest request) {
        SearchOffer search = getRenderedObject("search");
        boolean firstSearch = (search == null);

        if (search == null) {
            search = new SearchOffer();

            String query = request.getParameter("query");
            String degreeId = request.getParameter("degrees");
            String jobOfferType = request.getParameter("jobOfferType");

            if (query != null) {
                search.setQuery(query);
            }

            if (degreeId != null) {
                FenixDegree degree = FenixDegree.getFenixDegreeBy(degreeId);
                if (degree != null) {
                    search.setDegrees(degree);
                }
            }

            if (jobOfferType != null && JobOfferType.getByName(jobOfferType) != null) {
                search.setJobOfferType(JobOfferType.getByName(jobOfferType));
            }

            if (request.getParameter("pageNumber") != null) {
                firstSearch = false;
            }
        }

        if (!firstSearch) {
            int resultsPerPage = 25;
            List<JobOfferProcess> processes = search.sortedSearchByRegistration();
            request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));
        }

        request.setAttribute("search", search);
    }

    public void processEnterprisesSearch(HttpServletRequest request) {
        SearchEnterprise enterpriseSearch = getRenderedObject("enterpriseSearch");
        if (enterpriseSearch == null) {
            enterpriseSearch = new SearchEnterprise();

            String enterpriseState = request.getParameter("enterpriseState");
            String enterpriseName = request.getParameter("enterpriseName");

            if (enterpriseState != null && EnterpriseStateType.getByName(enterpriseState) != null) {
                enterpriseSearch.setEnterpriseState(EnterpriseStateType.getByName(enterpriseState));
            }

            if (enterpriseName != null) {
                enterpriseSearch.setEnterpriseName(enterpriseName);
            }
        }

        int resultsPerPage = 25;
        List<Enterprise> processes = enterpriseSearch.sortedSearchByEnterpriseName();
        enterpriseSearch.setEnterprisesCount(processes.size());

        request.setAttribute("enterpriseSearch", enterpriseSearch);
        request.setAttribute("processes", Utils.doPagination(request, processes, resultsPerPage));
    }

    public void processStudentsSearch(final HttpServletRequest request) {
        SearchStudentRegistrations search = getRenderedObject("searchStudents");
        boolean firstSearch = (search == null);

        if (search == null) {
            search = new SearchStudentRegistrations();

            String username = request.getParameter("username");
            if (username != null) {
                search.setUsername(username);
            }

            String degreeId = request.getParameter("degree");
            if (degreeId != null) {
                FenixDegree degree = FenixDegree.getFenixDegreeBy(degreeId);
                if (degree != null) {
                    search.setDegree(degree);
                }
            }

            Boolean registrationConclued = Boolean.valueOf(request.getParameter("registrationConclued"));
            if (registrationConclued != null) {
                search.setRegistrationConclued(registrationConclued);
            }

            if (request.getParameter("pageNumber") != null) {
                firstSearch = false;
            }
        }

        if (!firstSearch) {
            int resultsPerPage = 25;
            List<StudentRegistration> registrations = search.sortedSearchByStudentName();
            request.setAttribute("results", Utils.doPagination(request, registrations, resultsPerPage));
            request.setAttribute("resultsCount", registrations.size());
        }

        request.setAttribute("searchStudents", search);
    }

    public Spreadsheet exportStudentsSearch(HttpServletRequest request) {
        Spreadsheet spreadsheet = new Spreadsheet("Alunos");
        SearchStudentRegistrations search = getRenderedObject("searchStudents");
        if (search != null) {
            List<StudentRegistration> registrations = search.sortedSearchByStudentName();
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.degree"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.name"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.dateOfBirth"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.nationality"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.email"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.phone"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.mobilePhone"));
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.curriculum.address"));
            spreadsheet.setHeader("Ano Curricular");
            spreadsheet.setHeader(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                    "label.enterprise.degree.is.concluded"));

            for (FenixCycleType fenixCycleType : FenixCycleType.values()) {
                spreadsheet.setHeader(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                        "label.curriculum.averageForCycle", fenixCycleType.getLocalizedName()));
            }

            for (StudentRegistration studentRegistration : registrations) {
                Row row = spreadsheet.addRow();
                row.setCell(studentRegistration.getFenixDegree().getName().getContent());
                row.setCell(studentRegistration.getStudent().getPerson().getName());
                row.setCell(studentRegistration.getStudent().getCurriculum().getDateOfBirth() == null ? null : studentRegistration
                        .getStudent().getCurriculum().getDateOfBirth().toString("dd-MM-yyyy"));
                row.setCell(studentRegistration.getStudent().getCurriculum().getNationality());
                row.setCell(studentRegistration.getStudent().getCurriculum().getEmail());
                row.setCell(studentRegistration.getStudent().getCurriculum().getPhone());
                row.setCell(studentRegistration.getStudent().getCurriculum().getMobilePhone());

                List<String> address = new ArrayList<String>();
                address.add(studentRegistration.getStudent().getCurriculum().getAddress());
                address.add(studentRegistration.getStudent().getCurriculum().getAreaCode());
                address.add(studentRegistration.getStudent().getCurriculum().getArea());

                row.setCell(StringUtils.join(address, " "));

                row.setCell(studentRegistration.getCurricularYear());
                String isConcluded = "label.no";
                if (studentRegistration.getIsConcluded()) {
                    isConcluded = "label.yes";
                }
                row.setCell(BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, isConcluded));
                for (FenixCycleType fenixCycleType : FenixCycleType.values()) {
                    StudentRegistrationCycleType studentRegistrationCycleType =
                            studentRegistration.getStudentRegistrationCycleType(fenixCycleType);
                    if (studentRegistrationCycleType != null) {
                        row.setCell(studentRegistrationCycleType.getAverage().toString());
                    } else {
                        row.setCell("-");
                    }
                }
            }
        }
        return spreadsheet;
    }
}
