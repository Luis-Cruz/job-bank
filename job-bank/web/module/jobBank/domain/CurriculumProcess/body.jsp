<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<%@page import="module.organization.domain.OrganizationalModel"%>
<%@page import="myorg.domain.MyOrg"%>


<%@page import="module.jobBank.domain.CandidateOffer"%>
<bean:define id="student" name="process" property="curriculum.student"/>
<bean:define id="person" name="student" property="person"/>
<bean:define id="candidatesOffersActive" name="student" property="activeCandidateOffers"></bean:define>
<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.curriculum.personal.information"/>
</h3>

<div class="infobox mvert1">
	<table>
		<tr>
			<td valign="middle" style="padding: 10px">
				<bean:define id="urlPhoto" type="java.lang.String">https://fenix.ist.utl.pt/publico/retrievePersonalPhoto.do?method=retrieveByUUID&amp;contentContextPath_PATH=/homepage&amp;uuid=<bean:write name="person" property="user.username"/></bean:define>
				<img src="<%= urlPhoto %>">
			</td>
			<td valign="top" style="padding: 10px">
				<table width="100%">
					<tr>
						<fr:view name="student">
							<fr:schema type="module.jobBank.domain.Student" bundle="JOB_BANK_RESOURCES">
								<fr:slot name="person.name" key="label.curriculum.name"/>
								<fr:slot name="curriculum.email" key="label.curriculum.email"/>
								<fr:slot name="curriculum.dateOfBirth" key="label.curriculum.dateOfBirth"/>
								<fr:slot name="curriculum.nationality" key="label.curriculum.nationality"/>
								<fr:slot name="curriculum.address" key="label.curriculum.address"/>
								<fr:slot name="curriculum.mobilePhone" key="label.curriculum.mobilePhone"/>
							</fr:schema>
						</fr:view>
					</tr>
					<tr style="border: none;">
						<td style="border: none;">
							<br/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>


<h3 class="separator">
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.candidacies"/>
</h3>

<logic:present name ="candidatesOffersActive">
	<fr:view name="candidatesOffersActive">
		<fr:layout name="tabular">
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(view)" value="/student.do?method=viewJobOffer" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="jobOffer.jobOfferProcess.externalId/OID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="order(view)" value="1" />
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(delete)" value="/student.do?method=removeCandidancy" />
			<fr:property name="key(delete)" value="link.jobBank.removeCandidancy" />
			<fr:property name="param(delete)" value="OID" />
			<fr:property name="bundle(delete)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(delete)" value="jobOffer.candidancyPeriod" />
			<fr:property name="order(delete)" value="2" />
	
			
		</fr:layout>
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
			<fr:slot name="jobOffer.jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification" />
			<fr:slot name="jobOffer.place" key="label.enterprise.jobOffer.place" />
			<fr:slot name="jobOffer.beginDate" key="label.enterprise.offer.beginDate" />
			<fr:slot name="jobOffer.endDate" key="label.enterprise.offer.endDate" />
			<fr:slot name="jobOffer.state" key="label.enterprise.offer.state" />
		</fr:schema>
		
	</fr:view>
	
</logic:present>
<logic:empty name="candidatesOffersActive">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.curriculum.there.are.no.candidacies"/>
</logic:empty> 


