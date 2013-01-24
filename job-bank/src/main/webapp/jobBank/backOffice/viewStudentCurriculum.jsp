<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.student"/> </h2> 

<p class="mtop30px"><strong><bean:write name="student" property="name"/></strong></p>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<table>
	<tr>
		<td style="padding-right: 20px;">
			<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="student" property="user.username"/></bean:define>
			<img src="<%= urlPhoto %>">
		</td>
		<td style="vertical-align: top;">
			<fr:view name="student" property="curriculum">
				<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.Curriculum">
					<fr:slot name="student.person.user.username" key="label.enterprise.username"/>
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
					<fr:property name="classes" value="tview-horizontal mtop0"/>
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


<h3>
	<bean:message key="label.enterprise.jobOffers" bundle="JOB_BANK_RESOURCES"/>
</h3>

<logic:notEmpty name="offerCandidacies">

	<bean:message  key="message.backoffice.curriculum.list.offersCandidacies" bundle="JOB_BANK_RESOURCES"/>   
	
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

</logic:notEmpty>

<logic:empty name="offerCandidacies">
	<p><em><bean:message  key="message.curriculum.list.offersCandidacies.npe.empty" bundle="JOB_BANK_RESOURCES" /></em></p>
</logic:empty>
