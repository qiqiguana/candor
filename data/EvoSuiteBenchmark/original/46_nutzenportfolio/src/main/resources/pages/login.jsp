<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:xhtml/>
<div id="content">
	<html:form action="login.do?dispatch=login" focus="benutzername">
	
	<div class="loginBoxHeader">
		<div class="title">Login</div>
	</div>

	<div class="loginBoxContent">

		<html:messages id="error" message="true">
			<p><span class="error"><c:out value="${error}"/></span></p>
		</html:messages>
		<label for="benutzername">Benutzername:</label>
		<html:text property="benutzername"/>
		<html:messages id="error" property="benutzername">
			<span class="error"><c:out value="${error}"/></span>		
		</html:messages>
		<br/>
		<label for="passwort">Passwort:</label>
		<html:password property="passwort"/>
		<html:messages id="error" property="passwort">
			<span class="error"><c:out value="${error}"/></span>
		</html:messages>
		<br/>

	</div>
	
	<div class="buttons">
		<input type="submit" value="Login"/>
	</div>
	
	</html:form>
</div>