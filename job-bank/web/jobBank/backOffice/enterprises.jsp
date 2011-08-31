<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.backOffice.viewEnterprises"/></h2>


<bean:define id="state" name="enterpriseSearch" property="enterpriseState.type"/>


<fr:form  action="/backOffice.do?method=enterprises" >
	<fr:edit id="enterpriseSearch" name="enterpriseSearch">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.SearchEnterprise" >
			<fr:slot name="enterpriseName" key="label.enterprise.name">
				<fr:property name="size" value="40"/>
			</fr:slot>
			<fr:slot name="enterpriseState" key="label.enterprise.state" validator="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="form" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
		<fr:destination name="postback" path="/backOffice.do?method=enterprises"/>
	</fr:edit>
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.search"/>
	</html:submit>
</fr:form>


<fr:form>
	<fr:view name="processes" schema="<%="jobBank.enterprise.view."+state%>">
	<logic:notEqual name="enterpriseSearch" property="enterprisesCount" value="0">
		<logic:notEqual name="numberOfPages" value="1">
			<bean:define id="params">&amp;enterpriseState=<logic:present name="enterpriseSearch" property="enterpriseState"><bean:write name="enterpriseSearch" property="enterpriseState"/></logic:present>&amp;enterpriseName=<logic:present name="enterpriseSearch" property="enterpriseName"><bean:write name="enterpriseSearch" property="enterpriseName"/></logic:present></bean:define>
			<cp:collectionPages url="<%= "/backOffice.do?method=enterprises" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tview-vertical mtop20px"/>
			<fr:property name="link(view)" value="/backOffice.do?method=Enterprise"/>
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES"/>
			<fr:property name="key(view)" value="link.jobBank.view"/>
			<fr:property name="param(view)" value="enterpriseProcess.externalId/OID"/>
			<fr:property name="order(view)" value="1"/>
			<fr:property name="sortBy" value="name=asc, contactEmail=asc, nif=asc"/>
		</fr:layout>
		</logic:notEqual>
	</fr:view>		
</fr:form> 


<logic:empty name="processes">
	<p class="mtop15px"><em><bean:message bundle="JOB_BANK_RESOURCES" key="message.search.no.results.were.found"/>.</em></p>
</logic:empty>
