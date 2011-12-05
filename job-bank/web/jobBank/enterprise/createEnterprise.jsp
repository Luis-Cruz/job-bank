<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.createEnterprise"/></h2>


<p><bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.createEnterprise"/></p>


<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>



<fr:form action="/enterprise.do?method=createEnterprise" encoding="multipart/form-data"> 
	
	<h3><bean:message key="label.enterprise.createEnterprise.dataAccess" bundle="JOB_BANK_RESOURCES"/></h3> 
	<fr:edit id="enterpriseBean" name="enterpriseBean" >
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">
			<fr:slot name="password" key="label.enterprise.password" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>
			<fr:slot name="repeatPassword" key="label.enterprise.repeatPassword" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/>
			<fr:property name="columnClasses" value=",,tderror"/>
			<fr:property name="requiredMarkShown" value="true" />
			<fr:property name="requiredMessageShown" value="false" />
		</fr:layout>
	</fr:edit>
	
	<h3><bean:message key="label.enterprise.createEnterprise.enterprise" bundle="JOB_BANK_RESOURCES"/></h3>
	<fr:edit id="enterpriseBean2" name="enterpriseBean" >
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">	
		<fr:slot name="logoInputStream" key="label.enterprise.logo" bundle="JOB_BANK_RESOURCES">
			<fr:property name="fileNameSlot" value="logoFilename" />
			<fr:property name="size" value="30"/>
		</fr:slot>
		<fr:slot name="name" key="label.enterprise.name">
			<fr:validator name="module.jobBank.presentationTier.validators.EnterpriseNameNotRegisteredValidator"/>
			<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
			<fr:property name="size" value="50" />
		</fr:slot>
		<fr:slot name="contactPerson" key="label.enterprise.contactPerson" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"> 
			<fr:property name="size" value="50" />
		</fr:slot>
	 	<fr:slot name="nif" key="label.enterprise.nif" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>  	
		<fr:slot name="designation" key="label.enterprise.designation" layout="autoComplete"
				validator="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredAutoCompleteSelectionValidator">
       		<fr:property name="labelField" value="description"/>
			<fr:property name="format" value="${code} - ${description} <span style='color:gray'>(${group.code} - ${group.description})</span>"/>
			<fr:property name="minChars" value="2"/>
			<fr:property name="args" value="provider=module.jobBank.presentationTier.providers.EconomicActivityClassificationLeafProvider"/>
			<fr:property name="size" value="60"/>
		</fr:slot>
		<fr:slot name="summary" key="label.enterprise.summary"  layout="area" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
			<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
		</fr:slot>
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/>
			<fr:property name="columnClasses" value=",,tderror"/>
			<fr:property name="requiredMarkShown" value="true" />
			<fr:property name="requiredMessageShown" value="false" />
		</fr:layout>
	</fr:edit>
	
	<h3><bean:message key="label.enterprise.createEnterprise.enterpriseContacts" bundle="JOB_BANK_RESOURCES"/> </h3> 
	<fr:edit id="enterpriseBean3" name="enterpriseBean" >
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">
		<fr:slot name="privateContactEmail" key="label.enterprise.privateContactEmail">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.EmailValidator"/>  
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:property name="size" value="50" />
		</fr:slot>
		<fr:slot name="publicContactEmail" key="label.enterprise.publicContactEmail" validator="pt.ist.fenixWebFramework.rendererExtensions.validators.NotRequiredEmailValidator">
				<fr:property name="size" value="50" />
		</fr:slot>
		<fr:slot name="phone" key="label.enterprise.phone"/>
		<fr:slot name="mobilePhone" key="label.enterprise.mobilePhone"/>
		<fr:slot name="webAddress" key="label.enterprise.url"/>  	
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/>
			<fr:property name="columnClasses" value=",,tderror"/>
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	
	<p>
		<html:submit styleClass="inputbutton">
			<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
		</html:submit>
	</p>
</fr:form>



