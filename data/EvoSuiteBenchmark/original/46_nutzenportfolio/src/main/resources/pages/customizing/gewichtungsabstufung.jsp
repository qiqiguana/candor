<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/gewichtungsabstufung">
   	<html:hidden property="dispatch" value="saveAbstufungen"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.gewichtung"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			
			<c:choose>
				<c:when test="${opNu}">
					<p><fmt:message key="label.gewichtungsabstufungen.operativer.nutzen.beschreibung.1"/></p>
					<p><fmt:message key="label.gewichtungsabstufungen.operativer.nutzen.beschreibung.2"/></p>
				</c:when>
				<c:otherwise>
					<p><fmt:message key="label.gewichtungsabstufungen.beschreibung.1"/></p>
					<p><fmt:message key="label.gewichtungsabstufungen.beschreibung.2"/></p>
				</c:otherwise>
			</c:choose>
			
			<h1><fmt:message key="label.anzahl.abstufungen.erfassen"/></h1>
			
	    <html:select property="abstufungen" value="${abstufungen}">
	    	<html:option value="2">2 <fmt:message key="label.abstufungen"/></html:option>
	    	<html:option value="3">3 <fmt:message key="label.abstufungen"/></html:option>
	    	<html:option value="4">4 <fmt:message key="label.abstufungen"/></html:option>
	    	<html:option value="5">5 <fmt:message key="label.abstufungen"/></html:option>
	    </html:select>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/fragen/view.do">
	    	<c:param name="dispatch" value="populate"/>
      </c:url>
			<input
					type="button"
					class="button"
					value="<fmt:message key="button.label.zurueck"/>"
					onclick="window.location='<c:out value="${url}"/>'"/>
			<input
					type="submit"
					class="button"
					value="<fmt:message key="button.label.weiter"/>"/>
		</div>
		
	</html:form>
	
</div>