<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.createEnterprise"/></h2>
<logic:present name="enterpriseBean"> 
	<logic:notPresent name="enterpriseBean" property="emailValidation">
		<bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.emailValidation"/>  
		<fr:edit id="enterpriseBean"  action="/enterprise.do?method=createEmailValidation"  name="enterpriseBean">
			<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">
				<fr:slot name="loginEmail" key="label.enterprise.loginEmail">
				 	<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				 	<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.EmailValidator"/>
				 	<fr:validator name="module.jobBank.presentationTier.validators.EmailNotDefinedValidator"/>
				 </fr:slot>
			</fr:schema>
			<fr:destination name="cancel" path="/jobBank.do?method=frontPage" />
		</fr:edit>
	</logic:notPresent>
	
	<logic:present name="enterpriseBean" property="emailValidation">
		<logic:notPresent name="invalidRegistration">
			<bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.emailValidation.valid"/>
		</logic:notPresent>
	</logic:present>
	
</logic:present>

<logic:notPresent name="enterpriseBean">
	<logic:equal name="invalidRegistration" value="true">
		<bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.emailValidation.invalid"/>
	</logic:equal>
</logic:notPresent>	



