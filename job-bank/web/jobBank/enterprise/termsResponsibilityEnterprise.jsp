<%@page import="pt.utl.ist.fenix.tools.util.i18n.Language"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.termsResponsibility"/>
</h2>

<h3 style="margin-top: 30px;">
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.enterprise"/>
</h3>
	
<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.termsResponsibility.text"/>
	
<p>
	<a href="<%= request.getContextPath() + "/jobBank/TermsAndConditions_Businesses_" + Language.getLocale().getLanguage() + ".pdf" %>">
		<bean:message key="message.termsResponsibilityEnterprise.download" bundle="JOB_BANK_RESOURCES"/>
	</a>
</p>
 
<fr:form action="/enterprise.do?method=prepareToCreateEmailValidation">
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.acceptTerms"/>
	</html:submit>
</fr:form> 
