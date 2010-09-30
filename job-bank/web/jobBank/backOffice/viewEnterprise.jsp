<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.enterprise"/>
</h2>

<html:link action="/enterprise.do?method=viewEnterprises">
			<bean:message key="link.jobBank.back" bundle="JOB_BANK_RESOURCES"/>
</html:link>
<fr:view name="enterprise"  schema="jobBank.enterprise.registration">
</fr:view>




