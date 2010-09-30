package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.beans.EnterpriseBean;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class EnterpriseInformation extends ActivityInformation<EnterpriseProcess> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EnterpriseBean enterpriseBean;

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
