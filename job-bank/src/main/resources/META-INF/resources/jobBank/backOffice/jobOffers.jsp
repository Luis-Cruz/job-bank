<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/collection-pager" prefix="cp"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.offers"/></h2>

<fr:form action="/backOffice.do?method=jobOffers">
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.jobBank.domain.beans.SearchOfferState" bundle="JOB_BANK_RESOURCES">

			<fr:slot name="enterprise" key="label.enterprise.name">
				<fr:property name="size" value="40"/>
			</fr:slot>
			<fr:slot name="processNumber" key="label.enterprise.jobOfferProcess.processIdentification">
				<fr:property name="size" value="20"/>
			</fr:slot>
			<fr:slot name="jobOfferState" key="label.jobOfferSearch.state" required="true">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
			<fr:slot name="jobOfferType" key="label.enterprise.jobOffer.jobType" required="true">
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
	
	<logic:equal name="offerSearch" property="processesCount" value="0">
		<p><em><bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffers.empty"/>.</em></p>
	</logic:equal>
	
	<logic:notEqual name="offerSearch" property="processesCount" value="0">	
		<logic:notEqual name="numberOfPages" value="1">
			<bean:define id="params">&amp;offerState=<logic:present name="offerSearch" property="jobOfferState"><bean:write name="offerSearch" property="jobOfferState"/></logic:present>&amp;processNumber=<logic:present name="offerSearch" property="processNumber"><bean:write name="offerSearch" property="processNumber"/></logic:present>&amp;enterprise=<logic:present name="offerSearch" property="enterprise"><bean:write name="offerSearch" property="enterprise"/></logic:present>&amp;offerType=<logic:present name="offerSearch" property="jobOfferType"><bean:write name="offerSearch" property="jobOfferType"/></logic:present></bean:define>
			<cp:collectionPages url="<%= "/backOffice.do?method=jobOffers" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>
		
		<fr:view name="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer">
			<fr:layout name="tabular" >
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
				<fr:property name="link(view)" value="/jobBank.do?method=viewJobOffer" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="order(view)" value="1" />
			</fr:layout>
		</fr:view>
	</logic:notEqual>
</logic:present>


