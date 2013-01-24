<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="link.sideBar.jobBank.studentAuthorizations"/></h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<fr:edit id="studentAuthorizationBean2" name="studentAuthorizationBean" action="/backOffice.do?method=createStudentAuthorization">
	<fr:schema type="module.jobBank.domain.beans.StudentAuthorizationBean" bundle="JOB_BANK_RESOURCES">
		<fr:slot name="username" key="label.manager.person.username" readOnly="true">
			<fr:property name="size" value="20"/>
		</fr:slot>
		<fr:slot name="beginDate" key="label.beginDate" readOnly="true"/>
		<fr:slot name="endDate" key="label.endDate" layout="picker">
			<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.FutureLocalDateValidator"/>
		</fr:slot>
		<fr:destination name="invalid" path="/backOffice.do?method=prepareCreateStudentAuthorization"/>	
	</fr:schema>
	<fr:layout name="tabular">
		<fr:property name="classes" value="form" />
		<fr:property name="columnClasses" value=",,tderror" />
	</fr:layout>
</fr:edit>
