<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/strategischeZiele/view.do">
		<input type="hidden" name="dispatch" value="prepare"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.customizing.projektattraktivitaet"/></div>
		</div>
		
		<div class="largeBoxContent">
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.projektattraktivitaet.beschreibung.1"/>
			</p>
			<p>
				<fmt:message key="label.projektattraktivitaet.beschreibung.2"/>
			</p>
			<p>
				<fmt:message key="label.projektattraktivitaet.beschreibung.3"/>
			</p>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/customizing/view.do">
	    	<c:param name="dispatch" value="prepare"/>
      </c:url>
			<input
					type="button"
					class="button"
					value="<fmt:message key="button.label.zurueck"/>"
					onclick="window.location='<c:out value="${url}"/>'"/>
			<input
					type="submit"
					class="button"
					name="next"
					value="<fmt:message key="button.label.weiter"/>"/>
		</div>
		
	</html:form>
	
</div>