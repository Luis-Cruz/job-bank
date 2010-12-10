<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>

<bean:define id="enterprise" name="process" property="enterprise"/>
<bean:define id="jobOfferProcesses" name="process" property="enterprise.jobOfferProcesses"/>
<bean:define id="enterpriseId" name="enterprise" property="externalId"/>

<logic:equal name="process" property="enterprise.pendingToApproval" value="true">
	<div class="warning1">
		O registo está pendente para aprovação. Nesta fase apenas pode...
	</div>
</logic:equal>

<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.information"/>
</h3>



<logic:present name="enterprise" property="logo">
		<html:img action="<%= "/enterprise.do?method=viewlogo&enterpriseId="+ enterpriseId %>"/>
</logic:present>

<div class="infobox mvert1">
	<fr:view name="enterprise" schema="jobBank.enterprise.enterpriseProcess.view">
		<fr:layout name="tabular">
			<fr:property name="classes" value="infobox3 mbottom5px"/>
		</fr:layout>
	</fr:view>
</div>




	
<h3><bean:message bundle="JOB_BANK_RESOURCES" key="message.enteprise.agreement"/></h3> 
		
<p>Aenean eu leo leo. Donec sem lorem, commodo vel blandit at, placerat aliquet metus. In hac habitasse platea dictumst. Donec molestie fermentum metus, non ullamcorper libero venenatis vitae. Maecenas ut libero nunc, eget malesuada est.</p> 
				
<table class="tview3"> 
	<tr> 
		<th>Tipo de contrato:</th> 
		<td>
			<logic:equal name="enterprise" property="expired" value="true">
				<bean:message bundle="JOB_BANK_RESOURCES" key="message.enterprise.agreement.expired"/>
			</logic:equal>	
			<logic:equal name="enterprise" property="expired" value="false">
				<bean:write name="enterprise" property="agreementName"/>
			</logic:equal>	</td> 
	</tr> 
	<tr> 
		<th>Duração:</th> 
		<td><bean:write name="enterprise" property="agreementDuration"/></td> 
	</tr> 
</table> 
				

<h3> 
	<p> 
		<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffers"/>
	</p>
</h3>
<logic:present name="jobOfferProcesses">
	<fr:view name="jobOfferProcesses" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer.enterprise">
		<fr:layout name="tabular" >
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(view)" value="/jobBank.do?method=viewJobOffer" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="order(view)" value="1" />
		</fr:layout>
	</fr:view>
</logic:present>

<logic:empty name="jobOfferProcesses">
	<bean:message key="message.jobBank.not.have.offers"  bundle="JOB_BANK_RESOURCES"/>

</logic:empty>



