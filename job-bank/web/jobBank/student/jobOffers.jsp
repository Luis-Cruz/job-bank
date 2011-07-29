<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>

<%@page import="myorg.util.BundleUtil"%>
<%@page import="module.jobBank.domain.JobBankSystem"%>

<bean:define id="searchParameters"><c:out value="${search.requestParameters}" /></bean:define>

<fr:form action="/student.do?method=searchOffers">
	<fr:edit id="search" name="search">
		<fr:schema bundle="JOB_BANK_RESOURCES"
			type="module.jobBank.domain.beans.SearchOffer">
			<fr:slot name="query" key="label.enterprise.jobOffer.query" />
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
	<html:submit styleClass="inputbutton">
		<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.search" />
	</html:submit>

	<p><p><p>

	<logic:present name="processes">
	
		<bean:size id="processesSize" name="processes" />
		<logic:equal name="processesSize" value="0">
			<bean:message bundle="JOB_BANK_RESOURCES" key="message.search.no.results.were.found" />
		</logic:equal>
	
		<logic:notEqual name="numberOfPages" value="1">
			<bean:define id="params">&amp;query=<logic:present name="search" property="query"><bean:write name="search" property="query"/></logic:present>&amp;degrees=<logic:present name="search" property="degrees"><bean:write name="search" property="degrees.idInternal"/></logic:present>&amp;jobOfferType=<logic:present name="search" property="jobOfferType"><bean:write name="search" property="jobOfferType"/></logic:present></bean:define>
			<cp:collectionPages url="<%= "/student.do?method=searchOffers" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>
		
			<fr:view name="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer.student" >
				<fr:layout name="tabular">
	
					<fr:property name="classes"
						value="tstyle3 mvert1 width100pc tdmiddle punits" />
	
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

