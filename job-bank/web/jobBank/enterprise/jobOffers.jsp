<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/collectionPager.tld" prefix="cp"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.offers"/></h2>


<p> 
<html:link action="/enterprise.do?method=prepareToCreateOffer" >
		<bean:message bundle="JOB_BANK_RESOURCES" key="link.jobBank.createOffer" />
</html:link>
</p>

<logic:present name="processes">
	<fr:view name="processes" schema="jobBank.jobOfferProcess.jobOffer.viewJobOffer">
		<fr:layout name="tabular" >
			
			<fr:property name="classes" value="tstyle3 mvert1 width100pc tdmiddle punits"/>
			<fr:property name="link(view)" value="/jobBank.do?method=viewJobOffer" />
			<fr:property name="key(view)" value="link.jobBank.view" />
			<fr:property name="param(view)" value="OID" />
			<fr:property name="bundle(view)" value="JOB_BANK_RESOURCES" />
			<fr:property name="order(view)" value="1" />
		</fr:layout>
	</fr:view>
</logic:present>

<logic:empty name="processes">
	<bean:message bundle="JOB_BANK_RESOURCES" key="message.jobBank.not.have.offers"/>
</logic:empty>



