<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>
<div class="largeBoxHeader">
	<div class="title">Nutzenkriterien</div>
</div>

<div id="content">

	<html:form action="/adm/verwaltung/nutzenkriterien">
   	<html:hidden property="dispatch" value="update"/>
		<html:hidden property="nutzenkriteriumId"/>
       	
		<div class="largeBoxHeader">
			<div class="title">Nutzenkriterium bearbeiten</div>
		</div>
		
		<div class="largeBoxContent">
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<label for="name">Name:</label>
			<html:text property="name" styleClass="maxInputText"/><br/>
			<html:messages id="error" property="name">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages><br/>
			<label for="beschreibung">Beschreibung:</label>
			<html:text property="beschreibung" styleClass="maxInputText"/><br/>
			<html:messages id="error" property="beschreibung">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages><br/>
			<label for="frageManagementDefault">Standard-Frage für Management:</label>
			<html:textarea property="frageManagementDefault" cols="85" rows="3"/><br/>
			<html:messages id="error" property="frageManagementDefault">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages><br/>
			<label for="frageProjektbetroffeneDefault">Standard-Frage für Projektbetroffe:</label>
			<html:textarea property="frageProjektbetroffeneDefault"  cols="85" rows="3"/><br/>
			<html:messages id="error" property="frageProjektbetroffeneDefault">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages><br/>
			<label for="kategorieId">Kategorie:</label>
			<html:select property="kategorieId">
				<option value="null">-</option>
				<c:forEach var="map" items="${kategorien}">
					<html:option value="${map.kategorieId}">${map.name}</html:option>
				</c:forEach>
			</html:select>

		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/verwaltung/nutzenkriterien/view.do">
	    	<c:param name="dispatch" value="list"/>
      </c:url>
			<input type="button" class="button" value="abbrechen" onclick="window.location='<c:out value="${url}"/>'"/>
			<input type="submit" class="button" value="speichern"/>
		</div>
		
	</html:form>
	
</div>