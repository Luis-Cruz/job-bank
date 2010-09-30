<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.manageRoles"/>
</h2>

<bean:message bundle="JOB_BANK_RESOURCES" key="message.manager.npeGroup"/>

<div class="mbottom15">
<fr:form action="/jobBank.do?method=addManagement"> 
	<fr:edit id="searchUsers" name="searchUsers" >
		<fr:schema type="module.jobBank.domain.beans.SearchUsers" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="person" layout="autoComplete" key="label.manager.person" bundle="JOB_BANK_RESOURCES">
		        <fr:property name="labelField" value="name"/>
				<fr:property name="format" value="${name} (${user.username})"/>
				<fr:property name="minChars" value="3"/>		
				<fr:property name="args" value="provider=module.organization.presentationTier.renderers.providers.PersonAutoCompleteProvider"/>
				<fr:property name="size" value="40"/>
			</fr:slot>
		</fr:schema>
		
		<fr:layout name="tabular">
			<fr:property name="classes" value="form"/>
			<fr:property name="columnClasses" value=",,tderror"/>
		</fr:layout>
	
	</fr:edit>
	<html:submit styleClass="inputbutton">
			<bean:message  bundle="JOB_BANK_RESOURCES" key="label.jobBank.submit"/>
		</html:submit>
</fr:form>
</div>




<fr:view name="managementUsers">
		<fr:schema type="myorg.domain.User" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="username" key="label.manager.person.username" bundle="JOB_BANK_RESOURCES"/>
			<fr:slot name="person.name" key="label.manager.person.name" bundle="JOB_BANK_RESOURCES"/>
		</fr:schema>
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2"/>
			<fr:property name="columnClasses" value="aleft,,,,aright,"/>
			<fr:property name="sortBy" value="username=asc"/>
			
			<fr:property name="link(remove)" value="/jobBank.do?method=removeManagement"/>
			<fr:property name="bundle(remove)" value="JOB_BANK_RESOURCES"/>
			<fr:property name="key(remove)" value="link.jobBank.delete"/>
			<fr:property name="param(remove)" value="OID"/>
			<fr:property name="order(remove)" value="1"/>
		</fr:layout>
</fr:view>



