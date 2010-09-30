<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@ taglib uri="/WEB-INF/workflow.tld" prefix="wf"%>
<%@page import="module.jobBank.domain.utils.MobilityProcessStageState"%>

<bean:define id="mobilityProcessStageView" name="process" property="mobilityProcessStageView" type="module.jobBank.domain.utils.MobilityProcessStageView"/>


<logic:equal name="mobilityProcessStageView" property="offer.canceled" value="true">
	<div class="highlightBox"><p class="mvert025"><bean:message key="message.mobility.process.canceled" bundle="MOBILITY_RESOURCES"/></p></div>
</logic:equal>

<table style="text-align: center; width: 100%;">
	<tr>
		<td align="center">
			<table style="border-collapse: separate; border-spacing: 10px;">
				<tr>
					<logic:iterate id="entry" name="mobilityProcessStageView" property="mobilityProcessStageStates">
						<bean:define id="mobilityProcessStage" name="entry" property="key" type="module.jobBank.domain.utils.MobilityProcessStage"/>
						<bean:define id="mobilityProcessStageState" name="entry" property="value" type="module.jobBank.domain.utils.MobilityProcessStageState"/>

						<% final String colorStyle = mobilityProcessStageState == MobilityProcessStageState.COMPLETED ? "background-color: #CEF6CE; border-color: #04B404; "
								: (mobilityProcessStageState == MobilityProcessStageState.UNDER_WAY ? "background-color: #F6E3CE; border-color: #B45F04;" : ""); %>
						<td style="<%= colorStyle + "border-style: solid; border-width: thin; width: 120px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;" %>" align="center"
								title="<%= mobilityProcessStage.getLocalizedDescription() %>">
							<%= mobilityProcessStage.getLocalizedName() %>
							
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
							<bean:message bundle="JOB_BANK_RESOURCES" key="label.mobility.stage.view.legend"/>
						</strong>
					</td>
					<td style="border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= MobilityProcessStageState.NOT_YET_UNDER_WAY.getLocalizedName() %>
					</td>
					<td style="background-color: #F6E3CE; border-color: #B45F04; border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= MobilityProcessStageState.UNDER_WAY.getLocalizedName() %>
					</td>
					<td style="background-color: #CEF6CE; border-color: #04B404; border-style: solid; border-width: thin; width: 12px; padding: 5px; border-radius: 2em; -moz-border-radius: 2em;">
					</td>
					<td>
						<%= MobilityProcessStageState.COMPLETED.getLocalizedName() %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
