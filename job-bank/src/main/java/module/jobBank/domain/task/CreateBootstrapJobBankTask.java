package module.jobBank.domain.task;

import module.jobBank.domain.JobBankAccountabilityType;
import module.jobBank.domain.JobBankSystem;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Party;
import module.organization.domain.PartyType;
import module.organization.domain.PartyType.PartyTypeBean;
import module.organization.domain.dto.OrganizationalModelBean;
import module.organization.domain.search.PartySearchBean;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.FenixFramework;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class CreateBootstrapJobBankTask extends CreateBootstrapJobBankTask_Base {

    public CreateBootstrapJobBankTask() {
        super();
    }

    @Override
    public void executeTask() {

        JobBankAccountabilityType[] accountabilityTypes = JobBankAccountabilityType.values();
        for (JobBankAccountabilityType accountabilityType : accountabilityTypes) {
            accountabilityType.createAccountabilityType();
        }

        PartyTypeBean bean = new PartyTypeBean();
        bean.setType(JobBankSystem.PARTY_TYPE_NAME);
        bean.setName(new MultiLanguageString("Empresa"));
        PartyType.create(bean);

        OrganizationalModelBean organizationalModelBean = new OrganizationalModelBean();
        organizationalModelBean.setName(new MultiLanguageString("Banco de Empregos"));
        OrganizationalModel orgModel = OrganizationalModel.createNewModel(organizationalModelBean);

        // Get party NPE (Nucleo Parcerias Empresariais)
        Party party = (Party) FenixFramework.getDomainObject("450971740485");
        final PartySearchBean partySearchBean = new PartySearchBean(party);
        orgModel.addPartyService(partySearchBean.getParty());

        final JobBankSystem jobBankSystem = JobBankSystem.getInstance();
        jobBankSystem.setOrganizationalModel(orgModel);

        jobBankSystem.setUrlEmailValidation("http://localhost:8080/myorg/enterprise.do?method=emailValidation");

    }

    @Override
    public String getLocalizedName() {
        return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
    }

}
