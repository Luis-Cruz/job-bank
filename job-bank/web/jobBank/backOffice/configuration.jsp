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


<logic:present name="beanUrlEmailValidation" > 
	<bean:message key="label.backOffice.urlEmailValidation.note" bundle="JOB_BANK_RESOURCES"/>
	<fr:edit id="beanUrlEmailValidation" name="beanUrlEmailValidation" action="/backOffice.do?method=updateUrlEmailValidation"> 
		<fr:schema bundle="JOB_BANK_RESOURCES" type="myorg.util.VariantBean">
	 		<fr:slot name="string" key="label.backOffice.urlEmailValidation" layout="longText">
	 			<fr:property name="columns" value="60" />
	 			<fr:property name="rows" value="5" />
	 		</fr:slot>
	 	</fr:schema>
	</fr:edit>
</logic:present>


<fr:edit id="jobBankSystem" name="jobBankSystem" schema="jobBank.emailValidationFrom.configuration">
</fr:edit>