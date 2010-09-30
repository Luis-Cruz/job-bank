<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="processId" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>
<bean:define id="urlPostBack">/student.do?method=activityDefaultPostback&processId=<%=processId%>&activity=<%=activityName%></bean:define>

<bean:define id="className" name="information" property="curriculumQualficationBean.class.simpleName"/>
<bean:define id="usedSchema" value="<%= "jobBank.curriculumQualification." + className %>"/>
					
<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processId %>'> 

	<fr:edit id="activityBean" name="information">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.activity.CurriculumQualificationInformation">
			<fr:slot name="qualificationType" layout="menu-postback" key="label.curriculumQualification.qualificationType">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
		</fr:schema>	
		<fr:destination name="postback" path="<%= urlPostBack%>" ></fr:destination>
	</fr:edit>

	<fr:edit id="innerBean" name="information" property="curriculumQualficationBean" schema="<%= usedSchema %>">
	</fr:edit>
	
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="label.jobBank.submit"/>
	</html:submit>
	
	<h3> 	
		<bean:message bundle="JOB_BANK_RESOURCES" key="message.curriculum.curriculumQualifications.show"/>
	</h3>
	<fr:view name="process" property="curriculum.curriculumQualification">
		<fr:schema bundle="JOB_BANK_RESOURCES" type="module.jobBank.domain.beans.curriculumQualification.CurriculumQualificationBean">
			
			<fr:slot name="class.simpleName" key="label.curriculumQualification.qualificationType"/>
			<fr:slot name="beginDate" key="label.curriculumQualification.beginDate"/>
			<fr:slot name="endDate" key="label.curriculumQualification.endDate"/>
			
			<fr:layout name="tabular">
			
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
				<fr:property name="link(view)" value="/student.do?method=viewCurriculumQualification" />
				<fr:property name="key(view)" value="link.jobBank.view" />
				<fr:property name="param(view)" value="OID" />
				<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
				<fr:property name="order(view)" value="1" />

				
				<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
				<fr:property name="link(delete)" value="/student.do?method=deleteCurriculumQualification" />
				<fr:property name="key(delete)" value="link.jobBank.delete" />
				<fr:property name="param(delete)" value="OID" />
				<fr:property name="bundle(delete)" value="JOB_BANK_RESOURCES" />
				<fr:property name="order(delete)" value="2" />
				
			</fr:layout>	
			
		</fr:schema>	
	</fr:view>
</fr:form>

<logic:empty  name="process" property="curriculum.curriculumQualification">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.curriculumQualfication.there.are.no.addicionalQualfications"/>
</logic:empty> 
