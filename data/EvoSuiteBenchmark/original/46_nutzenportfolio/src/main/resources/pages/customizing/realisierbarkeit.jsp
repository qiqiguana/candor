<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/realisierbarkeit.do">
   	<html:hidden styleId="dispatch" property="dispatch" value="set"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.realisierbarkeit"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.realisierbarkeit.beschreibung.1"/>
			</p>
		
			<h1><fmt:message key="label.definition.eintrittswahrscheinlichkeit"/></h1>
			
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<html:messages id="error" property="name">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			
			<div class="choiceBox">
				<strong><fmt:message key="label.unwahrscheinlich"/>:</strong><br/>
				<html:select property="select_1_1" styleClass="choiceSelect">
			    <c:forEach var="map" items="${select_1_1}" varStatus="status">
						<c:set var="sel1" value=""/>
						<c:if test="${ew[1] == map.auswahlfeldId}">
							<c:set var="sel1" value="selected='selected'"/>
						</c:if>
						<option value="${map.auswahlfeldId}" ${sel1}>${map.name}</option>
					</c:forEach>
		    </html:select><br/>
	
				<strong><fmt:message key="label.eigene.definition"/>:</strong><br/>
				<html:text property="name" styleClass="smallText"/>
				<input type="hidden" name="lvl" value="1"/>
				<input type="hidden" name="type" value="${eintrittswahrscheinlichkeiten}"/>
				<input
						type="submit"
						class="button"
						id="add"
						name="add"
						value="<fmt:message key="label.uebernehmen"/>"
						onclick="document.getElementById('dispatch').value='add'"/>
			</div>
			
			<div class="choiceBox">
				<strong><fmt:message key="label.wahrscheinlich"/>:</strong><br/>
				<html:select property="select_1_2" styleClass="choiceSelect">
			    <c:forEach var="map" items="${select_1_2}" varStatus="status">
						<c:set var="sel2" value=""/>
						<c:if test="${ew[2] == map.auswahlfeldId}">
							<c:set var="sel2" value="selected='selected'"/>
						</c:if>
						<option value="${map.auswahlfeldId}" ${sel2}>${map.name}</option>
					</c:forEach>
		    </html:select><br/>
	
				<strong><fmt:message key="label.eigene.definition"/>:</strong><br/>
				<html:text property="name" styleClass="smallText"/>
				<input type="hidden" name="lvl" value="2"/>
				<input type="hidden" name="type" value="${eintrittswahrscheinlichkeiten}"/>
				<input
						type="submit"
						class="button"
						id="add"
						name="add"
						value="<fmt:message key="label.uebernehmen"/>"
						onclick="document.getElementById('dispatch').value='add'"/>
			</div>
			
			<div class="choiceBox">
				<strong><fmt:message key="label.sicher"/>:</strong><br/>
				<html:select property="select_1_3" styleClass="choiceSelect">
			    <c:forEach var="map" items="${select_1_3}" varStatus="status">
						<c:set var="sel3" value=""/>
						<c:if test="${ew[3] == map.auswahlfeldId}">
							<c:set var="sel3" value="selected='selected'"/>
						</c:if>
						<option value="${map.auswahlfeldId}" ${sel3}>${map.name}</option>
					</c:forEach>
		    </html:select><br/>
	
				<strong><fmt:message key="label.eigene.definition"/>:</strong><br/>
				<html:text property="name" styleClass="smallText"/>
				<input type="hidden" name="lvl" value="3"/>
				<input type="hidden" name="type" value="${eintrittswahrscheinlichkeiten}"/>
				<input
						type="submit"
						class="button"
						id="add"
						name="add"
						value="<fmt:message key="label.uebernehmen"/>"
						onclick="document.getElementById('dispatch').value='add'"/>
			</div>
			
			<div class="clearLeft"></div>
	    
			<h1><fmt:message key="label.definition.eintrittszeitpunkt"/></h1>
			
			<div class="choiceBox">
				<strong><fmt:message key="label.kurzfristig"/>:</strong><br/>
				<html:select property="select_2_1" styleClass="choiceSelect">
			    <c:forEach var="map" items="${select_2_1}" varStatus="status">
						<c:set var="sel1" value=""/>
						<c:if test="${ez[1] == map.auswahlfeldId}">
							<c:set var="sel1" value="selected='selected'"/>
						</c:if>
						<option value="${map.auswahlfeldId}" ${sel1}>${map.name}</option>
					</c:forEach>
		    </html:select><br/>
	
				<strong><fmt:message key="label.eigene.definition"/>:</strong><br/>
				<html:text property="name" styleClass="smallText"/>
				<input type="hidden" name="lvl" value="1"/>
				<input type="hidden" name="type" value="${eintrittszeitpunkte}"/>
				<input
						type="submit"
						class="button"
						id="add"
						name="add"
						value="<fmt:message key="label.uebernehmen"/>"
						onclick="document.getElementById('dispatch').value='add'"/>
			</div>
			
			<div class="choiceBox">
				<strong><fmt:message key="label.mittelfristig"/>:</strong><br/>
				<html:select property="select_2_2" styleClass="choiceSelect">
			    <c:forEach var="map" items="${select_2_2}" varStatus="status">
						<c:set var="sel2" value=""/>
						<c:if test="${ez[2] == map.auswahlfeldId}">
							<c:set var="sel2" value="selected='selected'"/>
						</c:if>
						<option value="${map.auswahlfeldId}" ${sel2}>${map.name}</option>
					</c:forEach>
		    </html:select><br/>
	
				<strong><fmt:message key="label.eigene.definition"/>:</strong><br/>
				<html:text property="name" styleClass="smallText"/>
				<input type="hidden" name="lvl" value="2"/>
				<input type="hidden" name="type" value="${eintrittszeitpunkte}"/>
				<input
						type="submit"
						class="button"
						id="add"
						name="add"
						value="<fmt:message key="label.uebernehmen"/>"
						onclick="document.getElementById('dispatch').value='add'"/>
			</div>
			
			<div class="choiceBox">
				<strong><fmt:message key="label.langfristig"/>:</strong><br/>
				<html:select property="select_2_3" styleClass="choiceSelect">
			    <c:forEach var="map" items="${select_2_3}" varStatus="status">
						<c:set var="sel3" value=""/>
						<c:if test="${ez[3] == map.auswahlfeldId}">
							<c:set var="sel3" value="selected='selected'"/>
						</c:if>
						<option value="${map.auswahlfeldId}" ${sel3}>${map.name}</option>
					</c:forEach>
		    </html:select><br/>
	
				<strong><fmt:message key="label.eigene.definition"/>:</strong><br/>
				<html:text property="name" styleClass="smallText"/>
				<input type="hidden" name="lvl" value="3"/>
				<input type="hidden" name="type" value="${eintrittszeitpunkte}"/>
				<input
						type="submit"
						class="button"
						id="add"
						name="add"
						value="<fmt:message key="label.uebernehmen"/>"
						onclick="document.getElementById('dispatch').value='add'"/>
			</div>
			
			<div class="clearLeft"></div>
			
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/detailziele/view.do">
	    	<c:param name="dispatch" value="prepare"/>
	    	<c:param name="quantifizierbar" value="false"/>
				<c:choose>
					<c:when test="${totalStrategischeZiele > 0}">
			    	<c:param name="step" value="${totalStrategischeZiele+1}"/>
			    	<c:param name="lastStep" value="true"/>
					</c:when>
				</c:choose>
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
					value="<fmt:message key="button.label.weiter"/>"
					onclick="document.getElementById('dispatch').value='next'"/>
		</div>
		
	</html:form>
	
</div>