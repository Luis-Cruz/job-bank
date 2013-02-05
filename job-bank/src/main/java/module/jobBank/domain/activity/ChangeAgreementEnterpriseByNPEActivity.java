package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.beans.EnterpriseBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.emailNotifier.domain.Email;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class ChangeAgreementEnterpriseByNPEActivity extends WorkflowActivity<EnterpriseProcess, EnterpriseContractInformation> {

    @Override
    public boolean isActive(EnterpriseProcess process, User user) {
        Enterprise enterprise = process.getEnterprise();
        return JobBankSystem.getInstance().isNPEMember(user) && !enterprise.isPendingAgreementToApprove()
                && !enterprise.hasAgreementForApproval() && !enterprise.isDisable();

    }

    @Override
    protected void process(EnterpriseContractInformation activityInformation) {
        Enterprise enterprise = activityInformation.getProcess().getEnterprise();
        EnterpriseBean bean = activityInformation.getEnterpriseBean();
        JobBankAccountabilityType jobBankAccountabilityType = bean.getNotActiveAccountabilityType();

        if (enterprise.changeRequestAgreementByNPE(jobBankAccountabilityType.readAccountabilityType())) {
            bean.setJobBankAccountabilityType(jobBankAccountabilityType);
            // enterpriseContractInformation.getEnterpriseBean().setJobBankAccountabilityType(jobBankAccountabilityType);
            sendEmail(enterprise, bean);
        }

        RenderUtils.invalidateViewState();
    }

    private void sendEmail(Enterprise enterprise, EnterpriseBean bean) {
        List<String> toAddresses = new ArrayList<String>();
        toAddresses.add(enterprise.getLoginEmail());
        String newContract = bean.getNotActiveAccountabilityType().getLocalizedName();
        JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        new Email(jobBankSystem.getEmailValidationFromName(), jobBankSystem.getEmailValidationFromEmail(), new String[] {},
                toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST, getEmailSubject(newContract), bean.getMessage());
    }

    @Override
    public ActivityInformation<EnterpriseProcess> getActivityInformation(EnterpriseProcess process) {
        EnterpriseContractInformation eci = new EnterpriseContractInformation(process, this);
        String message = getBody(eci.getEnterpriseBean().getName());
        eci.getEnterpriseBean().setMessage(message);
        return eci;
    }

    @Override
    public String getUsedBundle() {
        return JobBankSystem.JOB_BANK_RESOURCES;
    }

    @Override
    public boolean isDefaultInputInterfaceUsed() {
        return false;
    }

    private String getBody(MultiLanguageString enterpriseName) {
        StringBuilder body = new StringBuilder();
        body.append(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                "message.jobbank.contract.change.body", enterpriseName.toString()));
        body.append(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                "message.jobbank.ist.signature"));
        return body.toString();
    }

    private String getEmailSubject(String newContract) {
        String message =
                BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
                        "message.jobbank.message.jobbank.contract.change.subject.email", newContract);
        return message;
    }
}
