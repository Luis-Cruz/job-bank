package module.jobBank.domain.activity;

import java.io.InputStream;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.beans.EnterpriseBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.util.ByteArray;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

public class EnterpriseInformation extends ActivityInformation<EnterpriseProcess> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EnterpriseBean enterpriseBean;

    private String oldPassword;

    private final String realOldPassword;

    private boolean requestOldPassword;

    private boolean badOldPassword;

    private boolean badConfirmation;

    public String getLogoDisplayName() {
	return logoDisplayName;
    }

    public void setLogoDisplayName(String logoDisplayName) {
	this.logoDisplayName = logoDisplayName;
    }

    private ByteArray logo;
    private transient InputStream inputStream;
    private String filename;
    private String logoDisplayName;

    public EnterpriseInformation(final EnterpriseProcess process,
	    WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity) {
	super(process, activity);
	setEnterpriseBean(new EnterpriseBean(process.getEnterprise()));
	this.realOldPassword = process.getEnterprise().getUser().getPassword();
	setBadConfirmation(false);
	cleanPasswordChangeData();
    }

    @Override
    public boolean hasAllneededInfo() {
	return isPasswordChangeValid() && isForwardedFromInput();
    }

    private boolean isPasswordChangeValid() {
	return isPasswordConfirmationValid() && isOldPasswordValid();
    }

    private boolean isOldPasswordValid() {
	if(!enterpriseBean.getPassword().equals(getRealOldPassword()) && getOldPassword().isEmpty()) {
	    setRequestOldPassword(true);
	    cleanOldPasswordField();
	    return false;
	} else if (!enterpriseBean.getPassword().equals(getRealOldPassword()) && !getOldPassword().equals(getRealOldPassword())) {
	    setBadOldPassword(true);
	    cleanOldPasswordField();
	    return false;
	}

	cleanPasswordChangeData();

	return true;
    }

    private void cleanPasswordChangeData() {
	setRequestOldPassword(false);
	setBadOldPassword(false);
	cleanOldPasswordField();
    }

    private void cleanOldPasswordField() {
	setOldPassword("");
	RenderUtils.invalidateViewState();
    }

    private boolean isPasswordConfirmationValid() {
	if (!enterpriseBean.getPassword().equals(enterpriseBean.getRepeatPassword())) {
	    setBadConfirmation(true);
	    return false;
	}

	setBadConfirmation(false);
	return true;
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

    public void setBadOldPassword(boolean badOldPassword) {
	this.badOldPassword = badOldPassword;
    }

    public boolean isBadOldPassword() {
	return badOldPassword;
    }

    public String getRealOldPassword() {
	return realOldPassword;
    }

    public void setBadConfirmation(boolean badConfirmation) {
	this.badConfirmation = badConfirmation;
    }

    public boolean isBadConfirmation() {
	return badConfirmation;
    }

}
