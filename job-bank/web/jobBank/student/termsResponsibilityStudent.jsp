<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES"
	key="title.jobBank.termsResponsibility" /></h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1"><html:messages id="errorMessage"
		property="message" message="true">
		<span><fr:view name="errorMessage" /></span>
	</html:messages></div>
</logic:messagesPresent>

<h3><bean:message bundle="JOB_BANK_RESOURCES"
	key="title.jobBank.student" /></h3>

<logic:equal name="student" property="hasPersonalDataAuthorization" value="false">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.personalDataAuthorizationChoice.notAuthorized" />
</logic:equal>


<logic:equal name="student" property="hasPersonalDataAuthorization" value="true">
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.termsResponsibility.student.text"/>
	
	<p><a href="">Download (.pdf)</a></p>

	<fr:form action="/student.do?method=acceptResponsibilityTerms">
		<html:submit styleClass="inputbutton">
			<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.acceptTerms" />
		</html:submit>
	</fr:form>
</logic:equal>




