package module.jobBank.domain.task;

import java.util.HashSet;
import java.util.Set;

import module.jobBank.domain.FenixDegree;
import module.jobBank.domain.FenixDegreeType;
import module.jobBank.domain.JobBankSystem;
import module.webserviceutils.domain.HostSystem;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pt.ist.bennu.core.util.BundleUtil;

public class CreateOrUpdateFenixDegreesTask extends UpdateExpiredEnterpriseAgreementsTask_Base {

    public CreateOrUpdateFenixDegreesTask() {
	super();
    }

    @Override
    public void executeTask() {
	String jsonDegrees = HostSystem.getFenixJerseyClient().method("readBolonhaDegrees").get();
	JSONParser parser = new JSONParser();
	Set<FenixDegree> activeFenixDegrees = new HashSet<FenixDegree>();
	try {
	    JSONArray degreesInfos = (JSONArray) parser.parse(jsonDegrees);
	    for (Object degreeInfo : degreesInfos) {
		JSONObject jsonDegreeInfo = (JSONObject) degreeInfo;
		final String degreeOid = (String) jsonDegreeInfo.get("degreeOid");
		final String name = (String) jsonDegreeInfo.get("name");
		String degreeTypeString = (String) jsonDegreeInfo.get("degreeType");
		FenixDegree fenixDegreeByExternalId = FenixDegree.getFenixDegreeByExternalId(degreeOid);
		if (fenixDegreeByExternalId != null) {
		    if (!equals(fenixDegreeByExternalId, name, degreeTypeString)) {
			fenixDegreeByExternalId.update(name, degreeTypeString);
		    }
		} else {
		    fenixDegreeByExternalId = new FenixDegree(degreeOid, name, degreeTypeString);
		}
		activeFenixDegrees.add(fenixDegreeByExternalId);
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	JobBankSystem jobBank = JobBankSystem.getInstance();
	for (FenixDegree degree : jobBank.getFenixDegreeSet()) {
	    if (!activeFenixDegrees.contains(degree)) {
		degree.setActive(false);
	    }
	}
    }

    private boolean equals(FenixDegree fenixDegreeByExternalId, String name, String degreeTypeString) {
	return fenixDegreeByExternalId.getName().equalInAnyLanguage(name)
		&& fenixDegreeByExternalId.getDegreeType().equals(FenixDegreeType.valueOf(degreeTypeString))
		&& fenixDegreeByExternalId.getActive();
    }

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, getClass().getName());
    }
}
