<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/stufenbeschriebe">
   	<html:hidden property="dispatch" value="set" styleId="dispatch"/>
    <input type="hidden" name="step" value="${step}" styleId="step"/>
    <input type="hidden" name="kategorieId" value="${kategorieId}"/>
    <input type="hidden" name="lastStep" value="${lastStep}"/>   	
       	
		<div class="largeBoxHeader">
			<div class="title">
				<c:choose>
					<c:when test="${projektbetroffene}">
						<fmt:message key="label.stufenbeschriebe.fuer.projektbetroffenen.fragebogen"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.stufenbeschriebe.fuer.management.fragebogen"/>
					</c:otherwise>
				</c:choose>
				(${step-1})
			</div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.stufenbeschriebe.beschreibung"/>
			</p>
			
			<h1>${kategorie}</h1>
			
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>

			<c:forEach var="nutzenkriterien" items="${nutzenkriterienAssigned}" varStatus="nStatus">
				<c:if test="${nutzenkriterien.kategorieId == kategorieId}">
		    	<c:set var="frage" value="${nutzenkriterien.frageManagement}"/>
					<c:if test="${projektbetroffene}">
			    	<c:set var="frage" value="${nutzenkriterien.frageProjektbetroffene}"/>
		    	</c:if>
					<p><strong><fmt:message key="label.frage"/>:</strong> ${frage}</p>
					<c:forEach var="stufe" items="${stufen}" varStatus="status">
						<div class="choiceBox">
							<strong><fmt:message key="label.stufe"/> ${status.index + 1}:</strong><br/>
							<select name="select_3_${stufe}_${nutzenkriterien.nutzenkriteriumId}" class="choiceSelect">
								<c:if test="${stufe == 1}">
							    <c:forEach var="map" items="${select_3_1}" varStatus="status">
										<c:set var="sel" value=""/>
										<c:if test="${selected['1'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel" value="selected=\"selected\""/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 2}">
							    <c:forEach var="map" items="${select_3_2}" varStatus="status">
										<c:set var="sel" value=""/>
										<c:if test="${selected['2'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel" value="selected=\"selected\""/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 3}">
							    <c:forEach var="map" items="${select_3_3}" varStatus="status">
										<c:set var="sel" value=""/>
										<c:if test="${selected['3'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel" value="selected=\"selected\""/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 4}">
							    <c:forEach var="map" items="${select_3_4}" varStatus="status">
										<c:set var="sel" value=""/>
										<c:if test="${selected['4'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel" value="selected=\"selected\""/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 5}">
							    <c:forEach var="map" items="${select_3_5}" varStatus="status">
										<c:set var="sel" value=""/>
										<c:if test="${selected['5'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel" value="selected=\"selected\""/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel}>${map.name}</option>
									</c:forEach>
								</c:if>
					    </select><br/>
				
							<strong><fmt:message key="label.eigene.stufe"/>:</strong><br/>
							<html:text property="name" styleClass="smallText"/>
							<input type="hidden" name="lvl" value="${stufe}"/>
							<input type="hidden" name="type" value="${abstufungen}"/><br/>
							<input
									type="submit"
									class="button"
									name="add"
									value="<fmt:message key="label.uebernehmen"/>"
									onclick="document.getElementById('dispatch').value='add'"/>
						</div>
			    </c:forEach>
			    <div style="clear:left"></div>
			  </c:if>
		  </c:forEach>
		</div>
		
		<div class="buttons">
			<c:choose>
				<c:when test="${step == 2}">
					<c:url var="url" scope="page" value="/adm/abstufungen.do">
			    	<c:param name="dispatch" value="select"/>
		      </c:url>
				</c:when>
				<c:otherwise>
					<c:url var="url" scope="page" value="/adm/stufenbeschriebe.do">
			    	<c:param name="dispatch" value="prepare"/>
			    	<c:param name="step" value="${step - 1}"/>
				    <c:param name="lastStep" value="${lastStep}"/>
			    </c:url>
				</c:otherwise>
			</c:choose>
			
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