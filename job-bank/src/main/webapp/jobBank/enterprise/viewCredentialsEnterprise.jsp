<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>


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


