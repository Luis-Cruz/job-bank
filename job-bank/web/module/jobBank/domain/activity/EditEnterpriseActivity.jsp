<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<fr:form action="/enterprise.do?method=processEditEnterprise" encoding="multipart/form-data" >	 
	<html:hidden property="activity" value="<%=activityName.toString()%>"/>
	<html:hidden property="processId" value="<%=processOID.toString()%>"/>
	
	<h3><bean:message key="label.enterprise.createEnterprise.dataAccess" bundle="JOB_BANK_RESOURCES"/></h3> 
	
	<fr:edit id="activityBean" name="information">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.EnterpriseInformation">
			<fr:slot name="enterpriseBean.password" key="label.enterprise.password" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>  
			<fr:slot name="enterpriseBean.repeatPassword" key="label.enterprise.repeatPassword" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	<h3><bean:message key="label.enterprise.createEnterprise.enterprise" bundle="JOB_BANK_RESOURCES"/></h3>
	
	<fr:edit id="activityBean2" name="information" >
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.EnterpriseInformation">	
		<fr:slot name="enterpriseBean.logoInputStream" key="label.enterprise.logo" bundle="JOB_BANK_RESOURCES">
			<fr:property name="fileNameSlot" value="enterpriseBean.logoFilename" />
			<fr:property name="size" value="30"/>
		</fr:slot>
		
		<fr:slot name="enterpriseBean.name" key="label.enterprise.name">
			<fr:validator name="module.jobBank.presentationTier.validators.EnterpriseNameNotRegisteredValidator"/>
			<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			<fr:property name="size" value="50" />
		</fr:slot>
		
		<fr:slot name="enterpriseBean.contactPerson" key="label.enterprise.contactPerson"> 
			<fr:property name="size" value="50" />
		 </fr:slot>
		 <fr:slot name="enterpriseBean.nif" key="label.enterprise.nif" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>  	
		  
		<fr:slot name="enterpriseBean.designation" key="label.enterprise.designation" layout="area">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
			<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
		</fr:slot>
		<fr:slot name="enterpriseBean.summary" key="label.enterprise.summary"  layout="area">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
			<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
		</fr:slot>
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>
	<h3><bean:message key="label.enterprise.createEnterprise.enterpriseContacts" bundle="JOB_BANK_RESOURCES"/> </h3> 
	<fr:edit id="activityBean3" name="information">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.EnterpriseInformation">
		<fr:slot name="enterpriseBean.contactEmail" key="label.enterprise.contactEmail">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.EmailValidator"/>  
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:property name="size" value="50" />
		</fr:slot>
		<fr:slot name="enterpriseBean.area" key="label.enterprise.area"/>  
		<fr:slot name="enterpriseBean.areaCode" key="label.enterprise.areaCode"/> 
		<fr:slot name="enterpriseBean.phone" key="label.enterprise.phone"/> 
		<fr:slot name="enterpriseBean.fax" key="label.enterprise.fax"/>
		<fr:slot name="enterpriseBean.url" key="label.enterprise.url"/>  	
	</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
	</fr:edit>	
	
	
<table class="tstyle0">
	<tr>
		<th>
			<td>
				<html:submit styleClass="inputbutton">
					<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
				</html:submit>
				</fr:form>
			</td>
		</th>
		<th>
			<td>
				<fr:form action="/enterprise.do?method=enterprise">
					<html:submit styleClass="inputbutton"><bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/></html:submit>
				</fr:form>
			</td>
		</th> 
	</tr>
</table> 


