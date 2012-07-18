package module.jobBank.domain.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import module.jobBank.domain.Enterprise;
import module.jobBank.domain.EnterpriseStateType;
import module.jobBank.domain.JobBankSystem;
import module.jobBank.domain.utils.IPredicate;
import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.domain.util.Search;

import org.apache.commons.lang.StringUtils;

import pt.utl.ist.fenix.tools.util.StringNormalizer;

public class SearchEnterprise extends Search<Enterprise> {

    private EnterpriseStateType enterpriseState;
    private String enterpriseName;
    private int enterprisesCount;

    public SearchEnterprise() {
	init();
    }

    public void init() {
	setEnterpriseState(EnterpriseStateType.ALL);
    }

    public void setEnterpriseState(EnterpriseStateType enterpriseState) {
	this.enterpriseState = enterpriseState;
    }

    public EnterpriseStateType getEnterpriseState() {
	return enterpriseState;
    }

    public void setEnterprisesCount(int enterprisesCount) {
	this.enterprisesCount = enterprisesCount;
    }

    public int getEnterprisesCount() {
	return enterprisesCount;
    }

    public void setEnterpriseName(String enterpriseName) {
	this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() {
	return enterpriseName;
    }

    private boolean isSatisfiedEnterpriseName(Enterprise enterprise) {
	return (getEnterpriseName() == null || StringUtils.isEmpty(getEnterpriseName())) || enterprise.getName() != null
		&& StringNormalizer.normalize(enterprise.getName().getContent()).contains(
			StringNormalizer.normalize(getEnterpriseName()));
    }

    private boolean isSatisfiedEnterpriseState(Enterprise enterprise) {
	return isEnterpriseAll(enterprise) || isEnterprisePendingRegister(enterprise) || isEnterpriseActive(enterprise)
		|| isEnterpriseDisabled(enterprise)
		|| isEnterpriseRequestChangeAgreement(enterprise) || isEnterpriseRejected(enterprise);
    }


    private boolean isEnterpriseAll(Enterprise enterprise) {
	return getEnterpriseState().equals(EnterpriseStateType.ALL);
    }

    private boolean isEnterpriseDisabled(Enterprise enterprise) {
	return getEnterpriseState().equals(EnterpriseStateType.INACTIVE) && enterprise.isDisable();
    }

    private boolean isEnterpriseRejected(Enterprise enterprise) {
	return getEnterpriseState().equals(EnterpriseStateType.REJECTED) && enterprise.isCanceled();
    }

    private boolean isEnterpriseRequestChangeAgreement(Enterprise enterprise) {
	return getEnterpriseState().equals(EnterpriseStateType.REQUEST_CHANGE_AGREEMENT)
		&& enterprise.isChangeToRequestAgreement();
    }

    private boolean isEnterpriseActive(Enterprise enterprise) {
	return getEnterpriseState().equals(EnterpriseStateType.ACTIVE) && enterprise.isActive();
    }

    private boolean isEnterprisePendingRegister(Enterprise enterprise) {
	return getEnterpriseState().equals(EnterpriseStateType.PENDING_REGISTER) && enterprise.isPendingToApproval();
    }


    @Override
    public Set<Enterprise> search() {
	normalizeStrings();
	final User user = UserView.getCurrentUser();
	final Set<Enterprise> enterprises = Enterprise.readAllEnterprises(new IPredicate<Enterprise>() {

	    @Override
	    public boolean evaluate(Enterprise object) {
		Enterprise enterprise = object;
		return JobBankSystem.getInstance().isNPEMember(user) && isSatisfiedEnterpriseState(enterprise)
				&& isSatisfiedEnterpriseName(enterprise);
	    }

	});

	return enterprises;
    }

    private void normalizeStrings() {
	if (getEnterpriseName() != null) {
	    setEnterpriseName(StringNormalizer.normalize(getEnterpriseName()));
	}
    }

    public List<Enterprise> sortedSearchByEnterpriseName() {
	ArrayList<Enterprise> results = new ArrayList<Enterprise>(search());
	Collections.sort(results, Enterprise.COMPARATOR_BY_ENTERPRISE_STATE_AND_NAME);
	return results;
    }

}
