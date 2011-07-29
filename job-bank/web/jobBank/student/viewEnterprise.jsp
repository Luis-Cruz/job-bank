<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.enterprise"/>
</h2>

<h3>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.information"/>
</h3>

<div style="float: left;">
	<fr:view name="enterprise"  schema="jobBank.enterprise.enterpriseProcess.view"/>
</div>

<div style="float: right;padding-right: 15em;">
	<bean:define id="enterpriseId" name="enterprise" property="externalId"/>
	<logic:present name="enterprise" property="logo">
			<html:img action="<%= "/student.do?method=viewEnterpriseLogo&enterpriseId="+ enterpriseId %>" style="width: 120px; height: 120px;"/>
	</logic:present>
</div>

<div style="clear:both;"></div>

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
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
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



