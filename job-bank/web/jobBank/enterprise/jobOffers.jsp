<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.offers"/></h2>


<p> 
<html:link action="/enterprise.do?method=prepareToCreateOffer" >
		<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.createOffer" />
</html:link>
</p>


<fr:form  action="/backOffice.do?method=viewAllJobOffers" >
	<fr:edit id="offerSearch" name="offerSearch">
		<fr:schema type="module.jobBank.domain.beans.SearchOfferState" bundle="JOB_BANK_RESOURCES">

			<fr:slot name="jobOfferState" key="label.jobOfferSearch.state" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator" layout="menu-postback">
				<fr:property name="defaultOptionHidden" value="true"/>
				<fr:property name="excludedValues" value="ALL" />
			</fr:slot>
		</fr:schema>
		
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
		
		<fr:destination name="postback" path="/enterprise.do?method=viewAllJobOffers"/>
	</fr:edit>
</fr:form>

<logic:present name="processes">
	
	<logic:equal name="offerSearch" property="processesCount" value="0">
		<p><bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffers.empty"/></p>
	</logic:equal>
	
	<logic:notEqual name="offerSearch" property="processesCount" value="0">	
		<logic:notEqual name="numberOfPages" value="1">
			<bean:define id="params">&amp;offerState=<logic:present name="offerSearch" property="jobOfferState"><bean:write name="offerSearch" property="jobOfferState"/></logic:present>&amp;processNumber=<logic:present name="offerSearch" property="processNumber"><bean:write name="offerSearch" property="processNumber"/></logic:present>&amp;enterprise=<logic:present name="offerSearch" property="enterprise"><bean:write name="offerSearch" property="enterprise"/></logic:present></bean:define>
			<cp:collectionPages url="<%= "/enterprise.do?method=viewAllJobOffers" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>
		
		<fr:view name="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer">
			<fr:layout name="tabular" >
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
				<fr:property name="link(view)" value="/jobBank.do?method=viewJobOffer" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="order(view)" value="1" />
				
				<fr:property name="sortBy" value="creationDate=asc" />
			</fr:layout>
		</fr:view>
	</logic:notEqual>
</logic:present>

