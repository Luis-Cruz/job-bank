package module.jobBank.domain;

import java.util.HashMap;
import java.util.Map;

import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class FenixDegree extends FenixDegree_Base {

	public FenixDegree() {
		super();
	}

	public FenixDegree(String remoteDegreeOid, String presentationName, String degreeTypeName) {
		setRemoteDegreeOid(remoteDegreeOid);
		update(presentationName, degreeTypeName);
	}

	public void updateName(String presentationName) {
		setName(new MultiLanguageString(Language.pt, presentationName));
	}

	public void update(String presentationName, String degreeTypeName) {
		updateName(presentationName);
		setDegreeType(FenixDegreeType.getByFenixDegreeTypeByName(degreeTypeName));
		setActive(true);
	}

	public boolean isBolonhaMasterDegree() {
		return getDegreeType().equals(FenixDegreeType.BOLONHA_MASTER_DEGREE)
				|| getDegreeType().equals(FenixDegreeType.BOLONHA_INTEGRATED_MASTER_DEGREE);
	}

	public static FenixDegree getFenixDegreeByIdInternal(int IdInternal) {
		JobBankSystem jobBank = JobBankSystem.getInstance();

		for (FenixDegree degree : jobBank.getActiveFenixDegreeSet()) {
			if (degree.getIdInternal() == IdInternal) {
				return degree;
			}
		}

		return null;
	}

	public static FenixDegree getFenixDegreeByExternalId(String externalId) {
		String externalIdToCompare = getJobBankRemoteDegreeId(externalId);
		JobBankSystem jobBank = JobBankSystem.getInstance();
		for (FenixDegree degree : jobBank.getFenixDegreeSet()) {
			if (degree.getRemoteDegreeOid().equals(externalIdToCompare)) {
				return degree;
			}
		}
		return null;
	}

	protected static String getJobBankRemoteDegreeId(String externalId) {
		Map<String, String> equalDegrees = new HashMap<String, String>();
		equalDegrees.put("2761663971567", "2761663971474");
		equalDegrees.put("2761663971585", "2761663971475");
		String externalIdToCompare = equalDegrees.get(externalId);
		return externalIdToCompare == null ? externalId : externalIdToCompare;
	}

}
