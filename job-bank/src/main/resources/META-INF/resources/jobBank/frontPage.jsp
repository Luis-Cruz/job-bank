<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<div class="imagem-entrada"></div>

<div class="homepage">

	<p class="intro grid_7">
		<bean:message key="message.frontPage.intro" bundle="JOB_BANK_RESOURCES" />
	</p>

	<div class="clearthis"></div>
	
	
	<logic:empty name="user">
		<% final String contextPath = request.getContextPath(); %>
		<div id="botao" class="posicao-verde">
			<a href="<%= contextPath %>/enterprise.do?method=termsResponsibilityEnterprise" class="botao verde"><strong><bean:message bundle="JOB_BANK_RESOURCES" key="message.frontPage.registerEnterprise"/></strong></a>
		</div><!-- botão -->
	</logic:empty>

	<div class="hr">
		<hr />
	</div>
	
	<div id="vantagens">
		<h3><bean:message key="title.frontPage.platform.advantages" bundle="JOB_BANK_RESOURCES" /></h3>
		<div class="vantagens-linha">
			<div class="vantagem">
				<h2 class="perfis-de-alunos"><bean:message key="title.frontPage.student.profiles" bundle="JOB_BANK_RESOURCES" /></h2>
				<p><bean:message key="message.frontPage.student.profiles" bundle="JOB_BANK_RESOURCES" /></p>
			</div>
			<div class="vantagem">
				<h2 class="oferta-de-empregos"><bean:message key="title.frontPage.jobOffer" bundle="JOB_BANK_RESOURCES" /></h2>
				<p><bean:message key="message.frontPage.jobOffer" bundle="JOB_BANK_RESOURCES" /></p>
			</div>
		</div>
		<!-- vantagens-linha -->
	
		<div class="vantagens-linha">
			<div class="vantagem">
				<h2 class="confidencialidade"><bean:message key="title.frontPage.confidentiality" bundle="JOB_BANK_RESOURCES" /></h2>
				<p><bean:message key="message.frontPage.confidentiality" bundle="JOB_BANK_RESOURCES" /></p>
			</div>
			<div class="vantagem">
				<h2 class="calendarizacao"><bean:message key="title.frontPage.schedule" bundle="JOB_BANK_RESOURCES" /></h2>
				<p><bean:message key="message.frontPage.schedule" bundle="JOB_BANK_RESOURCES" /></p>
			</div>
		</div>
		<!-- vantagens-linha --> 
	
	</div>
	<!-- vantagens -->

	<div id="contactos">
		<h2><bean:message key="title.frontPage.contacts" bundle="JOB_BANK_RESOURCES" /></h2>
		<h3><bean:message key="label.jobBank.group.jobBankGroup.name" bundle="JOB_BANK_RESOURCES" /></h3>
		<p><bean:message key="message.frontPage.contacts.text" bundle="JOB_BANK_RESOURCES" /></p>
	</div>

</div>
