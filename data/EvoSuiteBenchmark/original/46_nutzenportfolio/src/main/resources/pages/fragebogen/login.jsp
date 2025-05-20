<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:xhtml/>
<div id="content">
	<c:set
			var="action"
			value="/fragebogen/projektattraktivitaet/login.do"/>
	<c:if test="${param.pa == null || param.pa == 0}">
		<c:set
			var="action"
			value="/fragebogen/nutzenattraktivitaet/operativer/nutzen/login.do"/>
	</c:if>
	
	<html:form action="${action}" focus="email">
   	<html:hidden property="dispatch" value="login"/>
   	<html:hidden property="p"/>
   	<html:hidden property="pa"/>
   	<html:hidden property="na"/>
   	<html:hidden property="o"/>
	
		<div class="loginBoxHeader">
			<div class="title"><fmt:message key="label.fragebogen"/></div>
		</div>
	
		<div class="loginBoxContent">
			<c:choose>
				<c:when test="${status}">
					<html:messages id="error" message="true">
						<p><span class="error"><c:out value="${error}"/></span></p>
					</html:messages>
					<label for="email"><fmt:message key="label.email"/>:</label>
					<html:text property="email"/>
					<html:messages id="error" property="email">
						<span class="error"><c:out value="${error}"/></span>		
					</html:messages>
				</c:when>
				<c:otherwise>
					<fmt:message key="label.befragung.abgeschlossen"/>
				</c:otherwise>
			</c:choose>
			<br/>
	
		</div>
		
		<div class="buttons">
			<c:if test="${status}">
				<input type="submit" value="<fmt:message key="button.label.weiter"/>"/>
			</c:if>
		</div>
	
	</html:form>
</div>