package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class EnterpriseEnableOrDisableInformation extends ActivityInformation<EnterpriseProcess> {

    public enum BlockOption implements IPresentableEnum {
	BLOCK("block", "label.enterprise.block"), UNBLOCK("reject", "label.enterprise.unblock");

	private final String type;
	private final String nameKey;
	private final String bundle;

	private BlockOption(final String type, final String nameKey, final String bundle) {
	    this.type = type;
	    this.nameKey = nameKey;
	    this.bundle = bundle;
	}

	private BlockOption(final String type, final String nameKey) {
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

    private BlockOption option;

    private MultiLanguageString enterpriseName;

    public EnterpriseEnableOrDisableInformation(final EnterpriseProcess process,
	    WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity,
	    MultiLanguageString enterpriseName, boolean enterpriseIsBlocked) {
	super(process, activity);
	setEnterpriseName(enterpriseName);
	setOption(enterpriseIsBlocked ? BlockOption.BLOCK : BlockOption.UNBLOCK);
	updateMessage();
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

    public void updateMessage() {
	StringBuilder body = new StringBuilder();
	body.append(getBody(enterpriseName));
	body.append(getSignature());
	setMessage(body.toString());
    }

    private String getBody(MultiLanguageString enterpriseName) {
	String bundleName = "message.jobbank.enterprise.disable.email.body";

	if (option.equals(BlockOption.UNBLOCK)) {
	    bundleName = "message.jobbank.enterprise.enable.email.body";
	}

	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, bundleName,
		enterpriseName.toString());
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    private String getSignature() {
	return BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "message.jobbank.ist.signature");
    }

    public void setOption(BlockOption option) {
	this.option = option;
    }

    public BlockOption getOption() {
	return option;
    }

    public void setEnterpriseName(MultiLanguageString enterpriseName) {
	this.enterpriseName = enterpriseName;
    }

    public MultiLanguageString getEnterpriseName() {
	return enterpriseName;
    }

    public boolean isBlock() {
	return option.equals(BlockOption.BLOCK);
    }
}
