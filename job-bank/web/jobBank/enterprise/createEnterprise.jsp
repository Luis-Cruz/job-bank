<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.createEnterprise"/></h2>
<p class="mbottom20px">Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis.</p> 
<fr:form action="/enterprise.do?method=createEnterprise" encoding="multipart/form-data"> 
	<h3><bean:message key="label.enterprise.createEnterprise.dataAccess" bundle="JOB_BANK_RESOURCES"/></h3> 
	<fr:edit id="enterpriseBean" name="enterpriseBean" >
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">
			<fr:slot name="password" key="label.enterprise.password" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>
			<fr:slot name="repeatPassword" key="label.enterprise.repeatPassword" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
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
		
		<fr:slot name="contactPerson" key="label.enterprise.contactPerson"> 
			<fr:property name="size" value="50" />
		 </fr:slot>
		 <fr:slot name="nif" key="label.enterprise.nif" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>  	
		  
		<fr:slot name="designation" key="label.enterprise.designation" layout="area">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
		</fr:slot>
		<fr:slot name="summary" key="label.enterprise.summary"  layout="area">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
		</fr:slot>
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	<h3><bean:message key="label.enterprise.createEnterprise.enterpriseContacts" bundle="JOB_BANK_RESOURCES"/> </h3> 
	<fr:edit id="enterpriseBean3" name="enterpriseBean" >
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean">
		<fr:slot name="contactEmail" key="label.enterprise.contactEmail">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.EmailValidator"/>  
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:property name="size" value="50" />
		</fr:slot>
		<fr:slot name="area" key="label.enterprise.area"/>  
		<fr:slot name="areaCode" key="label.enterprise.areaCode"/> 
		<fr:slot name="phone" key="label.enterprise.phone"/> 
		<fr:slot name="fax" key="label.enterprise.fax"/>
		<fr:slot name="url" key="label.enterprise.url"/>  	
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
	</html:submit>
</fr:form>



