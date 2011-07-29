<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.offer"/></h2>

<bean:define id="jobOffer" name="process" property="jobOffer"/>
<bean:define id="processId" name="process" property="externalId"/>


<p> </p>

<logic:messagesPresent property="message" message="true">
	<div class="warning1">
		<html:messages id="errorMessage" property="message" message="true" bundle="JOB_BANK_RESOURCES"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
		<p>
	</div>
</logic:messagesPresent>

<!-- UNCOMMENT IF COMMENTS ARE REQUESTED! -->

<%-- <bean:size id="comments"  name="process" property="comments"/>		  --%>
<!-- <span class="comments"> -->
<%-- 	<html:link page="/student.do?method=viewJobOfferComments" paramId="processId" paramName="process" paramProperty="externalId"> --%>
<%-- 	<%= comments %> --%>
<%-- 	<logic:equal name="comments" value="1"> --%>
<%-- 		<bean:message key="link.comment" bundle="WORKFLOW_RESOURCES"/> --%>
<%-- 	</logic:equal> --%>
<%-- 	<logic:notEqual name="comments" value="1"> --%>
<%-- 		<bean:message key="link.comments" bundle="WORKFLOW_RESOURCES"/> --%>
<%-- 	</logic:notEqual> --%>
<%-- 	</html:link> --%>
<%-- 	<bean:define id="unreadComments" name="process" property="unreadCommentsForCurrentUser"/> --%>
<%-- 	<logic:notEmpty name="unreadComments"> --%>
<%-- 		<bean:size id="count" name="unreadComments"/> (<%= count.toString() %> novos)  --%>
<%-- 	</logic:notEmpty> --%>
<!-- </span> -->


<!-- SUBMIT CANDIDACY -->

<bean:define id="externalCandidacyConfirmationMessage">return confirm('<bean:message key="message.confirmation.external.candidacy" bundle="JOB_BANK_RESOURCES" />')</bean:define>

<logic:equal name="jobOffer" property="canCreateOfferCandidacy" value="true">
	<span class="valid">
		<logic:equal name="jobOffer" property="externalCandidacy" value="false">
			<html:link action="/student.do?method=attachFilesToOfferCandidacy" paramId="OID" paramName="process" paramProperty="externalId">
				<bean:message key="link.jobOffer.candidate" bundle="JOB_BANK_RESOURCES"/>
			</html:link>
		</logic:equal>
		
		<logic:equal name="jobOffer" property="externalCandidacy" value="true">
			<html:link action="/student.do?method=candidateToExternalJobOffer" paramId="OID" paramName="process" paramProperty="externalId" onclick="<%= externalCandidacyConfirmationMessage %>">
				<bean:message key="link.jobOffer.external.candidate" bundle="JOB_BANK_RESOURCES"/>
			</html:link>
		</logic:equal>
	</span>
</logic:equal>


<!-- REMOVE CANDIDACY -->


<bean:define id="removeConfirmationMessage">return confirm('<bean:message key="message.confirmation.removeCandidacy" bundle="JOB_BANK_RESOURCES" />')</bean:define>

<logic:equal name="jobOffer" property="canRemoveOfferCandidacy" value="true">
	<span class="valid">
		<html:link action="/student.do?method=removeJobOfferCandidancy" paramId="OID" paramName="process" paramProperty="externalId" onclick="<%= removeConfirmationMessage %>">
			<bean:message key="link.jobBank.removeCandidancy" bundle="JOB_BANK_RESOURCES"/>
		</html:link>
	</span>
</logic:equal>


<!-- CANDIDACY INFORMATION -->


<logic:equal name="jobOffer" property="hasCandidacyForThisUser" value="true">
	
	<p>
	<h3 class="separator"><bean:message key="label.enterprise.candidacy.information" bundle="JOB_BANK_RESOURCES"/></h3>
	<p>
	<div class="infobox mvert1">
		<b>
			<bean:message key="label.enterprise.jobOffer.candidancyType" bundle="JOB_BANK_RESOURCES"/>:
		</b>
			<bean:write name="jobOffer" property="candidacyType.localizedName" bundle="JOB_BANK_RESOURCES"/></p>
		
		<logic:equal name="jobOffer" property="externalCandidacy" value="false">
			<p><b><bean:message key="lable.submited.files" bundle="JOB_BANK_RESOURCES"/></b></p>
			
			<logic:present name="jobOffer" property="studentFilesForJobOfferCandidacy"> 
				<logic:iterate id="file" name="jobOffer" property="studentFilesForJobOfferCandidacy">
					<bean:define id="fileExternalId" name="file" property="externalId"/>
					<li style="padding-left: 30px;">
						<bean:write name="file" property="displayName" />   
						<html:link action='<%="/student.do?method=downloadFile&amp;fileId="+fileExternalId %>'>
							<bean:define id="filename" name="file" property="filename" />
							<bean:message key="link.file.download" bundle="JOB_BANK_RESOURCES"/> 		
						</html:link> 
					</li>
				</logic:iterate>
			</logic:present>
		</logic:equal>
		
		<logic:equal name="jobOffer" property="externalCandidacy" value="true">
			<bean:define id="externalLink" name="jobOffer" property="jobOfferExternal.externalLink"/>
			<p><bean:message bundle="JOB_BANK_RESOURCES" key="lable.external.candidacy.information" arg0="<%= externalLink.toString() %>" /></p>
		</logic:equal>
		
		<p></p>
	</div>
</logic:equal>



<!-- OFFER INFORMATION -->


<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffer.information"/>
</h3>
<div class="infobox mvert1">
	<fr:view name="jobOffer">
		<fr:schema type="module.jobBank.domain.JobOffer" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="enterprise.name" key="label.enterprise.name" layout="link">
				<fr:property name="useParent" value="true"/>
				<fr:property name="linkFormat" value="/student.do?method=viewEnterprise&OID=${enterprise.externalId}" />
			</fr:slot>			
			<fr:slot name="jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification"/>
			<fr:slot name="externalCandidacy" key="label.enterprise.jobOffer.candidacyType.externalCandidacy"/>
			<fr:slot name="creationDate" key="label.enterprise.offer.creationDate"/>
			<fr:slot name="reference" key="label.enterprise.jobOffer.reference"/>
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





