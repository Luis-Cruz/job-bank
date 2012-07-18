package module.jobBank.presentationTier.validators;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.renderers.validators.HtmlValidator;

public class EmailNotDefinedValidator extends HtmlValidator {

    @Override
    public void performValidation() {
	String emailLogin = getComponent().getValue();
	if (Enterprise.isLoginEmailAlreadyRegistered(emailLogin)) {
	    setMessage(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
		    "message.error.enterprise.email.already.registered"));
	    setValid(false);
	}
    }

}
