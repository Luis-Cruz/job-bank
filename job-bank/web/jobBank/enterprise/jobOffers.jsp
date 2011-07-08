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
	<fr:edit id="processesState" name="processesState" type="module.jobBank.domain.beans.JobOfferViewBean">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.JobOfferViewBean" >
			<fr:slot name="processesState" key="label.jobOfferState.jobOffer" layout="menu-postback"/>
		</fr:schema>
		
		<fr:destination name="postback" path="/enterprise.do?method=viewAllJobOffers"/>
	</fr:edit>
</fr:form>

<p><p> 
<logic:present name="processesState">
	
	<logic:equal name="processesState" property="processesCount" value="0">
		<p><bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOffers.empty"/></p>
	</logic:equal>
	
	<logic:notEqual name="processesState" property="processesCount" value="0">	
		<logic:notEqual name="numberOfPages" value="1">
			<cp:collectionPages url="<%= "/enterprise.do?method=viewAllJobOffers" %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>
		
		<fr:view name="processesState" property="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer">
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