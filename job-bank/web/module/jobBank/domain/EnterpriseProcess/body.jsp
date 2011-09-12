<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>


<bean:define id="enterprise" name="process" property="enterprise"/>
<bean:define id="jobOfferProcesses" name="process" property="enterprise.jobOfferProcesses"/>
<bean:define id="enterpriseId" name="enterprise" property="externalId"/>


<logic:equal name="process" property="isEnterpriseMember" value="true">
	
	<logic:equal name="process" property="enterprise.pendingToApproval" value="true">
		<div class="warning1">
			<bean:message key="message.enterprise.pendingToApproval" bundle="JOB_BANK_RESOURCES"/>
		</div>
	</logic:equal>

	<logic:equal name="process" property="enterprise.pendingToApproval" value="false">
		<logic:present name="process" property="enterprise.agreementForApproval">
			<div class="warning1">
				<bean:message key="message.enterprise.agreement.change.request.made" bundle="JOB_BANK_RESOURCES"/>
			</div>
		</logic:present>
	</logic:equal>
	
	<logic:equal name="process" property="enterprise.canceled" value="true">
		<div class="warning1">
			<bean:message key="message.enterprise.is.canceled" bundle="JOB_BANK_RESOURCES"/>
		</div>
	</logic:equal>
	
</logic:equal>


<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.information"/>
</h3>



<logic:present name="enterprise" property="logo">
		<html:img action="<%= "/enterprise.do?method=viewlogo&enterpriseId="+ enterpriseId %>" style="width: 120px; height: 120px;"/>
</logic:present>

<div class="infobox">
	<fr:view name="enterprise" schema="jobBank.enterprise.enterpriseProcess.view">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tview-horizontal"/>
		</fr:layout>
	</fr:view>
</div>



<logic:equal name="process" property="enterprise.pendingToApproval" value="false">

	<logic:equal name="process" property="enterprise.canceled" value="false">

		<h3><bean:message bundle="JOB_BANK_RESOURCES" key="message.enteprise.agreement"/></h3> 
		
		<p><bean:message bundle="JOB_BANK_RESOURCES" key="message.enteprise.agreement.information"/></p>
						
		<table class="tview-horizontal"> 
			<tr> 
				<th><bean:message key="label.enterprise.status" bundle="JOB_BANK_RESOURCES"/>:</th> 
				<td><bean:write name="enterprise" property="agreementName"/></td> 
			</tr> 
			<tr> 
				<th><bean:message key="label.enterprise.agreement.duration" bundle="JOB_BANK_RESOURCES"/>:</th> 
				<td><bean:write name="enterprise" property="activeAccountability.beginDate"/> <bean:message key="label.to" bundle="JOB_BANK_RESOURCES"/> <bean:write name="enterprise" property="activeAccountability.endDate"/></td> 
			</tr> 
		</table> 
	
	</logic:equal>

</logic:equal>
	
