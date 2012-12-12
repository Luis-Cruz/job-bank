<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.enterprise"/>
</h2>
<bean:define id="enterprise" name="enterprise"/>
<bean:define id="enterpriseOID" name="enterprise" property="externalId"/>

<p>
	<html:link action="<%="/enterprise.do?method=editEnterprise&enterpriseOID"+enterpriseOID%>">
		<bean:message key="link.jobBank.editEnterprise" bundle="JOB_BANK_RESOURCES"/>
	</html:link>
</p>

<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.jobBank.contact"/>
</h2>

<bean:message bundle="JOB_BANK_RESOURCES" name="enterprise" property="contract"/>

<html:link action="<%="/enterprise.do?method=editEnterprise&enterpriseOID"+enterpriseOID%>">
			<bean:message key="link.jobBank.editEnterprise" bundle="JOB_BANK_RESOURCES"/>
</html:link>

