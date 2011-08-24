<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.searchStudents"/></h2>
<%@page import="myorg.util.BundleUtil"%>
<%@page import="module.jobBank.domain.JobBankSystem"%>

<fr:form action="/backOffice.do?method=searchStudents"> 
	<fr:edit id="searchStudents" name="searchStudents" >
		<fr:schema type="module.jobBank.domain.beans.SearchStudentRegistrations" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="username" key="label.manager.person" bundle="JOB_BANK_RESOURCES">
			</fr:slot>
			<fr:slot name="degree" key="label.enterprise.offer.degree" layout="menu-select">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.ActiveMasterFenixDegreesProvider" />
				<fr:property name="eachLayout" value="values" />
				<fr:property name="eachSchema" value="jobBank.enterprise.jobOffer.fenixDegree" />
				<fr:property name="sortBy" value="name=asc" />
				
				<fr:property name="defaultText" value="<%= BundleUtil.getFormattedStringFromResourceBundle(JobBankSystem.JOB_BANK_RESOURCES, "label.degree.all") %>"/>
			</fr:slot>
			<fr:slot name="registrationConclued" key="label.enterprise.degree.concluded" />
			
		</fr:schema>
		
		<fr:layout name="tabular">
			<fr:property name="classes" value="form"/>
			<fr:property name="columnClasses" value=",,tderror"/>
		</fr:layout>
	
	</fr:edit>
		<html:submit styleClass="inputbutton">
			<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.search"/>
		</html:submit>
</fr:form>


<logic:present name="results">

	<logic:equal name="resultsCount" value="0">
		<p><em><bean:message bundle="JOB_BANK_RESOURCES" key="label.students.search.empty"/>.</em></p>
	</logic:equal>
	
	<logic:notEqual name="resultsCount" value="0">	
		<logic:notEqual name="numberOfPages" value="1">
			<bean:define id="params"><logic:present name="searchStudents" property="username">&amp;username=<bean:write name="searchStudents" property="username"/></logic:present><logic:present name="searchStudents" property="degree">&amp;degree=<bean:write name="searchStudents" property="degree.idInternal"/></logic:present><logic:present name="searchStudents" property="registrationConclued">&amp;registrationConclued=<bean:write name="searchStudents" property="registrationConclued"/></logic:present></bean:define>
			<cp:collectionPages url="<%= "/backOffice.do?method=searchStudents" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
		</logic:notEqual>

		<fr:view name="results">
				<fr:schema type="module.jobBank.domain.StudentRegistration" bundle="JOB_BANK_RESOURCES">
					<fr:slot name="student.person.user.username" key="label.enterprise.username" bundle="JOB_BANK_RESOURCES"/>
					<fr:slot name="student.name" key="label.manager.person.name" bundle="JOB_BANK_RESOURCES"/>
					<fr:slot name="fenixDegree.name" key="label.curriculum.degree" bundle="JOB_BANK_RESOURCES"/>
					<fr:slot name="average" key="label.curriculum.average" bundle="JOB_BANK_RESOURCES" layout="null-as-label" />
					<fr:slot name="isConcluded" key="label.enterprise.degree.is.concluded" bundle="JOB_BANK_RESOURCES"/>
				</fr:schema>
				<fr:layout name="tabular">
					<fr:property name="classes" value="tstyle2"/>
					<fr:property name="columnClasses" value=",aleft,aleft,,,,"/>
					<fr:property name="sortBy" value="student.name,fenixDegree.name,average,isConcluded=asc"/>
					
					<fr:property name="link(view)" value="/backOffice.do?method=viewStudentCurriculum"/>
					<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES"/>
					<fr:property name="key(view)" value="link.jobBank.view"/>
					<fr:property name="param(view)" value="student.externalId/studentOID" />
					<fr:property name="order(view)" value="1"/>
				</fr:layout>
		</fr:view>
	</logic:notEqual>
</logic:present>


