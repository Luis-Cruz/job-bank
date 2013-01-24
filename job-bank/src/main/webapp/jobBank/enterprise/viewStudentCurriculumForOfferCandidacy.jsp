<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

 <%@page import="module.jobBank.domain.JobBankSystem"%>
 
<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacy"/></h2> 

<bean:define id="student" name="offercandidacy" property="student"/>

<p>
	<bean:message key="label.enterprise.jobOffer" bundle="JOB_BANK_RESOURCES"/>
	<html:link action="/jobBank.do?method=viewJobOffer" paramId="OID" paramName="jobOffer" paramProperty="jobOfferProcess.oid">
		<bean:write name="jobOffer" property="jobOfferProcess.processIdentification"/>
	</html:link>
</p>

<p><b><bean:write name="student" property="name"/></b> (<bean:write name="student" property="curriculum.email"/>)</p>


<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<logic:equal name="enterprise" property="jobProviderWithPrivilegesAgreement" value="true">

	<table>
		<tr>
			<td style="padding-right: 20px;">
				<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="student" property="user.username"/></bean:define>
				<img src="<%= urlPhoto %>">
			</td>
			<td style="vertical-align: top;">
				<fr:view name="student" property="curriculum">
					<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.Curriculum">
						<%if (JobBankSystem.getInstance().isNPEMember()) { %>
							<fr:slot name="student.person.user.username" key="label.enterprise.username"/>
						<% } %>
						<fr:slot name="email" key="label.curriculum.email"/>
						<fr:slot name="dateOfBirth" key="label.curriculum.dateOfBirth"/>
						<fr:slot name="nationality" key="label.curriculum.nationality"/>
						<fr:slot name="address" key="label.curriculum.address"/>
						<fr:slot name="area" key="label.curriculum.area"/>
						<fr:slot name="areaCode" key="label.curriculum.areaCode"/>
						<fr:slot name="districtSubdivision" key="label.curriculum.districtSubdivision">
							<fr:property name="nullOptionHidden" value="true"/>
						</fr:slot>
						<fr:slot name="mobilePhone" key="label.curriculum.mobilePhone"/>
						<fr:slot name="phone" key="label.curriculum.phone"/>  
					</fr:schema>
					<fr:layout name="tabular">
						<fr:property name="classes" value="tview-horizontal"/>
					</fr:layout>
				</fr:view>
			</td>
		</tr>
	</table>

	<fr:view name="student" property="studentRegistrationCycleTypes">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.StudentRegistration">
			<fr:slot name="studentRegistration.fenixDegree.name" key="label.curriculum.degree"/>
			<fr:slot name="cycleType" key="label.curriculumQualification.cycle"/>
			<fr:slot name="average" key="label.curriculum.average"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tview-vertical thleft tdleft mtop20px"/>
		</fr:layout>
	</fr:view>
</logic:equal>

<logic:equal name="enterprise" property="jobProviderWithPrivilegesAgreement" value="false">
	<p><bean:message key="message.enterprise.no.have.permissions.to.see.student.curriculum" bundle="JOB_BANK_RESOURCES"/></p>
</logic:equal>


<logic:present name="offercandidacy" property="processFiles"> 
	<logic:notEqual name="offercandidacy" property="processFilesCount" value="0"> 

<%--

			<p><b><bean:message key="lable.submited.files.by.student" bundle="JOB_BANK_RESOURCES"/></b></p>
			
			<ul style="padding-left: 20px;">
				<logic:iterate id="file" name="offercandidacy" property="processFiles">
					<bean:define id="fileExternalId" name="file" property="externalId"/>
					<bean:define id="processExternalId" name="student" property="curriculum.curriculumProcess.externalId"/>
					<li>
						<bean:write name="file" property="presentationName" />   
						<html:link action='<%="/workflowProcessManagement.do?method=downloadFile&amp;fileId="+fileExternalId+"&amp;processId="+processExternalId %>'>
							<bean:define id="filename" name="file" property="filename" />
							<bean:message key="link.file.download" bundle="JOB_BANK_RESOURCES"/><br> 		
						</html:link> 
					</li>
				</logic:iterate>
			</ul>

--%>


	<div class="infobox mtop15px">
		<p><bean:message key="lable.submited.files.by.student" bundle="JOB_BANK_RESOURCES"/></p>
		<ul style="padding-left: 20px;">
			<logic:iterate id="file" name="offercandidacy" property="processFiles">
				<bean:define id="fileExternalId" name="file" property="externalId"/>
				<bean:define id="processExternalId" name="student" property="curriculum.curriculumProcess.externalId"/>
				<li>
					<html:link action='<%="/workflowProcessManagement.do?method=downloadFile&amp;fileId="+fileExternalId+"&amp;processId="+processExternalId %>'>
						<bean:define id="filename" name="file" property="filename" />
						<bean:write name="file" property="presentationName" />
						<%--<bean:message key="link.file.download" bundle="JOB_BANK_RESOURCES"/>--%>		
					</html:link> 
				</li>
			</logic:iterate>
		</ul>
		<p class="mtop15px"><em><bean:message key="warning.submited.files.by.student" bundle="JOB_BANK_RESOURCES"/></em></p>
	</div>
		
		
	</logic:notEqual>	
</logic:present>


<h3 class="separator">
	<bean:message key="label.enterprise.other.jobOffers" bundle="JOB_BANK_RESOURCES"/>
</h3>

<p class="mbottom5px"> 
	<bean:define id="enterpriseName" name="enterprise" property="name"/>
	<bean:message  key="message.curriculum.list.offersCandidacies" bundle="JOB_BANK_RESOURCES" arg0="<%=enterpriseName.toString() %>"/>   
</p>

<fr:view name="offerCandidacies" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tview-vertical" />
		<fr:property name="link(view)" value="/workflowProcessManagement.do?method=viewProcess" />
		<fr:property name="key(view)" value="link.jobBank.view" />
		<fr:property name="param(view)" value="jobOffer.jobOfferProcess.externalId/processId" />
		<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
		<fr:property name="order(view)" value="1" />
	</fr:layout>
</fr:view>

