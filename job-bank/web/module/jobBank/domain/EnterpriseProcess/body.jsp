<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>

<bean:define id="enterprise" name="process" property="enterprise"/>
<bean:define id="enterpriseId" name="enterprise" property="externalId"/>

<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.information"/>
</h3>

<logic:present name="enterprise" property="logo">
		<html:img action="<%= "/enterprise.do?method=viewlogo&enterpriseId="+ enterpriseId %>"/>
</logic:present>

<div class="infobox mvert1">
	<fr:view name="enterprise" schema="jobBank.enterprise.enterpriseProcess.view">
	</fr:view>
</div>

<logic:equal name="process" property="accessible" value="true">
	<p> 
		<bean:message bundle="JOB_BANK_RESOURCES" key="message.enteprise.agreement"/>
		<p>
			<bean:write name="enterprise" property="agreement"/></p>
		</p>
	</p>
</logic:equal>


