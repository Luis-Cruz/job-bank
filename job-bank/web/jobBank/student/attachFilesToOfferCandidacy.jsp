<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.attachFileToOfferCandidacy"/>
</h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<bean:define id="OID" name="jobOfferProcess" property="externalId"/> 
<bean:message bundle="JOB_BANK_RESOURCES" key="message.offerCandidacy.chooseFilesToAttach"/>

<fr:form action='<%="/student.do?method=candidateToJobOffer&OID="+OID %>' >
	<fr:edit id="offerCandidacyBean" name="offerCandidacyBean">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.OfferCandidacyBean">
			<fr:slot name="processFile" layout="menu-select-postback" bundle="JOB_BANK_RESOURCES" key="link.jobBank.view">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.OfferCandidacyAttachFilesProvider" />
				<fr:property name="eachLayout" value="values" />
				<fr:property name="eachSchema" value="jobBank.enterprise.offerCandidacy.attachFiles" />
				<fr:property name="saveOptions" value="true" />
				<fr:property name="sortBy" value="filename=asc"/>
			</fr:slot>
		</fr:schema>	
		<fr:destination name="postback" path="/student.do?method=attachFilesToOfferCandidacy" />
	</fr:edit>
	
	
	<logic:empty name="offerCandidacyBean" property="attachFiles">
		<p>
			<bean:message key="message.offerCandidacy.warning.no.files.attached" bundle="JOB_BANK_RESOURCES" />
		</p>
	</logic:empty>	
	
	<logic:notEmpty name="offerCandidacyBean" property="attachFiles">	
		<html:link action="/student.do?method=clearFilesToOfferCandidacy" paramId="OID" paramName="jobOfferProcess" paramProperty="externalId">
	        <bean:message key="link.jobBank.clear" bundle="JOB_BANK_RESOURCES" />
	    </html:link>
	    
		<fr:view name="offerCandidacyBean" property="attachFiles">
			<fr:schema bundle="JOB_BANK_RESOURCES" type="module.workflow.domain.ProcessFile">
				<fr:slot name="displayName" key="label.offerCandidacy.attached.files"/>
			</fr:schema>
			<fr:layout name="tabular">
			   <bean:define id="curriculumExternalId" name="offerCandidacyBean" property="student.curriculum.curriculumProcess.externalId" />
					<fr:property name="classes" value="tstyle3" />
					
					<fr:property name="link(view)"
					value = '<%="/workflowProcessManagement.do?method=downloadFile&amp;&amp;processId="+curriculumExternalId %>' />
					<fr:property name="key(view)" value="link.jobBank.view" />
					<fr:property name="param(view)" value="externalId/fileId" />
					<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
					<fr:property name="order(view)" value="1" />
	
			</fr:layout>
					
		</fr:view>
	</logic:notEmpty>
	
	<html:submit styleClass="inputbutton">
		<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit" />
	</html:submit>
	
	<html:cancel styleClass="inputbutton">
		<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel" />
	</html:cancel>
	
	
	
</fr:form> 




