package module.jobBank.domain.activity;

import module.jobBank.domain.EnterpriseProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

public class MessageInformation extends ActivityInformation<EnterpriseProcess> {

    private String message;

    public MessageInformation(final EnterpriseProcess process,
            WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity) {
        super(process, activity);

    }

    public MessageInformation(final EnterpriseProcess process,
            WorkflowActivity<EnterpriseProcess, ? extends ActivityInformation<EnterpriseProcess>> activity, String msg) {
        super(process, activity);
        setMessage(msg);
    }

    @Override
    public boolean hasAllneededInfo() {
        return isForwardedFromInput();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getUsedSchema() {
        return "jobBank.activityInformation." + getActivity().getClass().getSimpleName();
    }

}
