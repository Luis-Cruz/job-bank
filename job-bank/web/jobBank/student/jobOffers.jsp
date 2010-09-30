<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>


<bean:size id="processesSize" name="processes" />

<fr:form action="/student.do?method=searchOffers">
	<fr:edit id="search" name="search"
		type="module.jobBank.domain.beans.Search">
		<fr:schema bundle="JOB_BANK_RESOURCES"
			type="module.jobBank.domain.beans.Search">
			<fr:slot name="reference"
				key="label.enterprise.jobOfferProcess.processIdentification" />
			<fr:slot name="enterprise" key="label.enterprise.name" />
			<fr:slot name="function" key="label.enterprise.jobOffer.function" />
			<fr:slot name="creationDate"
				key="label.enterprise.offer.creationDate" layout="picker" />
			<fr:slot name="jobOfferType" key="label.enterprise.jobOffer.jobType">
				<fr:property name="defaultText" value="label.jobOffer.jobType.all" />
				<fr:property name="defaultTextBundle" value="JOB_BANK_RESOURCES" />
				<fr:property name="key" value="true" />
			</fr:slot>

		</fr:schema>
		<fr:destination name="postback" path="/student.do?method=search" />
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.search" />
	</html:submit>



	<logic:present name="processes">
		<fr:view name="processes">
			<fr:layout name="tabular">

				<fr:property name="classes"
					value="tstyle3 mvert1 width100pc tdmiddle punits" />

				<fr:property name="link(view)"
					value="/student.do?method=viewJobOffer" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="visibleIf(view)" value="canViewJobProcess" />
				<fr:property name="order(view)" value="1" />


				<fr:property name="classes"
					value="tstyle3 mvert1 width100pc tdmiddle punits" />
				<fr:property name="link(candidate)"
					value="/student.do?method=candidateToJobOffer" />
				<fr:property name="key(candidate)" value="link.jobOffer.candidate" />
				<fr:property name="param(candidate)" value="OID" />
				<fr:property name="bundle(candidate)" value="JOB_BANK_RESOURCES" />
				<fr:property name="visibleIf(candidate)"
					value="jobOffer.canCreateCandidateOffer" />
				<fr:property name="order(candidate)" value="2" />



			</fr:layout>
			<fr:schema bundle="JOB_BANK_RESOURCES"
				type="module.jobBank.domain.JobOfferProcess">
				<fr:slot name="jobOffer.jobOfferProcess.processIdentification"
					key="label.enterprise.jobOfferProcess.processIdentification" />
				<fr:slot name="jobOffer.place" key="label.enterprise.jobOffer.place" />
				<fr:slot name="jobOffer.beginDate"
					key="label.enterprise.offer.beginDate" />
				<fr:slot name="jobOffer.endDate"
					key="label.enterprise.offer.endDate" />
			</fr:schema>
		</fr:view>
	</logic:present>


</fr:form>
<logic:equal name="processesSize" value="0">
	<bean:message bundle="JOB_BANK_RESOURCES"
		key="message.search.no.results.were.found" />
</logic:equal>

