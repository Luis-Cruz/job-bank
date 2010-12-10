package module.jobBank.presentationTier.validators;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.JobBankSystem;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.renderers.validators.HtmlValidator;

public class EnterpriseNameNotRegisteredValidator extends HtmlValidator {

    @Override
    public void performValidation() {
	String enterpriseName = getComponent().getValue();
	if (Enterprise.isNameAlreadyRegistered(enterpriseName)) {
	    setMessage(BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES,
		    "error.jobBank.enterprise.name.already.exists"));
	    setValid(false);
	}
    }

}
