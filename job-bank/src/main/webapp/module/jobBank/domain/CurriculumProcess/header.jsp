<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<bean:define id="student" name="process" property="curriculum.student"/>
<bean:define id="person" name="student" property="person"/>

<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.personalArea"/>
	<span class="processNumber">(<bean:write name="person" property="name"/>)</span>	 
</h2>
