<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

 
<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacy"/> </h2> 

<bean:define id="student" name="offercandidacy" property="student"/>
(<bean:write name="student" property="name"/>)


<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>


<fr:view name="student" property="curriculum">
	<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.Curriculum">
		<fr:slot name="student.number" key="label.curriculum.id"/>
		<fr:slot name="email" key="label.curriculum.email"/>
		<%-- 		<fr:slot name="student.degree.presentationName" key="label.curriculum.degree"/>  --%>
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
		<fr:slot name="professionalStatus" key="label.curriculum.professionalStatus" layout="area">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
		</fr:slot>
		<fr:slot name="geographicAvailability" key="label.curriculum.geographicAvailability"/>
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="infobox3 mbottom5px"/>
	</fr:layout>
</fr:view>

<logic:equal name="enterprise" property="jobProviderWithPrivilegesAgreement" value="true">
<fr:view name="student" property="activeStudentRegistrationSet">
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
	<bean:message key="message.enterprise.no.have.permissions" bundle="JOB_BANK_RESOURCES"/>
</logic:equal>

<logic:present name="offercandidacy" property="processFiles"> 
	<logic:iterate id="file" name="offercandidacy" property="processFiles">
		<tr>
			<bean:define id="fileExternalId" name="file" property="externalId"/>
			<bean:define id="processExternalId" name="student" property="curriculum.curriculumProcess.externalId"/>
			<td>
				
				<html:link action='<%="/workflowProcessManagement.do?method=downloadFile&amp;fileId="+fileExternalId+"&amp;processId="+processExternalId %>'>
					<bean:define id="filename" name="file" property="filename" />
					<bean:write name="file" property="displayName" /> - <%= filename.toString() %>   		
				</html:link> 
			</td>
		</tr>
	</logic:iterate>
	</p>
</logic:present>

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

