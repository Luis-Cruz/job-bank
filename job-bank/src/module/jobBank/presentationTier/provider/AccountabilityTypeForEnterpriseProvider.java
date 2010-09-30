package module.jobBank.presentationTier.provider;

import java.util.ArrayList;
import java.util.List;

import module.jobBank.domain.JobBankAccountabilityType;
import module.organization.domain.AccountabilityType;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class AccountabilityTypeForEnterpriseProvider implements DataProvider {

    @Override
    public Object provide(final Object source, final Object currentValue) {
	List<AccountabilityType> types = new ArrayList<AccountabilityType>();
	types.add(JobBankAccountabilityType.JOB_PROVIDER.readAccountabilityType());
	types.add(JobBankAccountabilityType.JOB_PROVIDER_WITH_PRIVILEGES.readAccountabilityType());
	return types;
    }

    @Override
    public Converter getConverter() {
	return null;
    }

}
