package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.jobBank.domain.JobBankSystem;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

public abstract class ApprovalInformation extends ActivityInformation<EnterpriseProcess> {

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

    public ApprovalInformation(final EnterpriseProcess process,
            WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity) {
        super(process, activity);
        setOption(ApprovalOption.APPROVE);
        setPreviousOption(ApprovalOption.APPROVE);
    }

    abstract void updateMessage();

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

    public boolean isApprove() {
        return option.equals(ApprovalOption.APPROVE);
    }

}
