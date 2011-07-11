package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class EnterpriseApprovalInformation extends ActivityInformation<EnterpriseProcess> {

    public enum ApprovalOption implements IPresentableEnum {
	APPROVE("approve", "label.enterprise.approve"), REJECT("reject", "label.enterprise.reject");

	private final String type;
	private final String nameKey;
	private final String bundle;

	private ApprovalOption(final String type, final String nameKey, final String bundle) {
	    this.type = type;
	    this.nameKey = nameKey;
	    this.bundle = bundle;
	}

	private ApprovalOption(final String type, final String nameKey) {
	    this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
	}

	public String getType() {
	    return type;
	}

	@Override
	public String getLocalizedName() {
	    return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
	}
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String message;

    private ApprovalOption option;

    private ApprovalOption previousOption;

    private MultiLanguageString enterpriseName;

    public EnterpriseApprovalInformation(final EnterpriseProcess process,
	    WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity,
	    MultiLanguageString enterpriseName) {
	super(process, activity);
	setEnterpriseName(enterpriseName);
	setOption(ApprovalOption.APPROVE);
	setPreviousOption(ApprovalOption.APPROVE);
	updateMessage();
    }

    private void updateMessage() {
	StringBuilder body = new StringBuilder();
	body.append(enterpriseName);
	body.append(getBody());
	body.append(getSignature());
	setMessage(body.toString());
    }

    private String getBody() {
	String bundleName = "message.jobbank.enterprise.approval.email.body";

	if (!option.equals(ApprovalOption.APPROVE)) {
	    bundleName = "message.jobbank.enterprise.rejection.email.body";
	}

	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundleName);
    }
    
    
    private String getSignature() {
	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "message.jobbank.ist.signature");
    }

    @Override
    public boolean hasAllneededInfo() {
	if (decisionChanged()) {
	    updateMessage();
	    setPreviousOption(option);
	    RenderUtils.invalidateViewState();
	    return false;
	}
	return isForwardedFromInput();
    }

    private boolean decisionChanged() {
	return !option.equals(previousOption);
    }

    @Override
    public String getUsedSchema() {
	return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getMessage() {
	return message;
    }

    public void setOption(ApprovalOption option) {
	this.option = option;
    }

    public ApprovalOption getOption() {
	return option;
    }

    public void setPreviousOption(ApprovalOption previousOption) {
	this.previousOption = previousOption;
    }

    public ApprovalOption getPreviousOption() {
	return previousOption;
    }

    public void setEnterpriseName(MultiLanguageString enterpriseName) {
	this.enterpriseName = enterpriseName;
    }

    public MultiLanguageString getEnterpriseName() {
	return enterpriseName;
    }

    public boolean isApprove() {
	return option.equals(ApprovalOption.APPROVE);
    }

}
