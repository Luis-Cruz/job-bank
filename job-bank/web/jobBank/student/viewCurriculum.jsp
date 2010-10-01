<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.curriculum"/>
</h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>


<bean:define id="curriculumQualifications" name="student" property="curriculum.curriculumQualification"/>
<bean:size id="curriculumQualificationsSize" name="curriculumQualifications"/>

<fr:form>
		<fr:view name="student" property="curriculum">
			<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.Curriculum">
				<fr:slot name="email" key="label.curriculum.email"/> 
				<fr:slot name="dateOfBirth" key="label.curriculum.dateOfBirth" layout="picker"/>
				<fr:slot name="nationality" key="label.curriculum.nationality"/>
				<fr:slot name="address" key="label.curriculum.address"/>
				<fr:slot name="area" key="label.curriculum.area"/>
				<fr:slot name="areaCode" key="label.curriculum.areaCode"/>
				<fr:slot name="districtSubdivision" key="label.curriculum.districtSubdivision">
					<property name="nullOptionHidden" value="true"/>
				</fr:slot>
				<fr:slot name="mobilePhone" key="label.curriculum.mobilePhone"/>
				<fr:slot name="phone" key="label.curriculum.phone"/>  
				<fr:slot name="professionalStatus" key="label.curriculum.professionalStatus" layout="area">  
					<property name="columns" value="60" />
					<property name="rows" value="6" />
				</fr:slot>
				<fr:slot name="geographicAvailability" key="label.curriculum.geographicAvailability"/>
	</fr:schema>
		</fr:view>
	
		<logic:present name="curriculumQualifications" >
			<fr:view name="curriculumQualifications">
		
				<fr:layout name="tabular">
	
					<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
					<fr:property name="link(view)" value="/student.do?method=viewCurriculumQualification" />
					<fr:property name="key(view)" value="label.jobBank.view" />
					<fr:property name="param(view)" value="OID" />
					<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				
				</fr:layout>
				<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.JobOfferProcess">
					<fr:slot name="class.simpleName"/>
					<fr:slot name="beginDate"/>
				<fr:slot name="endDate"/>
				</fr:schema>
			</fr:view>
		</logic:present>
</fr:form> 




