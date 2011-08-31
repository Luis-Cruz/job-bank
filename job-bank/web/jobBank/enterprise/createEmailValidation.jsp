<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.createEnterprise"/></h2>

<logic:messagesPresent property="error" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="error" message="true" bundle="JOB_BANK_RESOURCES"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<logic:messagesPresent property="message" message="true">
	<div class="warning1">
		<html:messages id="errorMessage" property="message" message="true" bundle="JOB_BANK_RESOURCES"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>


<logic:present name="enterpriseBean">
	<logic:notPresent name="enterpriseBean" property="emailValidation">
		<div class="forminline">
			<p class="mtop30px mbottom5px"><strong><bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.emailValidation"/></strong></p>
			 <fr:form action="/enterprise.do?method=createEmailValidation" >
		
				<fr:edit id="enterpriseBean" name="enterpriseBean">
					<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">
						<fr:slot name="loginEmail" key="label.enterprise.loginEmail">
						 	<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
						 	<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.EmailValidator"/>
						 	<fr:validator name="module.jobBank.presentationTier.validators.EmailNotDefinedValidator"/>
						 	<fr:property name="size" value="40"/>
						 </fr:slot>
					</fr:schema>				
				</fr:edit>
				<html:submit styleClass="inputbutton">
					<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
				</html:submit>
			</fr:form>
		
			<fr:form  action="/jobBank.do?method=frontPage" >
				<html:submit styleClass="inputbutton cancel">
					<bean:message bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/>
				</html:submit>
			</fr:form>
		</div>
	</logic:notPresent>

	<logic:notEmpty name="enterpriseBean" property="emailValidation">
		<p><bean:write name="enterpriseBean" property="emailValidation.body"/></p>
	</logic:notEmpty>
</logic:present>

