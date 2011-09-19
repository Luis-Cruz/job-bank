<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<%@page import="module.jobBank.domain.enums.CandidacyType"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>

<style>
table.choose-degrees ul {
height: 300px;
overflow-y: scroll;
list-style: none;
}
</style>

<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processOID %>'>
	<fr:edit name="information" id="activityBean"> 
		<fr:schema  type="module.jobBank.domain.activity.JobOfferInformation"  bundle="JOB_BANK_RESOURCES">	    
			
			<fr:slot name="jobOfferBean.jobOfferType" key="label.enterprise.jobOffer.jobType">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
			
			<logic:equal name="information" property="jobOfferBean.candidacyType.localizedName" value="<%= CandidacyType.External.getLocalizedName() %>"> 
				<fr:slot name="jobOfferBean.externalLink" key="label.enterprise.JobofferExternal.externalLink">
					<fr:property name="size" value="60" />
					<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.UrlValidator"/>	
					<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>			
				</fr:slot>
			</logic:equal>  
			
			<fr:slot name="jobOfferBean.place" key="label.enterprise.jobOffer.place"> 
				<fr:property name="size" value="60" />
				<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			</fr:slot>
		
			<fr:slot name="jobOfferBean.function" key="label.enterprise.jobOffer.function" layout="area">  
				<fr:property name="columns" value="60" />
				<fr:property name="rows" value="6" />
				<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
			</fr:slot>
		
			<fr:slot name="jobOfferBean.descriptionOffer" key="label.enterprise.jobOffer.descriptionOffer"  layout="area">  
				<fr:property name="columns" value="60" />
				<fr:property name="rows" value="6" />
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.RequiredMultiLanguageStringValidator"/>
			</fr:slot>
		
			<fr:slot name="jobOfferBean.requirements" key="label.enterprise.jobOffer.requirements" layout="area">  
				<fr:property name="columns" value="60" />
				<fr:property name="rows" value="6" />
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.MultiLanguageStringValidator"/>
			</fr:slot>
		
			<fr:slot name="jobOfferBean.vacancies" key="label.enterprise.jobOffer.vacancies">
			  <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.NumberValidator"/>
			  <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
			</fr:slot>
			
			<fr:slot name="jobOfferBean.beginDate" key="label.enterprise.offer.beginDate" layout="picker">
				<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.LocalDateValidator"/>
			</fr:slot> 
			
			<fr:slot name="jobOfferBean.endDate" key="label.enterprise.offer.endDate" layout="picker" >
				<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.LocalDateValidator"/>
			</fr:slot>	
			
			<fr:slot name="jobOfferBean.degrees" key="label.enterprise.offer.degree" layout="option-select">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.ActiveFenixDegreesProvider" />
				<fr:property name="eachLayout" value="values" />
				<fr:property name="eachSchema" value="jobBank.enterprise.jobOffer.fenixDegree" />
				<fr:property name="saveOptions" value="true" />
				<fr:property name="selectAllShown" value="true" />
				<fr:property name="sortBy" value="name=asc"/>
			</fr:slot>
		
		<fr:destination name="cancel" path='<%="/workflowProcessManagement.do?method=viewProcess&processId=" + processOID%>'/>
		
		<fr:layout name="tabular"> 
			<fr:property name="classes" value="choose-degrees"/>
			<fr:property name="requiredMarkShown" value="true" />
			<fr:property name="columnClasses" value=",,tderror" />
		</fr:layout>
		
		</fr:schema>
	</fr:edit>

	<p>
		<html:submit styleClass="inputbutton">
			<bean:message  bundle="JOB_BANK_RESOURCES" key="label.jobBank.submit"/>
	    </html:submit>
	
	    <html:cancel styleClass="inputbutton cancel">
			<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/>
	    </html:cancel>
    </p>

</fr:form>