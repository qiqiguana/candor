<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">

	<html:form action="/adm/benutzer">
   	<html:hidden property="dispatch" value="insertOrUpdate"/>
		<html:hidden property="benutzerId"/>
       	
		<div class="largeBoxHeader">
			<div class="title">
				<c:choose>
					<c:when test="${!empty param.benutzerId}">
						<fmt:message key="label.benutzer.bearbeiten"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.neuer.benutzer"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="largeBoxContent">
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<label for="benutzername" class="largeLabel"><fmt:message key="label.benutzername"/>:</label>
			<html:text property="benutzername"/>
			<span class="error">
				<html:messages id="error" property="benutzername">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
			<label for="passwort" class="largeLabel"><fmt:message key="label.passwort"/>:</label>
			<html:password property="passwort"/>
			<span class="error">
				<html:messages id="error" property="passwort">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
			<label for="passwort2" class="largeLabel"><fmt:message key="label.passwort.wiederholen"/>:</label>
			<html:password property="passwort2"/>
			<span class="error">
				<html:messages id="error" property="passwort2">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/benutzer/view.do">
	    	<c:param name="dispatch" value="list"/>
      </c:url>
			<input
					type="button"
					class="button"
					value="<fmt:message key="button.label.abbrechen"/>"
					onclick="window.location='<c:out value="${url}"/>'"/>
			<input
					type="submit"
					class="button"
					value="<fmt:message key="button.label.speichern"/>"/>
		</div>
		
	</html:form>
	
</div>