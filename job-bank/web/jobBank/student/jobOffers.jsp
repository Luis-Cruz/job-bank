<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>


<bean:size id="processesSize" name="processes" />

<bean:define id="searchParameters"><c:out value="${search.requestParameters}" /></bean:define>

<fr:form action="/student.do?method=searchOffers">
	<fr:edit id="search" name="search">
		<fr:schema bundle="JOB_BANK_RESOURCES"
			type="module.jobBank.domain.beans.SearchOffer">
			<fr:slot name="query" key="label.enterprise.jobOffer.query" />
			<fr:slot name="degrees" key="label.enterprise.offer.degree" layout="menu-select">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.RemoteAllBolonhaDegreesProvider" />
				<fr:property name="eachLayout" value="values"/>
				<fr:property name="saveOptions" value="true"/>
				<fr:property name="sortBy" value="name=asc"/>
				<fr:property name="eachSchema" value="jobBank.enterprise.jobOffer.fenixDegree" />
			</fr:slot>
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
	
		<fr:view name="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer.student" >
			<fr:layout name="tabular-sortable">

				<fr:property name="classes"
					value="tstyle3 mvert1 width100pc tdmiddle punits" />

				<fr:property name="link(view)"
					value="/jobBank.do?method=viewJobOffer" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="visibleIf(view)" value="canViewJobProcess" />
				<fr:property name="order(view)" value="1" />


				<fr:property name="classes"
					value="tstyle3 mvert1 width100pc tdmiddle punits" />
				<fr:property name="link(candidate)"
					value="/student.do?method=attachFilesToOfferCandidacy" />
				<fr:property name="key(candidate)" value="link.jobOffer.candidate" />
				<fr:property name="param(candidate)" value="OID" />
				<fr:property name="bundle(candidate)" value="JOB_BANK_RESOURCES" />
				<fr:property name="visibleIf(candidate)"
					value="jobOffer.canCreateOfferCandidacy" />
				<fr:property name="order(candidate)" value="2" />
				
			
				<fr:property name="linkFormat(externalCandidacy)" value="${jobOffer.jobOfferExternal.externalLink}"/>
				<fr:property name="key(externalCandidacy)" value="link.jobOffer.externalCandidacy" />
				<fr:property name="bundle(externalCandidacy)" value="JOB_BANK_RESOURCES" />
				<fr:property name="visibleIf(externalCandidacy)" value="jobOffer.externalCandidacy" />
				<fr:property name="hasContext(externalCandidacy)" value="false" />
				<fr:property name="contextRelative(externalCandidacy)" value="false" />
				<fr:property name="moduleRelative(externalCandidacy)" value="false" />
				<fr:property name="order(externalCandidacy)" value="3" />
				
				<fr:property name="sortParameter" value="sortBy"/>
       			<fr:property name="sortUrl" value="<%= "/student.do?method=searchOffers"%>" />
		        <fr:property name="sortBy" value="<%= request.getParameter("sortBy") == null ? "jobOffer.function=asc" : request.getParameter("sortBy") %>"/>
			    <fr:property name="sortIgnored" value="true"/>					
			    <fr:property name="sortableSlots" value="jobOffer.reference, jobOffer.enterprise.name, jobOffer.reference, jobOffer.function, jobOffer.place,jobOffer.presentationPeriod"  />
			    
			</fr:layout>
		</fr:view>
	</logic:present>


</fr:form>
<logic:equal name="processesSize" value="0">
	<bean:message bundle="JOB_BANK_RESOURCES"
		key="message.search.no.results.were.found" />
</logic:equal>

