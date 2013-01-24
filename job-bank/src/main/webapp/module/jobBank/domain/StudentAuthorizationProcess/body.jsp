<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>


<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.jobBank.studentAuthorization"/>
</h3>

<bean:define id="studentAuthorization" name="process" property="studentAuthorization"/>

<div class="infobox mvert1">
	<fr:view name="studentAuthorization">
		<fr:schema type="module.jobBank.domain.StudentAuthorization" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="studentAuthorizationProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification"/>
			<fr:slot name="student.person.user.username" key="label.manager.person.username"/>
			<fr:slot name="student.person.name" key="label.manager.person.name"/>
			<fr:slot name="beginDate" key="label.beginDate"/>
			<fr:slot name="endDate" key="label.endDate"/>
			<fr:layout name="tabular-nonNullValues">
				<fr:property name="classes" value="tview-horizontal"/>
			</fr:layout>
		</fr:schema>
	</fr:view>
</div>

<h3>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.curriculum.personal.information"/>
</h3>

<div class="infobox" style="padding: 10px;">
	<table>
		<tr>
			<td style="padding-right: 20px;">
				<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="studentAuthorization" property="student.person.user.username"/></bean:define>
				<img src="<%= urlPhoto %>">
			</td>
			<td style="vertical-align: top;">
				<fr:view name="studentAuthorization" property="student">
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

<fr:view name="studentAuthorization" property="student.studentRegistrationCycleTypes">
	<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.StudentRegistration">
		<fr:slot name="studentRegistration.fenixDegree.name" key="label.curriculum.degree"/>
		<fr:slot name="cycleType" key="label.curriculumQualification.cycle"/>
		<fr:slot name="average" key="label.curriculum.average"/>
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="tview-vertical thleft tdleft mtop20px"/>
	</fr:layout>
</fr:view>

<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.jobBank.allStudentAuthorizations"/>
</h3>

<fr:view name="studentAuthorization" property="student.studentAuthorizationSet">
	<fr:schema type="module.jobBank.domain.StudentAuthorization" bundle="JOB_BANK_RESOURCES">
		<fr:slot name="studentAuthorizationProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification"/>
		<fr:slot name="student.person.user.username" key="label.manager.person.username"/>
		<fr:slot name="student.person.name" key="label.manager.person.name"/>
		<fr:slot name="beginDate" key="label.beginDate"/>
		<fr:slot name="endDate" key="label.endDate"/>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="sortBy" value="beginDate=asc"/>
		</fr:layout>
	</fr:schema>
</fr:view>