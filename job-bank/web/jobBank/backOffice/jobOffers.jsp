<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.offers"/></h2>


<fr:form action="/backOffice.do?method=jobOffers">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.jobBank.domain.beans.SearchOffer" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="processNumber" key="label.enterprise.jobOfferProcess.processIdentification">
				<fr:property name="size" value="10"/>
			</fr:slot>
			<fr:slot name="offerSearchState" key="label.jobOfferSearch.state" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
		<fr:destination name="postback" path="/backOffice.do?method=jobOffers"/>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.search"/>
	</html:submit>
</fr:form>

<logic:present name="processes">
	<fr:view name="processes">
		<fr:layout name="tabular">
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			
			<fr:property name="link(view)" value="/jobBank.do?method=viewJobOfferProcessToManage" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="visibleIf(view)" value="canViewJobProcess" />
			<fr:property name="order(view)" value="1" />
			
			
			<fr:property name="sortBy" value="processIdentification"/>

		</fr:layout>
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
			<fr:slot name="jobOffer.jobOfferProcess.processIdentification" key="label.enterprise.jobOfferProcess.processIdentification" />
			<fr:slot name="jobOffer.place" key="label.enterprise.jobOffer.place" />
			<fr:slot name="jobOffer.beginDate" key="label.enterprise.offer.beginDate" />
			<fr:slot name="jobOffer.endDate" key="label.enterprise.offer.endDate" />
		</fr:schema>
	</fr:view>
</logic:present>


<logic:empty name="processes">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.offers"/>
</logic:empty> 



