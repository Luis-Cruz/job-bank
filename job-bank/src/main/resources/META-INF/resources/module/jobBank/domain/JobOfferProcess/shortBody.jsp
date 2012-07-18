<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="jobOffer" name="process" property="jobOffer"/>


<h4>
	<bean:message bundle="JOB_BANK_RESOURCES" key="label.enterprise.jobOfferProcess.processIdentification"/> - 
	<span class="processNumber"><bean:write name="jobOffer" property="jobOfferProcess.processIdentification"/></span> 
</h4>


