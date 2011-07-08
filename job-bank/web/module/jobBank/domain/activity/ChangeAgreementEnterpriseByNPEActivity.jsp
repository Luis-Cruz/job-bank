<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>
<p> 
	Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis.
</p>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

		
<logic:equal name="process" property="enterprise.changeToRequestAgreement" value="true">
	<div class="success1">
		<span>
			<bean:define id="agreement" name="process" property="enterprise.agreementNameForApproval"/> 
		    <bean:message  key="message.enterprise.changeAgreement" bundle="JOB_BANK_RESOURCES" arg0="<%= agreement.toString() %>"/> 
		</span>
	</div>
</logic:equal>		

<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processOID %>'>
	<fr:edit id="activityBean" name="information"> 					
	<fr:schema type="module.jobBank.domain.activity.EnterpriseContractInformation"  bundle="JOB_BANK_RESOURCES">
		
		<fr:slot name="enterpriseBean.notActiveAccountabilityType" key="label.enterprise.newAccountabilityType">  
			<fr:property name="excludedValues" value="PENDING" />
			<fr:property name="defaultOptionHidden" value="true"/> 
			<fr:property name="readOnly" value="true"/>
		</fr:slot>
		
		<fr:slot name="enterpriseBean.message" key="label.enterprise.rejectMessage" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"
			layout="longText"> 
			<fr:property name="columns" value="60" /> 
			<fr:property name="rows" value="6" /> 
		</fr:slot>
		
		<bean:message  key="message.enterprise.agreement.duration" bundle="JOB_BANK_RESOURCES"/>
		
	</fr:schema>
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
				<fr:form action='<%="/backOffice.do?method=Enterprise&OID="+processOID %>'>
					<html:submit styleClass="inputbutton"><bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/></html:submit>
				</fr:form>
			</td>
		</th> 
	</tr>
</table> 

	



