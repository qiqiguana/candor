<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="content">
	
	<div class="loginBoxHeader">
		<div class="title">Login</div>
	</div>

	<html:form action="anmelden.do?dispatch=anmelden" focus="benutzername">
		<div class="loginBoxContent">
	
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<label for="benutzername">Benutzername:</label>
			<html:text property="benutzername"/>
			<span class="error">
				<html:messages id="error" property="benutzername">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
			<label for="passwort">Passwort:</label>
			<html:password property="passwort"/>
			<span class="error">
				<html:messages id="error" property="passwort">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
	
		</div>
		<div class="buttons">
			<input type="reset" id="reset" value="Clear"/>
			<input type="submit" value="Login"/>
		</div>
	</html:form>
</div>