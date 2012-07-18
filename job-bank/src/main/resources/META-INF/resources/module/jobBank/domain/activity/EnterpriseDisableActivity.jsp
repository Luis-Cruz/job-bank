<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>


<p>
	<bean:message key="message.enterprise.disable.information" bundle="JOB_BANK_RESOURCES"/>
</p>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<div class="forminline">

<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processOID %>'>
	<fr:edit id="activityBean" name="information"> 					
	<fr:schema type="module.jobBank.domain.activity.EnterpriseEnableOrDisableInformation"  bundle="JOB_BANK_RESOURCES">
		
		<fr:slot name="option" key="label.enterprise.operation">  
			<fr:property name="defaultOptionHidden" value="true"/> 
			<fr:property name="readOnly" value="true"/>
		</fr:slot>
		
		<fr:slot name="message" key="label.enterprise.rejectMessage" required="true"
			layout="longText"> 
			<fr:property name="columns" value="70" /> 
			<fr:property name="rows" value="8" /> 
		</fr:slot>

	</fr:schema>
</fr:edit>


	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
	</html:submit>
	
</fr:form>

<fr:form action='<%="/backOffice.do?method=Enterprise&OID="+processOID %>'>
	<html:submit styleClass="inputbutton cancel"><bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/></html:submit>
</fr:form>

</div>
