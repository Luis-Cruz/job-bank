<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/collection-pager" prefix="cp"%>

<bean:size id="resultSize" name="studentAuthorizationBean" property="result"/>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<fr:form action="/backOffice.do">
	<fr:edit id="studentAuthorizationBean" name="studentAuthorizationBean">
		<input type="hidden" name="method"/>
		<fr:schema type="module.jobBank.domain.beans.StudentAuthorizationBean" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="username" key="label.manager.person.username">
				<fr:property name="size" value="20"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
	</fr:edit>
	<html:submit styleClass="inputbutton" onclick="this.form.method.value='studentAuthorizations'">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.search"/>
	</html:submit>
	<logic:equal name="studentAuthorizationBean" property="isStudent" value="true">
		<logic:equal name="resultSize" value="0">
			<html:submit styleClass="inputbutton" onclick="this.form.method.value='prepareCreateStudentAuthorization'">
				<bean:message  bundle="JOB_BANK_RESOURCES" key="link.create"/>
			</html:submit>
		</logic:equal>
	</logic:equal>
</fr:form>

<br/><br/>



<logic:equal name="resultSize" value="0">
	<logic:notEmpty name="studentAuthorizationBean" property="username">
		<logic:equal name="studentAuthorizationBean" property="isStudent" value="true">
			<h2><bean:message bundle="JOB_BANK_RESOURCES" key="link.sideBar.jobBank.studentAuthorizations"/></h2>
			<bean:message bundle="JOB_BANK_RESOURCES" key="message.studentAuthorization.studentWithoutAuthorizations"/>
		</logic:equal>
	</logic:notEmpty>
	<logic:empty name="studentAuthorizationBean" property="username">
		<h2><bean:message bundle="JOB_BANK_RESOURCES" key="link.sideBar.jobBank.studentAuthorizations"/></h2>
		<bean:message bundle="JOB_BANK_RESOURCES" key="message.studentAuthorization.noActiveAuthorizations"/>
	</logic:empty>
</logic:equal>
<logic:notEqual name="resultSize" value="0">
	<h2><bean:message bundle="JOB_BANK_RESOURCES" key="link.sideBar.jobBank.studentAuthorizations"/></h2>
	<fr:view name="studentAuthorizationBean" property="result">
		<fr:schema type="module.jobBank.domain.StudentAuthorization" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="student.person.user.username" key="label.manager.person.username"/>
			<fr:slot name="student.person.name" key="label.manager.person.name"/>
			<fr:slot name="beginDate" key="label.beginDate"/>
			<fr:slot name="endDate" key="label.endDate"/>
			<fr:slot name="student.hasPersonalDataAuthorization" key="label.jobbank.dataAuthorization"/>
			<fr:slot name="student.studentRegistrationCount" key="label.registrations"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(view)" value="/backOffice.do?method=viewStudentAuthorization" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="OID/studentAuthorizationOID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(view)" value="isActive" />
		</fr:layout>
	</fr:view>
</logic:notEqual>

