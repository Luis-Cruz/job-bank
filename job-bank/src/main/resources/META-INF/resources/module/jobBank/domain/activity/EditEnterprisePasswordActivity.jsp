<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>

<div class="forminline">

<fr:form action="/enterprise.do?method=processEditEnterprise" encoding="multipart/form-data" >	 
	<html:hidden property="activity" value="<%=activityName.toString()%>"/>
	<html:hidden property="processId" value="<%=processOID.toString()%>"/>
	
	<h3 class="mtop30px"><bean:message key="label.enterprise.createEnterprise.dataAccess" bundle="JOB_BANK_RESOURCES"/></h3> 
	
	<fr:edit id="activityBean" name="information">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.EnterpriseInformation">
			<fr:slot name="enterpriseBean.password" key="label.enterprise.password" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="password"/>  
			<fr:slot name="enterpriseBean.repeatPassword" key="label.enterprise.repeatPassword" layout="password"/>		
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="thwidth150px"/> 
			<fr:property name="requiredMarkShown" value="true" />
			<fr:property name="requiredMessageShown" value="false" />
		</fr:layout>
	</fr:edit>
	
	<logic:equal name="information" property="requestOldPassword" value="true">
		<fr:edit id="activityBean3" name="information">
			<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.EnterpriseInformation">
				
					<fr:slot name="oldPassword" key="label.enterprise.old.password" layout="password" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>  
					
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="thwidth150px"/> 
				<fr:property name="requiredMarkShown" value="true" />
				<fr:property name="requiredMessageShown" value="false" />
			</fr:layout>
		</fr:edit>
	</logic:equal>
	

<html:submit styleClass="inputbutton">
	<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
</html:submit>
</fr:form>

<fr:form action="/enterprise.do?method=enterprise">
	<html:submit styleClass="inputbutton cancel"><bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/></html:submit>
</fr:form>

</div>

