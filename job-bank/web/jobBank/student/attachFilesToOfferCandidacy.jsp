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
		<fr:destination name="postback" path="/student.do?method=attachFilesToOfferCandidacy" />
		<fr:destination name="cancel" path="/student.do?method=searchOffers" />
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.OfferCandidacyBean">
			<fr:slot name="attachFiles" key="link.jobBank.view" layout="option-select">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.OfferCandidacyAttachFilesProvider" />
				<fr:property name="eachSchema" value="jobBank.enterprise.offerCandidacy.attachFiles" />
				<fr:property name="eachLayout" value="values" />
			</fr:slot>
		</fr:schema>
	</fr:edit>
	
	<html:submit styleClass="inputbutton">
		<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit" />
	</html:submit>
	
	<html:cancel styleClass="inputbutton">
		<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel" />
	</html:cancel>
</fr:form>
