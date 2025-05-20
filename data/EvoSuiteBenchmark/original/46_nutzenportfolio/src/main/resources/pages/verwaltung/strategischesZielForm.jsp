<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>
<div class="largeBoxHeader">
	<div class="title">Strategische Ziele</div>
</div>
<div id="content">

	<html:form action="/adm/verwaltung/strategischeZiele">
   	<html:hidden property="dispatch" value="update"/>
		<html:hidden property="strategischesZielId"/>
       	
		<div class="largeBoxHeader">
			<div class="title">Strategisches Ziel bearbeiten</div>
		</div>
		
		<div class="largeBoxContent">
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<label for="name">Name:</label>
			<html:text property="name" styleClass="maxInputText"/>
			<html:messages id="error" property="name">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages>
			<label for="beschreibung">Beschreibung:</label>
			<html:textarea property="beschreibung" cols="53" rows="6"/>
			<html:messages id="error" property="beschreibung">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages>

		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/verwaltung/strategischeZiele/view.do">
	    	<c:param name="dispatch" value="list"/>
      </c:url>
			<input type="button" class="button" value="abbrechen" onclick="window.location='<c:out value="${url}"/>'"/>
			<input type="submit" class="button" value="speichern"/>
		</div>
		
	</html:form>
	
</div>