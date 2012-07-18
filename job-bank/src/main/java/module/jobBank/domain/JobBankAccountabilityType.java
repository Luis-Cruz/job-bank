package module.jobBank.domain;

import module.organization.domain.AccountabilityType;
import module.organization.domain.AccountabilityType.AccountabilityTypeBean;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.rendererExtensions.util.IPresentableEnum;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public enum JobBankAccountabilityType implements IPresentableEnum {

    PENDING("Pending", "label.jobBank.pending"), JOB_PROVIDER("JobProvider", "label.jobBank.jobProvider"), JOB_PROVIDER_WITH_PRIVILEGES(
	    "jobProviderWithPrivileges", "label.jobBank.jobProviderWithPrivileges");
    private final String type;
    private final String nameKey;
    private final String bundle;

    private JobBankAccountabilityType(final String type, final String nameKey, final String bundle) {
	this.type = type;
	this.nameKey = nameKey;
	this.bundle = bundle;
    }

    private JobBankAccountabilityType(final String type, final String nameKey) {
	this(type, nameKey, JobBankSystem.JOB_BANK_RESOURCES);
    }

    public String getType() {
	return type;
    }

    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(bundle, nameKey);
    }

    public AccountabilityType readAccountabilityType() {
	return AccountabilityType.readBy(getType());
    }

    public void createAccountabilityType() {
	final AccountabilityTypeBean accountabilityTypeBean = new AccountabilityTypeBean(getType(), new MultiLanguageString(
		getLocalizedName()));
	AccountabilityType.create(accountabilityTypeBean);
    }

    public static JobBankAccountabilityType readAccountabilityType(AccountabilityType activeAccountabilityType) {
	if (activeAccountabilityType == null) {
	    return null;
	}
	if (activeAccountabilityType.equals(JobBankAccountabilityType.PENDING.readAccountabilityType())) {
	    return JobBankAccountabilityType.PENDING;
	}
	if (activeAccountabilityType.equals(JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType())) {
	    return JobBankAccountabilityType.JOB_PROVIDER;
	}
	if (activeAccountabilityType.equals(JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES.readAccountabilityType())) {
	    return JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES;
	}
	return null;

    }
}
