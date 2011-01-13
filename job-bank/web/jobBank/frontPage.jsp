<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>


<h2>
	<bean:message bundle="JOB_BANK_RESOURCES" key="title.jobBank.home"/>
</h2>

<logic:messagesPresent property="message" message="true">
	<div class="error1">
		<html:messages id="errorMessage" property="message" message="true"> 
			<span><fr:view name="errorMessage"/></span>
		</html:messages>
	</div>
</logic:messagesPresent>

<div class="infobox4 col2-1"> 
					<h3>Introdução</h3> 
					<div> 
						<p>Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis. Maecenas vitae dignissim quam. Quisque sodales viverra nisi, eget ornare velit pretium quis. Aliquam vitae metus ut nunc varius varius.</p> 
					</div> 
					<logic:empty name="user"> 
					<h3>Como registar a sua empresa?</h3> 
					<div> 
						<p>Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis.</p> 
						
						<p>
						<form>
							<input type="button" value="Registar Empresa" onClick="/enterprise.do?method=termsResponsabiltyEnterprise"/></p>
						</form> 
					</div> 
					</logic:empty>
					
					<h3>Recuperar Password</h3> 
					<div> 
						<html:link action="/enterprise.do?method=prepareToPasswordRecover">
							<bean:message key="link.enterprise.passwordRecover" bundle="JOB_BANK_RESOURCES"/>
						</html:link>
					</div>
					<h3>Contactos</h3> 
					<div> 
						<p>Donec sollicitudin cursus vitae viverra:<br /><a href="">lorem-ipsum@ist.utl.pt</a></p> 
						<p>Donec sollicitudin cursus vitae viverra:<br /><a href="">lorem-ipsum@ist.utl.pt</a></p> 
						<p>Donec sollicitudin cursus vitae viverra:<br /><a href="">lorem-ipsum@ist.utl.pt</a></p> 
					</div> 
				</div> 
				
				<div class="infobox4 col2-2"> 
					<h3>Notícias</h3> 
					<div> 
						<h4 style="margin-bottom: -15px;"><span style="font-weight: normal; padding-right: 5px;">20/10/2010</span> Proin porta massa ac nisl</h4> 
						<p>Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis. Maecenas vitae dignissim quam. Quisque sodales viverra nisi.</p> 
						<h4 style="margin-bottom: -15px;"><span style="font-weight: normal; padding-right: 5px;">20/10/2010</span> Proin porta massa ac nisl</h4> 
						<p>Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis. Maecenas vitae dignissim quam. Quisque sodales viverra nisi.</p> 
						<h4 style="margin-bottom: -15px;"><span style="font-weight: normal; padding-right: 5px;">20/10/2010</span> Proin porta massa ac nisl</h4> 
						<p>Donec sollicitudin cursus nunc vitae viverra. Proin porta massa ac nisl sollicitudin auctor volutpat augue ultrices. Cras molestie suscipit dignissim. Nam dictum iaculis consectetur. Etiam nisi dolor, posuere sed congue ut, vehicula nec mi. Vestibulum elementum iaculis nunc ut mattis. Maecenas vitae dignissim quam. Quisque sodales viverra nisi.</p> 
					</div> 
				</div> 
 
 
				<div class="clear"></div> 

			</div> 
		</div> 
		<div id="footer"> 
			<div class="c1"></div> 
			<div class="c2"></div> 
			<div class="c3"></div> 
			<div class="c4"></div> 
			<p> &copy;2010 Instituto Superior Técnico </p> 
		</div> 
	</div> 
	<!-- #container2 --> 
</div> 


