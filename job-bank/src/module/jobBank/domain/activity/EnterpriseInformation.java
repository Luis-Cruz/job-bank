package module.jobBank.domain.activity;

import java.io.InputStream;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.beans.EnterpriseBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.util.ByteArray;

public class EnterpriseInformation extends ActivityInformation<EnterpriseProcess> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EnterpriseBean enterpriseBean;

    // public ByteArray getLogo() {
    // return logo;
    // }
    //
    // public void setLogo(ByteArray logo) {
    // this.logo = logo;
    // }
    //
    // public InputStream getInputStream() {
    // return inputStream;
    // }
    //
    // public void setInputStream(InputStream inputStream) {
    // this.inputStream = inputStream;
    // }
    //
    // public String getFilename() {
    // return filename;
    // }
    //
    // public void setFilename(String filename) {
    // this.filename = filename;
    // }

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
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
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

}
