<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>


<jsp:include page="../processStageView.jsp"/>
<bean:define id="jobOffer" name="process" property="jobOffer"/>
<bean:define id="candidates" name="process" property="jobOffer.activeOfferCandidacies"/>
<bean:define id="OID" name="process" property="externalId"/>

<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffer.information"/>
</h3>

<div class="infobox mvert1">
	<fr:view name="jobOffer">
		<fr:schema type="module.jobBank.domain.JobOffer" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification"/>
			<fr:slot name="externalCandidacy" key="label.enterprise.jobOffer.candidacyType.externalCandidacy"/>
			<fr:slot name="creationDate" key="label.enterprise.offer.creationDate"/>
			<fr:slot name="enterpriseName" key="label.enterprise.name"/>
			<fr:slot name="place" key="label.enterprise.jobOffer.place"/>
			<fr:slot name="jobOfferType" key="label.enterprise.jobOffer.jobType"/>
			<fr:slot name="descriptionOffer" key="label.enterprise.jobOffer.descriptionOffer" layout="longText"/>
			<fr:slot name="requirements" key="label.enterprise.jobOffer.requirements" layout="longText"/>
			<fr:slot name="beginDate" key="label.enterprise.offer.beginDate" layout="picker" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.DateTimeValidator"/> 
			<fr:slot name="endDate" key="label.enterprise.offer.endDate" layout="picker" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.DateTimeValidator"/>			
			<fr:layout name="tabular-nonNullValues">
				<fr:property name="classes" value="mvert05 thleft"/>
			</fr:layout>
		</fr:schema>
	</fr:view>
</div>



<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacies"/>
</h3>

<logic:equal name="jobOffer" property="externalCandidacy" value="false"> 
	<p>
		Aenean eu leo leo. Donec sem lorem, commodo vel blandit at, placerat aliquet metus. In hac habitasse platea dictumst. Donec molestie fermentum metus, non ullamcorper libero venenatis vitae. Maecenas ut libero nunc, eget malesuada est.
	</p>
	<logic:equal name="jobOffer" property="afterCompleteCandidancyPeriod" value="true">
		<table class="tview3 mtop5px"> 
			<tr> 
				<th>Vagas total:</th> 
				<td><bean:write name="jobOffer"  property="vacancies"/></td> 
			</tr> 
			<tr> 
				<th>Vagas Livres:</th> 
				<td><bean:write name="jobOffer"  property="numberOfFreeVacancies"/></td> 
			</tr> 
		</table> 
		<logic:present name="candidates">
			<fr:view name="candidates">
				<fr:layout name="tabular">
				
					<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
					<fr:property name="link(view)" value="<%="/enterprise.do?method=viewStudentCurriculum&OID="+OID %>" />
					<fr:property name="key(view)" value="link.jobBank.view" />
					<fr:property name="param(view)" value="student.externalId/studentOID" />
					<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
		
					
					<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
					<fr:property name="link(select)" value="<%="/enterprise.do?method=selectCandidateToJobOffer&OID="+OID %>"/>
					<fr:property name="key(select)" value="link.jobBank.selectCandidate" />
					<fr:property name="param(select)" value="student.externalId/studentOID" />
					<fr:property name="bundle(select)" value="JOB_BANK_RESOURCES" />
					<fr:property name="visibleIf(select)" value="canSelectCandidacy" />
					
					<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
					<fr:property name="link(remove)" value="<%="/enterprise.do?method=removeCandidateToJobOffer&OID="+OID %>"/>
					<fr:property name="key(remove)" value="link.jobBank.removeCandidate" />
					<fr:property name="param(remove)" value="student.externalId/studentOID" />
					<fr:property name="bundle(remove)" value="JOB_BANK_RESOURCES" />
					<fr:property name="visibleIf(remove)" value="canRemoveCandidacy" />
					
		
				</fr:layout>
				<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
					<fr:slot name="student.number" key="label.curriculum.id" />
					<fr:slot name="student.name" key="label.curriculum.name" />
					<fr:slot name="student.curriculum.email" key="label.curriculum.email" />
					<fr:slot name="selected" key="label.offerCandidacy.selected" />
				</fr:schema>
			</fr:view>
		</logic:present>
		
		<logic:empty name="candidates">
			<bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.candidates"/>
		</logic:empty>
	</logic:equal>
	
	
	<logic:equal name="jobOffer" property="afterCompleteCandidancyPeriod" value="false">
		Após o fecho das candidaturas poderá ver todos os candidatos
	</logic:equal>
</logic:equal>

<logic:equal name="jobOffer" property="externalCandidacy" value="true">
	Os candidatos são geridos por uma candidatura externa
</logic:equal>
	




