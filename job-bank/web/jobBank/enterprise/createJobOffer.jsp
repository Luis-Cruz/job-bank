<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.createOffer"/></h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<bean:define id="classType" name="jobOfferBean" property="candidacyType.type"/>

<fr:form action="/enterprise.do?method=createOffer">
	<fr:edit id="jobOfferBean" name="jobOfferBean" > 
		<fr:schema  type="module.jobBank.domain.beans.JobOfferBean"  bundle="JOB_BANK_RESOURCES">	    
			<fr:slot name="jobOfferType" key="label.enterprise.jobOffer.jobType">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
			<fr:slot name="candidacyType" key="label.enterprise.jobOffer.candidancyType" layout="menu-postback">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
			<bean:define id="candidacyType" name="jobOfferBean" property="candidacyType"/>
			<logic:equal name="candidacyType" value="External"> 
			
				<fr:slot name="externalLink" key="label.enterprise.JobofferExternal.externalLink">
					 <!-- <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.UrlValidator"/> -->
				</fr:slot>
			
			</logic:equal>  
			<fr:slot name="place" key="label.enterprise.jobOffer.place"> 
				<fr:property name="size" value="80" />
				<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			</fr:slot>
			<fr:slot name="function" key="label.enterprise.jobOffer.function" layout="area">  
				<fr:property name="columns" value="60" />
				<fr:property name="rows" value="6" />
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
			</fr:slot>
			<fr:slot name="descriptionOffer" key="label.enterprise.jobOffer.descriptionOffer" layout="area">  
				<fr:property name="columns" value="60" />
				<fr:property name="rows" value="6" />
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
			</fr:slot>
			<fr:slot name="requirements" key="label.enterprise.jobOffer.requirements" layout="area">  
				<fr:property name="columns" value="60" />
				<fr:property name="rows" value="6" />
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.MultiLanguageStringValidator"/>
			</fr:slot>
			<fr:slot name="vacancies" key="label.enterprise.jobOffer.vacancies">
			  <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.NumberValidator"/>
			  <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			</fr:slot>
			<fr:slot name="beginDate" key="label.enterprise.offer.beginDate" layout="picker">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.FutureLocalDateValidator"/>
			</fr:slot> 
			<fr:slot name="endDate" key="label.enterprise.offer.endDate" layout="picker" >
				<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.FutureLocalDateValidator"/>
			</fr:slot>	
			<fr:slot name="degrees" key="label.enterprise.offer.degree" layout="option-select">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.ActiveFenixDegreesProvider" />
				<fr:property name="eachLayout" value="values" />
				<fr:property name="eachSchema" value="jobBank.enterprise.jobOffer.fenixDegree" />
				<fr:property name="saveOptions" value="true" />
				<fr:property name="selectAllShown" value="true" />
				<fr:property name="sortBy" value="name=asc"/>
			</fr:slot>
		</fr:schema>
		<fr:layout name="tabular"> 
			<fr:property name="classes" value="height: 200px; overflow-y: scroll;"/>
			<fr:property name="requiredMarkShown" value="true" />
		</fr:layout>
		<fr:destination name="postback" path="/enterprise.do?method=prepareToCreateOffer"/>
	</fr:edit>
		

<table class="tstyle0">
	<tr>
		<th>
			<td>
				<html:submit styleClass="inputbutton">
					<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.submit"/>
				</html:submit>
				</fr:form>
			</td>
		</th>
		<th>
			<td>
				<fr:form action="/enterprise.do?method=viewAllJobOffers">
					<html:submit styleClass="inputbutton"><bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/></html:submit>
				</fr:form>
			</td>
		</th> 
	</tr>
</table>



