<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.backOffice.viewEnterprises"/>
</h2>

<bean:define id="state" name="enterpriseState" property="enterpriseStateType.type"/>

<p> 
<fr:form  action="/backOffice.do?method=enterprises" >
	<fr:edit id="enterpriseState" name="enterpriseState" type="module.jobBank.domain.beans.EnterpriseBean">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.EnterpriseBean" >
			<fr:slot name="enterpriseStateType" key="label.enterpriseStateType.enterprises" />
		</fr:schema>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.search"/>
	</html:submit>
</fr:form>
	
<fr:form>
	<fr:view name="enterprises" schema="<%="jobBank.enterprise.view."+state%>">
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

<logic:empty name="enterprises">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.search.no.results.were.found"/>
 </logic:empty>
