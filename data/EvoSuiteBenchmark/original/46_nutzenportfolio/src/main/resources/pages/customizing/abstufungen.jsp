<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/abstufungen">
   	<html:hidden property="dispatch" value="saveAbstufungen"/>
       	
		<div class="largeBoxHeader">
			<div class="title">
			<c:choose>
				<c:when test="${opNu}">
					<fmt:message key="label.stufenbeschriebe.fuer.projektbetroffenen.fragebogen"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="label.stufenbeschriebe.fuer.management.fragebogen"/>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.abstufungen.beschreibung"/>
			</p>
			
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