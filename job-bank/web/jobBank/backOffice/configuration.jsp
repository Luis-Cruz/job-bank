<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.configuration"/></h2>


<logic:notPresent name="jobBankSystem" property="organizationalModel">
	<p>
		<bean:message key="label.configuration.model.jobBank.is.not.defined" bundle="JOB_BANK_RESOURCES"/>
		<br/>
		<html:link action="/backOffice.do?method=prepareSelectOrganizationalModel">
			<bean:message key="label.configuration.select.organizationalModel" bundle="JOB_BANK_RESOURCES"/>
		</html:link>
	</p>
</logic:notPresent>




