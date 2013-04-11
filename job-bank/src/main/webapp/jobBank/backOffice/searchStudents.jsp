<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/collection-pager" prefix="cp"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.searchStudents"/></h2>
<%@page import="pt.ist.bennu.core.util.BundleUtil"%>
<%@page import="module.jobBank.domain.JobBankSystem"%>

<fr:form action="/backOffice.do?"> 
	<input type="hidden" name="method" />
	<fr:edit id="searchStudents" name="searchStudents" >

		<fr:schema type="module.jobBank.domain.beans.SearchStudentRegistrations" bundle="JOB_BANK_RESOURCES">
			<fr:slot name="username" key="label.manager.person" bundle="JOB_BANK_RESOURCES">
				<fr:property name="size" value="40"/>
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

	<html:submit styleClass="inputbutton" onclick="this.form.method.value='searchStudents'">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.search"/>
	</html:submit>
	<html:submit styleClass="inputbutton" onclick="this.form.method.value='exportStudents'">
		<bean:message bundle="JOB_BANK_RESOURCES" key="label.students.search.exportStudents" />
	</html:submit>

</fr:form>


<logic:present name="results">

	<logic:equal name="resultsCount" value="0">
		<p><em><bean:message bundle="JOB_BANK_RESOURCES" key="label.students.search.empty"/>.</em></p>
	</logic:equal>
	
	<logic:notEqual name="resultsCount" value="0">
		<logic:notEqual name="numberOfPages" value="1">
			<div class="mtop20px mbottom5px pagination">
				<bean:define id="params"><logic:present name="searchStudents" property="username">&amp;username=<bean:write name="searchStudents" property="username"/></logic:present><logic:present name="searchStudents" property="degree">&amp;degree=<bean:write name="searchStudents" property="degree.externalId"/></logic:present><logic:present name="searchStudents" property="registrationConclued">&amp;registrationConclued=<bean:write name="searchStudents" property="registrationConclued"/></logic:present></bean:define>
				<cp:collectionPages url="<%= "/backOffice.do?method=searchStudents" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
			</div>
		</logic:notEqual>
		
		<fr:view name="results">
			<fr:schema type="module.jobBank.domain.StudentRegistration" bundle="JOB_BANK_RESOURCES">
				<fr:slot name="student.person.user.username" key="label.enterprise.username" bundle="JOB_BANK_RESOURCES"/>
				<fr:slot name="student.name" key="label.manager.person.name" bundle="JOB_BANK_RESOURCES"/>
				<fr:slot name="fenixDegree.name" key="label.curriculum.degree" bundle="JOB_BANK_RESOURCES"/>
				<fr:slot name="lastStudentRegistrationCycleType.cycleType" key="label.curriculumQualification.cycle" bundle="JOB_BANK_RESOURCES"/>
				<fr:slot name="lastStudentRegistrationCycleType.average" key="label.curriculum.average" bundle="JOB_BANK_RESOURCES" layout="null-as-label" />
				<fr:slot name="isConcluded" key="label.enterprise.degree.is.concluded" bundle="JOB_BANK_RESOURCES"/>
			</fr:schema>
			<fr:layout name="tabular">
				<fr:property name="classes" value="tview-vertical"/>
				<fr:property name="columnClasses" value=",aleft,aleft,nowrap,,,"/>
				<fr:property name="sortBy" value="student.name,fenixDegree.name,lastStudentRegistrationCycleType.average,isConcluded=asc"/>
				
				<fr:property name="link(view)" value="/backOffice.do?method=viewStudentCurriculum"/>
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES"/>
				<fr:property name="key(view)" value="link.jobBank.view"/>
				<fr:property name="param(view)" value="student.externalId/studentOID" />
				<fr:property name="order(view)" value="1"/>
			</fr:layout>
		</fr:view>
		
		<logic:notEqual name="numberOfPages" value="1">
			<div class="mtop10px pagination">
				<bean:define id="params"><logic:present name="searchStudents" property="username">&amp;username=<bean:write name="searchStudents" property="username"/></logic:present><logic:present name="searchStudents" property="degree">&amp;degree=<bean:write name="searchStudents" property="degree.externalId"/></logic:present><logic:present name="searchStudents" property="registrationConclued">&amp;registrationConclued=<bean:write name="searchStudents" property="registrationConclued"/></logic:present></bean:define>
				<cp:collectionPages url="<%= "/backOffice.do?method=searchStudents" + params %>" pageNumberAttributeName="pageNumber" numberOfPagesAttributeName="numberOfPages" numberOfVisualizedPages="10"/>
			</div>
		</logic:notEqual>

	</logic:notEqual>

</logic:present>


