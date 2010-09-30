<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.offers"/></h2>


<p> 
<html:link action="/enterprise.do?method=prepareToCreateOffer" >
		<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.createOffer" />
</html:link>
</p>

<logic:present name="processes">
	<fr:view name="processes">
		<fr:layout name="tabular">
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(view)" value="/jobBank.do?method=viewJobOfferProcessToManage" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="order(view)" value="1" />
			
	
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(candidates)" value="/enterprise.do?method=viewCandidatesToJobOffer" />
			<fr:property name="key(candidates)" value="link.jobBank.candidates" />
			<fr:property name="param(candidates)" value="OID" />
			<fr:property name="bundle(candidates)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(candidates)" value="jobOffer.afterCompletedCandidancyPeriod" />
			<fr:property name="order(candidates)" value="2" />

		</fr:layout>
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
			<fr:slot name="jobOffer.jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification" />
			<fr:slot name="jobOffer.place" key="label.enterprise.jobOffer.place" />
			<fr:slot name="jobOffer.endDate" key="label.enterprise.offer.endDate" />
		</fr:schema>
	</fr:view>
</logic:present>

<logic:empty name="processes">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.offers"/>
</logic:empty>



