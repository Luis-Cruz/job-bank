<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>


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
			
			<p class="mbottom5px">Por favor, introduza o endereço de email com que deseja registar a empresa.</p>
			
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
					<fr:layout name="tabular">
						<fr:property name="columnClasses" value=",,tderror" />
					</fr:layout>				
				</fr:edit>
				<html:submit styleClass="inputbutton">
					<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
				</html:submit>
			</fr:form>
		</div>
	</logic:notPresent>
<%--
	<logic:notEmpty name="enterpriseBean" property="emailValidation">
		<p><bean:write name="enterpriseBean" property="emailValidation.body"/></p>
	</logic:notEmpty>
--%>
</logic:present>

