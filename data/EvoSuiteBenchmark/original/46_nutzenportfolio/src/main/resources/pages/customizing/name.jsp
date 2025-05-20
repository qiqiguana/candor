<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/customizing">
   	<html:hidden property="dispatch" value="insertOrUpdate"/>
    <html:hidden property="customizingId"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.benennung.des.customizing"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.customizing.beschreibung.willkommen"/>
			</p>
			<p>
				<fmt:message key="label.customizing.beschreibung.1"/>
				<a href="<html:rewrite page='/resources/files/Erlaeuterung_Software.pdf'/>" target="_blank"><fmt:message key="label.customizing.beschreibung.1.link"/></a>.
			</p>
			<p>
				<fmt:message key="label.customizing.beschreibung.2"/>
			</p>
			
			<h1><fmt:message key="label.customizing.benennen"/></h1>
			<label for="name"><fmt:message key="label.name"/>:</label>
			<html:text property="name" size="40"/> <html:errors property="name"/><br/>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/customizing/view.do">
	    	<c:param name="dispatch" value="list"/>
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