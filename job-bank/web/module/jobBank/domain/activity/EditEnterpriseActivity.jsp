<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="enterpriseId" name="process" property="enterprise.externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>


<logic:present name="information" property="enterpriseBean">
	<logic:present name="information" property="enterpriseBean.logo">
		<html:img action="<%= "/enterprise.do?method=viewlogo&enterpriseId="+ enterpriseId  %>"/>
	</logic:present >	
</logic:present>

<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processOID %>'>	
	<fr:edit id="activityBean" name="information" property="enterpriseBean"  schema="jobBank.enterprise.registration.edit"/>
		<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="label.jobBank.submit"/>
	</html:submit>
</fr:form>