<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="pt.utl.ist.fenix.tools.util.i18n.Language"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.termsResponsibility" /></h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true">
			<span><fr:view name="errorMessage" /></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<h3><bean:message bundle="JOB_BANK_RESOURCES"
	key="title.jobBank.student" /></h3>

<logic:equal name="student" property="hasPersonalDataAuthorization" value="false">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.personalDataAuthorizationChoice.notAuthorized" />
</logic:equal>


<logic:equal name="student" property="hasPersonalDataAuthorization" value="true">
	
	<div>
		<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.termsResponsibility.student.text"/>
	</div>
	
	<p>
		<a href="<%= request.getContextPath() + "/jobBank/TermsAndConditions_Students_" + Language.getLocale().getLanguage() + ".pdf" %>">
			<bean:message key="message.termsResponsibilityEnterprise.download" bundle="JOB_BANK_RESOURCES"/>
		</a>
	</p>

	<fr:form action="/student.do?method=acceptResponsibilityTerms">
		<html:submit styleClass="inputbutton">
			<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.acceptTerms" />
		</html:submit>
	</fr:form>
</logic:equal>




