<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="student" name="process" property="curriculum.student"/>
<bean:define id="person" name="student" property="person"/>

<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.personalArea"/>
	<span class="processNumber">(<bean:write name="person" property="name"/>)</span>	 
</h2>
