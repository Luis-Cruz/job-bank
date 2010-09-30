<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>

<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOfferProcess.processIdentification"/>
	<span class="processNumber">(<bean:write name="jobOffer" property="jobOfferProcess.processIdentification"/>)</span>	 
</h2>

<jsp:include page="../module/jobBank/domain/JobOfferProcess/body.jsp"/>


<bean:define id="OID" name="process" property="OID"/>
<wf:isActive processName="process" activityName="SubmitCandidacyActivity" scope="request">
	<p>				
		<html:link action="<%= "/jobBank.do?method=submitCandidacy&OID="+OID%>">
			<bean:message key="activity.SubmitCandidacyActivity" bundle="JOB_BANK_RESOURCES"/>
		</html:link>
	</p>
</wf:isActive>
<wf:isActive processName="process" activityName="UnSubmitCandidacyActivity" scope="request">
	<p>
		<html:link action="<%= "/jobBank.do?method=unSubmitCandidacy&OID="+OID%>">
			<bean:message key="activity.UnSubmitCandidacyActivity" bundle="JOB_BANK_RESOURCES"/>
		</html:link>
	</p>
</wf:isActive>
