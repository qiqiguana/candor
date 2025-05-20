<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/strategischeZiele.do">
   	<html:hidden property="dispatch" styleId="dispatch" value="set"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.strategische.ziele"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.strategische.ziele.beschreibung.1"/>
			</p>
		
			<h1><fmt:message key="label.strategische.ziele.bestimmen"/></h1>
			
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
		
			<c:forEach var="zieleMap" items="${strategischeZiele}" varStatus="status">
				<html:multibox styleClass="checkbox" property="strategischeZieleIds" value="${zieleMap.strategischesZielId}"/>
				<a href="#" class="popup">${zieleMap.name}
				<span class="top">?</span>
				<span class="description">${zieleMap.beschreibung}</span></a>
				<br/>
	    </c:forEach>
	    
			<h1><fmt:message key="label.eigene.strategische.ziele.erfassen"/></h1>
			<label for="name" class="largeLabel"><fmt:message key="label.name"/>:</label>
			<html:text property="name" styleId="name" styleClass="largeInputText"/>
			<html:messages id="error" property="name">
				<span class="error"><c:out value="${error}"/></span>
			</html:messages><br/>
			<label for="beschreibung" class="largeLabel"><fmt:message key="label.beschreibung"/>:</label>
			<html:textarea property="beschreibung" styleId="beschreibung" cols="50" rows="5"/><br/>
			<html:messages id="error" property="beschreibung">
				<label for="err" class="largeLabel">&nbsp;</label>
				<span class="error" id="err"><c:out value="${error}"/></span><br/>
			</html:messages>
			<label for="add" class="largeLabel">&nbsp;</label>
			<input
					type="submit"
					class="button"
					id="add"
					name="add"
					value="<fmt:message key="label.uebernehmen"/>"
					onclick="document.getElementById('dispatch').value='add'"/>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/projektattraktivitaet/view.do"/>
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