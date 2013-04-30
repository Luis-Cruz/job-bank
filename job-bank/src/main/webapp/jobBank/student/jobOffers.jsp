<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/collection-pager" prefix="cp"%>

<%@page import="pt.ist.bennu.core.util.BundleUtil"%>
<%@page import="module.jobBank.domain.JobBankSystem"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffers"/></h2>

<fr:form action="/student.do?method=searchOffers">
	<fr:edit id="search" name="search">
		<fr:schema bundle="JOB_BANK_RESOURCES"
			type="module.jobBank.domain.beans.SearchOffer">
			<fr:slot name="query" key="label.enterprise.jobOffer.query">
				<fr:property name="size" value="40" />
			</fr:slot>
			<fr:slot name="degrees" key="label.enterprise.offer.degree" layout="menu-select">
				<fr:property name="defaultText" value="<%= BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.degree.all") %>"/>
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.ActiveFenixDegreesProvider" />
				<fr:property name="eachLayout" value="values"/>
				<fr:property name="saveOptions" value="true"/>
				<fr:property name="sortBy" value="name=asc"/>
				<fr:property name="eachSchema" value="jobBank.enterprise.jobOffer.fenixDegree" />
			</fr:slot>
			<fr:slot name="jobOfferType" key="label.enterprise.jobOffer.jobType">
				<fr:property name="excludedValues" value="ALL" />
				<fr:property name="defaultText" value="label.jobOffer.jobType.all" />
				<fr:property name="defaultTextBundle" value="JOB_BANK_RESOURCES" />
				<fr:property name="key" value="true" />
			</fr:slot>
		</fr:schema>
		
		<fr:destination name="postback" path="/student.do?method=searchOffers" />
	</fr:edit>
	
	<p>
		<html:submit styleClass="inputbutton">
			<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.search" />
		</html:submit>
	</p>


	<logic:present name="processes">
	
		<bean:size id="processesSize" name="processes" />
		<logic:equal name="processesSize" value="0">
			<p><em><bean:message bundle="JOB_BANK_RESOURCES" key="message.search.no.results.were.found" />.</em></p>
		</logic:equal>
	
		<logic:notEqual name="numberOfPages" value="1">
			<bean:define id="params">&amp;query=<logic:present name="search" property="query"><bean:write name="search" property="query"/></logic:present>&amp;degrees=<logic:present name="search" property="degrees"><bean:write name="search" property="degrees.externalId"/></logic:present>&amp;jobOfferType=<logic:present name="search" property="jobOfferType"><bean:write name="search" property="jobOfferType"/></logic:present></bean:define>
			<cp:collectionPages url="<%= "/student.do?method=searchOffers" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>
		
		<fr:view name="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer.student" >
			<fr:layout name="tabular">
				<fr:property name="classes"	value="tview-vertical" />
				<fr:property name="link(view)" value="/student.do?method=viewJobOffer" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="visibleIf(view)" value="canViewJobProcess" />
				<fr:property name="order(view)" value="1" />
			</fr:layout>
		</fr:view>
		
	</logic:present>


</fr:form>

