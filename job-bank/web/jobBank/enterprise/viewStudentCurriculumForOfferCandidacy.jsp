<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

 <%@page import="module.jobBank.domain.JobBankSystem"%>
 
<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacy"/> </h2> 

<div class="infobox mvert1">
	<bean:define id="student" name="offercandidacy" property="student"/>
	(<b><bean:write name="student" property="name"/></b>)
	<br><br>
	
	<logic:messagesPresent property="message" message="true">
		<div class="error1">
			<html:messages id="errorMessage" property="message" message="true"> 
				<span><fr:view name="errorMessage"/></span>
			</html:messages>
		</div>
	</logic:messagesPresent>
	
	<logic:equal name="enterprise" property="jobProviderWithPrivilegesAgreement" value="true">
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
				<fr:property name="classes" value="infobox3 mbottom5px"/>
			</fr:layout>
		</fr:view>
		
<fr:view name="student" property="studentRegistrationSet">
			<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.StudentRegistration">
				<fr:slot name="fenixDegree.name" key="label.curriculum.degree"/>
				<fr:slot name="average" key="label.curriculum.average"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="infobox3 mbottom5px"/>
			</fr:layout>
		</fr:view>
	</logic:equal>
	
	<logic:equal name="enterprise" property="jobProviderWithPrivilegesAgreement" value="false">
		<br><bean:message key="message.enterprise.no.have.permissions.to.see.student.curriculum" bundle="JOB_BANK_RESOURCES"/><br><br>
	</logic:equal>
	
	<logic:present name="offercandidacy" property="processFiles"> 
		<logic:notEqual name="offercandidacy" property="processFilesCount" value="0"> 
			<br>
			<p><b><bean:message key="lable.submited.files.by.student" bundle="JOB_BANK_RESOURCES"/></b></p>
			
			<logic:iterate id="file" name="offercandidacy" property="processFiles">
				<bean:define id="fileExternalId" name="file" property="externalId"/>
				<bean:define id="processExternalId" name="student" property="curriculum.curriculumProcess.externalId"/>
				<li style="padding-left: 30px;">
					<bean:write name="file" property="displayName" />   
					<html:link action='<%="/workflowProcessManagement.do?method=downloadFile&amp;fileId="+fileExternalId+"&amp;processId="+processExternalId %>'>
						<bean:define id="filename" name="file" property="filename" />
						<bean:message key="link.file.download" bundle="JOB_BANK_RESOURCES"/><br> 		
					</html:link> 
				</li>
			</logic:iterate>
			
			<p><em><font size="1.5"><bean:message key="warning.submited.files.by.student" bundle="JOB_BANK_RESOURCES"/></font></em></p>
		</logic:notEqual>	
	</logic:present>
				
</div>

<h3>
	<bean:message key="label.enterprise.jobOffers" bundle="JOB_BANK_RESOURCES"/>
</h3>
<p> 
<bean:define id="enterpriseName" name="enterprise" property="name"/>
<bean:message  key="message.curriculum.list.offersCandidacies" bundle="JOB_BANK_RESOURCES" arg0="<%=enterpriseName.toString() %>"/>   

</p>
<fr:view name="offerCandidacies" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer">
	
	<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits" />
	
			<fr:property name="link(view)"
					value="/workflowProcessManagement.do?method=viewProcess" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="jobOffer.jobOfferProcess.externalId/processId" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="order(view)" value="1" />
	</fr:layout>
</fr:view>

