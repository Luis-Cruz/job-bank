<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.enterprise"/>
</h2>

<h3>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.information"/>
</h3>

<div>
	<bean:define id="enterpriseId" name="enterprise" property="externalId"/>
	<logic:present name="enterprise" property="logo">
		<html:img action="<%= "/student.do?method=viewEnterpriseLogo&enterpriseId="+ enterpriseId %>" style="width: 120px; height: 120px;"/>
	</logic:present>
</div>

<fr:view name="enterprise">
	<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.Enterprise">
		<fr:slot name="name" key="label.enterprise.name"/>
		<fr:slot name="designation.description" key="label.enterprise.designation"/> 
		<fr:slot name="summary" key="label.enterprise.summary"/>
	</fr:schema>
	<fr:layout name="tabular" >
		<fr:property name="classes" value="tview-horizontal"/>
	</fr:layout>
</fr:view>





<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.contacts"/>
</h3>



<bean:define id ="sortedContacts" name="enterprise" property="sortedContacts"/>
	
<logic:notEmpty name="sortedContacts">
<table class="tstyle3">
	<logic:iterate id="sortedContact" name="sortedContacts">
	<logic:present name="sortedContact">
		<tr>
		<%-- Use the class to write the bean:message --%>
			<bean:define id="keyDependingOnClass" name="sortedContact" property="class.name"/>
			<td><bean:message key="<%=keyDependingOnClass.toString()%>" bundle="CONTACTS_RESOURCES"/> (<bean:write name="sortedContact" property="type.localizedName"/>)</td>
			<%-- showing of the contact information --%>
			<td><bean:write name="sortedContact" property="description"/></td>
	</logic:present>
	</logic:iterate>
</table>
</logic:notEmpty>


<logic:empty name="sortedContacts">
	<br>
	<bean:message key="edit.person.information.and.contacts.no.available.contacts" bundle="CONTACTS_RESOURCES"/>
</logic:empty>


<h3>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffers"/>
</h3>

<fr:view name="enterprise" property="publicationsJobOffers" >
<slot name="jobOffer.jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification" />
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOffer">
			<fr:slot name="enterprise.name" key="label.enterprise.name" />
			<fr:slot name="function" key="label.enterprise.jobOffer.function" />
			<fr:slot name="place" key="label.enterprise.jobOffer.place" />
			<fr:slot name="presentationPeriod" key="label.enterprise.jobOffer.candidacyPeriod" />
			<fr:slot name="vacancies" key="label.enterprise.jobOffer.vacancies" />
			<fr:slot name="state" key="label.enterprise.jobOffer.state" />
			<fr:slot name="externalCandidacy" key="label.enterprise.jobOffer.candidacyType.externalCandidacy" />
			
			<fr:layout name="tabular" >
				<fr:property name="classes" value="tview-vertical"/>
				<fr:property name="link(view)" value="/jobBank.do?method=viewJobOffer" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="jobOfferProcess.externalId/OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="order(view)" value="1" />
<%-- 				<fr:property name="sortBy" value="jobOfferProcess.processIdentification=asc" /> --%>
			</fr:layout>
		</fr:schema>
		
</fr:view>

<logic:empty name="enterprise" property="publicationsJobOffers" >
 	<bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.active.offers"/>
</logic:empty>



