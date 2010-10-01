<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.backOffice.viewEnterprises"/>
</h2>

<p> 
<html:link action="/backOffice.do?method=viewActiveEnterprises">
			<bean:message key="link.backOffice.viewActiveEnterprises" bundle="JOB_BANK_RESOURCES"/>
</html:link>
<p> 
<html:link action="/backOffice.do?method=viewRequestsToChangeEnterprises">
			<bean:message key="link.backOffice.viewRequestsForChangeEnterpries" bundle="JOB_BANK_RESOURCES"/>
</html:link>
</p>

<h3>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.backOffice.pending"/>
</h3>

	
<fr:form>
	<fr:view name="pendingEnterprises" schema="jobBank.enterprise.view.pending">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			
			<fr:property name="link(view)" value="/backOffice.do?method=Enterprise"/>
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES"/>
			<fr:property name="key(view)" value="link.jobBank.view"/>
			<fr:property name="param(view)" value="enterpriseProcess.externalId/OID"/>
			<fr:property name="order(view)" value="1"/>
			
			
		</fr:layout>
	</fr:view>		
</fr:form> 

<logic:empty name="pendingEnterprises">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.backOffice.no.have.pending.entreprises.to.approve"/>
 </logic:empty>
