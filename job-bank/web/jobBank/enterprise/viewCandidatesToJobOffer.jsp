<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidates"/></h2>

<bean:define id="OID" name="process" property="externalId"/>
<bean:size id="candidatesSize" name="candidates"/>


<logic:present name="candidates">
	<fr:view name="candidates">
		<fr:layout name="tabular">
		
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(view)" value="<%="/student.do?method=viewCurriculum&OID="+OID %>" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="externalId/candidateOID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />

			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(select)" value="<%="/enterprise.do?method=selectCandidateToJobOffer&OID="+OID %>"/>
			<fr:property name="key(select)" value="link.jobBank.selectCandidate" />
			<fr:property name="param(select)" value="externalId/candidateOID" />
			<fr:property name="bundle(select)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIfNot(select)" value="selected" />
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(remove)" value="<%="/enterprise.do?method=removeCandidateToJobOffer&OID="+OID %>"/>
			<fr:property name="key(remove)" value="link.jobBank.removeCandidate" />
			<fr:property name="param(remove)" value="externalId/candidateOID" />
			<fr:property name="bundle(remove)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(remove)" value="selected" />
			
			
		

		</fr:layout>
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
			<fr:slot name="student.name" key="label.curriculum.name" />
			<fr:slot name="student.curriculum.email" key="label.curriculum.email" />
		</fr:schema>
	</fr:view>
</logic:present>

<logic:equal name="candidatesSize" value="0">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.candidates"/>
</logic:equal>


