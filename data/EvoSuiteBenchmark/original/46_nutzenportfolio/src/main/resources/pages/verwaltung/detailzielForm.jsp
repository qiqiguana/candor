<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>
<div class="largeBoxHeader">
	<div class="title">Detailziele</div>
</div>

<div id="content">

	<html:form action="/adm/verwaltung/detailziele">
   	<html:hidden property="dispatch" value="update"/>
		<html:hidden property="detailzielId"/>
       	
		<div class="largeBoxHeader">
			<div class="title">Detailziel bearbeiten</div>
		</div>
		
		<div class="largeBoxContent">
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<label for="name">Name:</label>
			<html:text property="name" styleClass="maxInputText"/><br/>
			<html:messages id="error" property="name">
				<span class="error"><c:out value="${error}"/></span><br/>
			</html:messages>
			<label for="strategischesZielId">Strategisches Ziel:</label>
			<html:select property="strategischesZielId">
				<option value="null">-</option>
				<c:forEach var="map" items="${strategischeZiele}">
					<html:option value="${map.strategischesZielId}">${map.name}</html:option>
				</c:forEach>
			</html:select>

		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/verwaltung/detailziele/view.do">
	    	<c:param name="dispatch" value="list"/>
      </c:url>
			<input type="button" class="button" value="abbrechen" onclick="window.location='<c:out value="${url}"/>'"/>
			<input type="submit" class="button" value="speichern"/>
		</div>
		
	</html:form>
	
</div>