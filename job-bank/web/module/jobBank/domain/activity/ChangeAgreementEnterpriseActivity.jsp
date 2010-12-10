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
			Foi submetida uma alteração de contrato para: <bean:write name="process" property="enterprise.agreementNameForApproval"/> 1 ano. Esta alteração encontra-se pendente de aprovação por parte do NPE.
		</span>
	</div>
</logic:equal>		
		
<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processOID %>'>
	<fr:edit id="activityBean" name="information"> 					
	<fr:schema type="module.jobBank.domain.activity.EnterpriseContractInformation"  bundle="JOB_BANK_RESOURCES">
		
		<fr:slot name="enterpriseBean.jobBankAccountabilityType" key="label.enterprise.requestAccountabilityType">  
			<fr:property name="excludedValues" value="PENDING" />
			<fr:property name="defaultOptionHidden" value="true"/> 
		</fr:slot>
		Duracção: 	1 Ano
	</fr:schema>
</fr:edit>
<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="label.jobBank.submit"/>
    </html:submit>
</fr:form>

	



