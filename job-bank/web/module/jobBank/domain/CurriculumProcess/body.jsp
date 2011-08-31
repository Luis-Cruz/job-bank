<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>


<%@page import="module.jobBank.domain.OfferCandidacy"%>
<bean:define id="student" name="process" property="curriculum.student"/>
<bean:define id="person" name="student" property="person"/>
<bean:define id="activeOfferCandidacies" name="student" property="activeOfferCandidacies"></bean:define>

<h3>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.curriculum.personal.information"/>
</h3>

<div class="infobox" style="padding: 10px;">
	<table>
		<tr>
			<td style="padding-right: 20px;">
				<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="person" property="user.username"/></bean:define>
				<img src="<%= urlPhoto %>">
			</td>
			<td style="vertical-align: top;">
				<fr:view name="student">
					<fr:schema type="module.jobBank.domain.Student" bundle="JOB_BANK_RESOURCES">
						<fr:slot name="person.name" key="label.curriculum.name"/>
						<fr:slot name="person.user.username" key="label.enterprise.username"/>
						<fr:slot name="curriculum.email" key="label.curriculum.email"/>
						<fr:slot name="curriculum.dateOfBirth" key="label.curriculum.dateOfBirth"/> 
						<fr:slot name="curriculum.nationality" key="label.curriculum.nationality"/> 
						<fr:slot name="curriculum.address" key="label.curriculum.address"/> 
						<fr:slot name="curriculum.area" key="label.curriculum.area"/> 
						<fr:slot name="curriculum.areaCode" key="label.curriculum.areaCode"/> 
						<fr:slot name="curriculum.districtSubdivision" key="label.curriculum.districtSubdivision"/> 
						<fr:slot name="curriculum.mobilePhone" key="label.curriculum.mobilePhone"/> 
						<fr:slot name="curriculum.phone" key="label.curriculum.phone"/>
					</fr:schema>
					<fr:layout name="tabular">
						<fr:property name="classes" value="tview-horizontal mvert0"/>
					</fr:layout>
				</fr:view>
			</td>
		</tr>
	</table>
</div>


<h3><bean:message bundle="JOB_BANK_RESOURCES" key="label.registrations"/></h3>

<fr:view name="student" property="allStudentRegistrationSet">
	<fr:schema type="module.jobBank.domain.StudentRegistration" bundle="JOB_BANK_RESOURCES">
		<fr:slot name="fenixDegree.name" key="label.curriculumQualification.degree"/>
		<fr:slot name="cycleType" key="label.curriculumQualification.cycle"/>
		<fr:slot name="average" key="label.curriculum.average" layout="null-as-label"/>   
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="tview-vertical thleft tdleft"/>
		<fr:property name="columnClasses" value="aleft,,"/>
	</fr:layout>
</fr:view>
	
						
<h3><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacies"/></h3>

<logic:present name ="activeOfferCandidacies">
	<fr:view name="activeOfferCandidacies">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tview-vertical"/>
			<fr:property name="link(view)" value="/student.do?method=viewJobOffer" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="jobOffer.jobOfferProcess.externalId/OID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="order(view)" value="1" />
			<fr:property name="sortBy" value="creationDate=asc" />
		</fr:layout>
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
			<fr:slot name="jobOffer.jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification" />
			<fr:slot name="jobOffer.enterprise.name" key="label.enterprise.name" />
			<fr:slot name="jobOffer.function" key="label.enterprise.jobOffer.function" />
			<fr:slot name="jobOffer.presentationPeriod" key="label.enterprise.jobOffer.candidacyPeriod" />
			<fr:slot name="jobOffer.state" key="label.enterprise.offer.state" />
		</fr:schema>
	</fr:view>
</logic:present>


<logic:empty name="activeOfferCandidacies">
	<p><em><bean:message bundle="JOB_BANK_RESOURCES" key="message.curriculum.there.are.no.candidacies"/>.</em></p>
</logic:empty> 


