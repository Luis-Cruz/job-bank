<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.enterprise"/>
</h2>

<html:messages id="message" message="true" property="error"
	bundle="JOB_BANK_RESOURCES">
	<span class="error"> <bean:write name="message" /> </span>
	<br />
</html:messages>


	
<fr:form action="/jobBank.do?method=frontPage">
	<fr:view  name="enterprise" schema="jobBank.enterprise.registration.view"/>
</fr:form>

<div class="warning1"><bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.registration.pending"/></div> 


