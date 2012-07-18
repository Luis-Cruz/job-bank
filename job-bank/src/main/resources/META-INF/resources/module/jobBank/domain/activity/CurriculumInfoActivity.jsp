<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="processId" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>
				

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

					
 (A informação é obtida por uma aplicação externa)
<fr:view name="information">
	<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.CurriculumQualificationInformation">
		<fr:slot name="curriculumBean.email" key="label.curriculum.email"/>
		<fr:slot name="curriculumBean.dateOfBirth" key="label.curriculum.dateOfBirth"/> 
		<fr:slot name="curriculumBean.nationality" key="label.curriculum.nationality"/> 
		<fr:slot name="curriculumBean.address" key="label.curriculum.address"/> 
		<fr:slot name="curriculumBean.area" key="label.curriculum.area"/> 
		<fr:slot name="curriculumBean.areaCode" key="label.curriculum.areaCode"/> 
		<fr:slot name="curriculumBean.districtSubdivision" key="label.curriculum.districtSubdivision"/> 
		<fr:slot name="curriculumBean.mobilePhone" key="label.curriculum.mobilePhone"/> 
		<fr:slot name="curriculumBean.phone" key="label.curriculum.mobilePhone"/>   
	</fr:schema>	
</fr:view>


<fr:edit id="activityBean" name="information">
	<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.CurriculumQualificationInformation">
		<fr:slot name="curriculumBean.professionalStatus" key="label.curriculum.professionalStatus" layout="area">  
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="6" />
		</fr:slot>
		<fr:slot name="curriculumBean.geographicAvailability" key="label.curriculum.geographicAvailability"  layout="area">
			<fr:property name="columns" value="60" />
			<fr:property name="rows" value="1" />
		</fr:slot>  
	</fr:schema>	
</fr:edit>
	



