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
<bean:define id="processOID" name="process" property="externalId"/>


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


<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.contacts"/>
</h3>


<logic:present name="sortedContacts">
	<logic:notEmpty name="sortedContacts">
	<br>
	<table class="tstyle3">
		<tr>
			<th/>
			<th/>
			<%-- Print the visibility group name --%>
			<logic:iterate id="visibilityGroup" name="visibilityGroups">
			<%-- Print something depending on the existence of a group alias or not--%>
				<th>
				<logic:empty name="visibilityGroup" property="groupAlias">
					<bean:write name="visibilityGroup" property="name"/>
				</logic:empty>
				<logic:notEmpty name="visibilityGroup" property="groupAlias">
					<logic:notEmpty name="visibilityGroup" property="groupAlias.groupAlias">
						<bean:write name="visibilityGroup" property="groupAlias.groupAlias"/>
					</logic:notEmpty>
					<logic:empty name="visibilityGroup" property="groupAlias.groupAlias">
						<bean:write name="visibilityGroup" property="name"/>
					</logic:empty>
				</logic:notEmpty>
				</th>
			</logic:iterate>
		</tr>
		<logic:iterate id="sortedContact" name="sortedContacts">
		<logic:present name="sortedContact">
			<tr>
			<%-- Use the class to write the bean:message --%>
				<bean:define id="keyDependingOnClass" name="sortedContact" property="class.name"/>
				<td><bean:message key="<%=keyDependingOnClass.toString()%>" bundle="CONTACTS_RESOURCES"/> (<bean:write name="sortedContact" property="type.localizedName"/>)</td>
				<%-- showing of the contact information --%>
				<td><bean:write name="sortedContact" property="description"/></td>
				<%-- mark all visibilities with the given image or - case they aren't visible--%>
						<logic:iterate id="visibilityGroup" name="visibilityGroups">
						<td class="acenter">
						<%if (((module.contacts.domain.PartyContact)sortedContact).isVisibleTo((myorg.domain.groups.PersistentGroup)visibilityGroup)) {%>
	                		<img src="<%= request.getContextPath() + "/contacts/image/accept.gif" %>"/>
						<%}else {%>
							-
						<%}%>
						</td>
						</logic:iterate>
						<td class="tdclear">
						<html:link action="<%= "/contacts.do?method=editPartyContact&OID=" + processOID + "&forwardToAction=" + "backOffice.do" + "&forwardToMethod=Enterprise"%>" paramId="partyContactOid" paramName="sortedContact" paramProperty="externalId">
							<bean:message bundle="CONTACTS_RESOURCES" key="manage.contacts.edit.label"/>
						</html:link>,
						<html:link action="<%= "/contacts.do?method=deletePartyContact&OID=" + processOID + "&forwardToAction=" + "backOffice.do" + "&forwardToMethod=Enterprise"%>" paramId="partyContactOid" paramName="sortedContact" paramProperty="externalId">
							<bean:message bundle="CONTACTS_RESOURCES" key="manage.contacts.remove.label"/>
						</html:link>
						</td>
		</logic:present>
		</logic:iterate>
	</table>
	</logic:notEmpty>
</logic:present>


<logic:empty name="sortedContacts">
<br>
<bean:message key="edit.person.information.and.contacts.no.available.contacts" bundle="CONTACTS_RESOURCES"/>
<br>
</logic:empty>

<html:link action="<%= "/contacts.do?method=createPartyContact&OID=" + processOID + "&forwardToAction=" + "backOffice.do" + "&forwardToMethod=Enterprise" %>" paramId="personOid" paramName="enterprise" paramProperty="unit.oid">
<br/>
	<p><bean:message bundle="CONTACTS_RESOURCES" key="manage.contacts.addContact.label"/></p>
</html:link>



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
	
