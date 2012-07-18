package module.jobBank.domain.activity;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.beans.EnterpriseBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.exceptions.DomainException;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

public class EnterpriseInformation extends ActivityInformation<EnterpriseProcess> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EnterpriseBean enterpriseBean;

    private String oldPassword;

    private boolean requestOldPassword;

    public String getLogoDisplayName() {
	return logoDisplayName;
    }

    public void setLogoDisplayName(String logoDisplayName) {
	this.logoDisplayName = logoDisplayName;
    }

    private String logoDisplayName;

    public EnterpriseInformation(final EnterpriseProcess process,
	    WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity) {
	super(process, activity);
	setEnterpriseBean(new EnterpriseBean(process.getEnterprise()));
	cleanOldPasswordField();
	setRequestOldPassword(false);
	setOldPassword("");
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    public void checkPasswords() {
	checkOldPassword();
    }

    private void checkOldPassword() {
	final EnterpriseProcess process = getProcess();
	final Enterprise enterprise = process.getEnterprise();
	final User user = enterprise.getUser();

	if (!user.matchesPassword(enterpriseBean.getPassword())) {
	    enterpriseBean.checkPassword();
	    if (getOldPassword().isEmpty()) {
		cleanOldPasswordField();
		throw new DomainException("message.error.need.old.password",
			DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	    } else if (!user.matchesPassword(getOldPassword())) {
		cleanOldPasswordField();
		throw new DomainException("message.error.bad.old.password",
			DomainException.getResourceFor(JobBankSystem.JOB_BANK_RESOURCES));
	    }
	}

	setRequestOldPassword(false);
    }

    private void cleanOldPasswordField() {
	setRequestOldPassword(true);
	setOldPassword("");
	RenderUtils.invalidateViewState();
    }

    public EnterpriseBean getEnterpriseBean() {
	return enterpriseBean;
    }

    public void setEnterpriseBean(EnterpriseBean enterpriseBean) {
	this.enterpriseBean = enterpriseBean;
    }

    @Override
    public String getUsedSchema() {
	return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
    }

    public void setRequestOldPassword(boolean requestOldPassword) {
	this.requestOldPassword = requestOldPassword;
    }

    public boolean isRequestOldPassword() {
	return requestOldPassword;
    }

    public void setOldPassword(String oldPassword) {
	this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
	return oldPassword;
    }

}
