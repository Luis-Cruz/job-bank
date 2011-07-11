<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<bean:define id="processOID" name="process" property="externalId"/>
<bean:define id="activityName" name="information" property="activityName"/>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>


<fr:form action='<%="/workflowProcessManagement.do?method=process&activity="+activityName+"&processId="+processOID %>'>
	<fr:edit name="information" id="activityBean"> 
		<fr:schema  type="module.jobBank.domain.activity.JobOfferInformation"  bundle="JOB_BANK_RESOURCES">	    
			
			<fr:slot name="jobOfferBean.jobOfferType" key="label.enterprise.jobOffer.jobType">
				<fr:property name="defaultOptionHidden" value="true"/>
			</fr:slot>
			
			<logic:equal name="jobOfferBean.candidacyType" value="External"> 
				<fr:slot name="jobOfferBean.externalLink" key="label.enterprise.JobofferExternal.externalLink">
					 <!-- <fr:validator name="pt.ist.fenixWebFramework.renderers.validators.UrlValidator"/> -->
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
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.FutureLocalDateValidator"/>
			</fr:slot> 
			
			<fr:slot name="jobOfferBean.endDate" key="label.enterprise.offer.endDate" layout="picker" >
				<fr:validator  name="pt.ist.fenixWebFramework.renderers.validators.RequiredValidator"/>
				<fr:validator name="pt.ist.fenixWebFramework.rendererExtensions.validators.FutureLocalDateValidator"/>
			</fr:slot>	
			
			<fr:slot name="jobOfferBean.remoteDegrees" key="label.enterprise.offer.degree" layout="option-select">
				<fr:property name="providerClass" value="module.jobBank.presentationTier.providers.RemoteAllDegreesProvider" />
				<fr:property name="eachLayout" value="values" />
				<fr:property name="eachSchema" value="jobBank.enterprise.jobOffer.remoteDegree" />
				<fr:property name="saveOptions" value="true" />
				<fr:property name="selectAllShown" value="true" />
				<fr:property name="sortBy" value="presentationName=asc"/>
			</fr:slot>
		
		<fr:destination name="cancel" path='<%="/workflowProcessManagement.do?method=viewProcess&processId=" + processOID%>'/>
		
		<fr:layout name="tabular"> 
			<fr:property name="classes" value="height: 200px; overflow-y: scroll;"/>
		</fr:layout>
		
		</fr:schema>
	</fr:edit>

	
	<html:submit styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="label.jobBank.submit"/>
    </html:submit>

    <html:cancel styleClass="inputbutton">
		<bean:message  bundle="JOB_BANK_RESOURCES" key="button.jobBank.cancel"/>
    </html:cancel>

</fr:form>