package module.jobBank.domain.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.JobOffer;
import module.jobBank.domain.JobOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.User;
import pt.ist.emailNotifier.domain.Email;

public class JobOfferApprovalActivity extends WorkflowActivity<JobOfferProcess, ActivityInformation<JobOfferProcess>> {

    @Override
    public boolean isActive(JobOfferProcess process, User user) {
	JobOffer jobOffer = process.getJobOffer();
	return jobOffer.isActive() && jobOffer.isPendingToApproval() && JobBankSystem.getInstance().isNPEMember(user);
    }

    @Override
    protected void process(ActivityInformation<JobOfferProcess> activityInformation) {
	JobOffer jobOffer = activityInformation.getProcess().getJobOffer();
	jobOffer.approve();
	List<String> toAddresses = new ArrayList<String>();
	toAddresses.add(jobOffer.getEnterprise().getLoginEmail());
	new Email("Job Bank", "noreply@ist.utl.pt", new String[] {}, toAddresses, Collections.EMPTY_LIST, Collections.EMPTY_LIST,
		"Registration Failed - Job Bank", getBody());
    }

    private String getBody() {
	StringBuilder body = new StringBuilder();
	body
		.append("Your request for registration was not approved. Contact Núcleo Parcerias Empresariais for more information.\n Thanks ");
	body.append(String.format("\n\n\n Instituto Superior Técnico"));
	return body.toString();
    }

    @Override
    public ActivityInformation<JobOfferProcess> getActivityInformation(JobOfferProcess process) {
	return new ActivityInformation(process, this);
    }

    @Override
    public String getUsedBundle() {
	return JobBankSystem.JOB_BANK_RESOURCES;
    }
}
