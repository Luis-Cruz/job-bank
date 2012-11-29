<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/workflow" prefix="wf"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/collection-pager" prefix="cp"%>

<%@page import="pt.ist.bennu.core.util.BundleUtil"%>
<%@page import="module.jobBank.domain.JobBankSystem"%>


<h2><bean:message bundle="JOB_BANK_RESOURCES" key="label.notifications.header"/></h2>

<bean:define id="searchParameters"><c:out value="${search.requestParameters}" /></bean:define>


<logic:present name="filters">
		
		<fr:view name="filters" schema="jobBank.student.notification" >
			<fr:layout name="tabular">
				<fr:property name="classes"	value="tview-vertical" />
				<fr:property name="link(view)" value="/student.do?method=removeNotificationFilter" />
				<fr:property name="key(view)" value="link.jobBank.delete"/>
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="order(view)" value="1" />
			</fr:layout>
		</fr:view>
		
</logic:present>

<h3><bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.createNotification"/></h3>

<fr:form action="/student.do?method=processNotifications">
	<fr:edit id="filter" name="filter">
		<fr:schema bundle="JOB_BANK_RESOURCES"
			type="module.jobBank.domain.beans.JobOfferNotificationFilterBean">

			<fr:slot name="degree" key="label.enterprise.offer.degree" layout="menu-select">
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
		
		<fr:destination name="postback" path="/student.do?method=processNotifications" />
	</fr:edit>
	
	<p>
		<html:submit styleClass="inputbutton">
			<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.createNotification" />
		</html:submit>
	</p>


</fr:form>





