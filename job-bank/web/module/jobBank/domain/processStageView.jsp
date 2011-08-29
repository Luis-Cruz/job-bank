<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>
<%@page import="module.jobBank.domain.utils.JobBankProcessStageState"%>

<bean:define id="jobBankProcessStageView" name="process" property="jobBankProcessStageView" type="module.jobBank.domain.utils.JobBankProcessStageView"/>


<logic:equal name="jobBankProcessStageView" property="offer.canceled" value="true">
	<div class="highlightBox"><p class="mvert025"><bean:message key="message.jobBank.process.canceled" bundle="JOB_BANK_RESOURCES"/></p></div>
</logic:equal>



<div id="graficos-processo">
	
	<ul>
		<logic:iterate id="entry" name="jobBankProcessStageView" property="jobBankProcessStageStates">
			<bean:define id="jobBankProcessStage" name="entry" property="key" type="module.jobBank.domain.utils.JobBankProcessStage"/>
			<bean:define id="jobBankProcessStageState" name="entry" property="value" type="module.jobBank.domain.utils.JobBankProcessStageState"/>

			<% final String classStyle = jobBankProcessStageState == JobBankProcessStageState.COMPLETED ? "completo"
					: (jobBankProcessStageState == JobBankProcessStageState.UNDER_WAY ? "em-curso" : "por-iniciar"); %>
			<li class="<%= classStyle %>"><%= jobBankProcessStage.getLocalizedName() %></li>
		</logic:iterate>

	</ul>
	
</div>


<table style="text-align: center; width: 100%; margin-top: 20px;">
	<tr>
		<td align="center">
			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<logic:iterate id="entry" name="jobBankProcessStageView" property="jobBankProcessStageStates">
						<bean:define id="jobBankProcessStage" name="entry" property="key" type="module.jobBank.domain.utils.JobBankProcessStage"/>
						<bean:define id="jobBankProcessStageState" name="entry" property="value" type="module.jobBank.domain.utils.JobBankProcessStageState"/>

						<% final String colorStyle = jobBankProcessStageState == JobBankProcessStageState.COMPLETED ? "background-color: #CEF6CE; border-color: #04B404; "
								: (jobBankProcessStageState == JobBankProcessStageState.UNDER_WAY ? "background-color: #F6E3CE; border-color: #B45F04;" : ""); %>
						<td style="<%= colorStyle + "border-style: solid; border-width: thin; width: 120px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;" %>"
							align="center"
							title="<%= jobBankProcessStage.getLocalizedDescription() %>">
								<%= jobBankProcessStage.getLocalizedName() %>
						</td>
					</logic:iterate>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table style="border-collapse: separate; border-spacing: 10px; border-style: dotted; border-width: thin; background-color: #FEFEFE;">
				<tr>
					<td align="center">
						<strong>
							<bean:message bundle="JOB_BANK_RESOURCES" key="label.jobBank.stage.view.legend"/>
						</strong>
					</td>
					<td style="border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= JobBankProcessStageState.NOT_YET_UNDER_WAY.getLocalizedName() %>
					</td>
					<td style="background-color: #F6E3CE; border-color: #B45F04; border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= JobBankProcessStageState.UNDER_WAY.getLocalizedName() %>
					</td>
					<td style="background-color: #CEF6CE; border-color: #04B404; border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= JobBankProcessStageState.COMPLETED.getLocalizedName() %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
