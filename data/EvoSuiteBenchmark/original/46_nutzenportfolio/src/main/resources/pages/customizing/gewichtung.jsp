<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/gewichtung">
   	<html:hidden property="dispatch" value="set" styleId="dispatch"/>
    <input type="hidden" name="step" value="${step - 1}"/>
    <input type="hidden" name="kategorieId" value="${kategorieId}"/>
    <input type="hidden" name="lastStep" value="true"/>   	
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.gewichtung"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			
			<c:choose>
				<c:when test="${opNu}">
					<p><fmt:message key="label.gewichtung.projektbetroffene.beschreibung"/></p>
				</c:when>
				<c:otherwise>
					<p><fmt:message key="label.gewichtung.management.beschreibung"/></p>
				</c:otherwise>
			</c:choose>

			<c:forEach var="nutzenkriterien" items="${nutzenkriterienAssigned}" begin="0" end="0" varStatus="nStatus">
				<c:if test="${nutzenkriterien.kategorieId == kategorieId}">
					<c:forEach var="stufe" items="${stufen}" varStatus="status">
						<div class="choiceBox">
							<strong>Stufe ${stufe}:</strong><br/>
							<select name="select_4_${stufe}" class="choiceSelect">
								<c:if test="${stufe == 1}">
							    <c:forEach var="map" items="${select_4_1}" varStatus="status">
										<c:set var="sel1" value=""/>
										<c:if test="${selected['1'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel1" value="selected='selected'"/>
										</c:if>							    
										<option value="${map.auswahlfeldId}" ${sel1}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 2}">
							    <c:forEach var="map" items="${select_4_2}" varStatus="status">
										<c:set var="sel2" value=""/>
										<c:if test="${selected['2'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel2" value="selected='selected'"/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel2}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 3}">
							    <c:forEach var="map" items="${select_4_3}" varStatus="status">
										<c:set var="sel3" value=""/>
										<c:if test="${selected['3'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel3" value="selected='selected'"/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel3}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 4}">
							    <c:forEach var="map" items="${select_4_4}" varStatus="status">
										<c:if test="${selected['4'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel4" value="selected='selected'"/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel4}>${map.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${stufe == 5}">
							    <c:forEach var="map" items="${select_4_5}" varStatus="status">
										<c:set var="sel5" value=""/>
										<c:if test="${selected['5'][nutzenkriterien.nutzenkriteriumId] == map.auswahlfeldId}">
											<c:set var="sel5" value="selected='selected'"/>
										</c:if>
										<option value="${map.auswahlfeldId}" ${sel5}>${map.name}</option>
									</c:forEach>
								</c:if>
					    </select><br/>
				
							<strong><fmt:message key="label.eigene.stufe"/>:</strong><br/>
							<html:text property="name" styleClass="smallText"/>
							<input type="hidden" name="lvl" value="${stufe}"/>
							<input type="hidden" name="type" value="${gewichtungen}"/><br/>
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
			<c:url var="url" scope="page" value="/adm/gewichtungstyp.do">
	    	<c:param name="dispatch" value="select"/>
      </c:url>
      <input
      		type="button"
      		class="button"
      		name="back"
      		value="<fmt:message key="button.label.zurueck"/>"
      		onclick="window.location='${url}'"/>
			<c:choose>
				<c:when test="${opNu}">
					<c:set var="lbl" value="button.label.fertigstellen"/>
				</c:when>
				<c:otherwise>
					<c:set var="lbl" value="button.label.weiter"/>
				</c:otherwise>
			</c:choose>
			<input
					type="submit"
					class="button"
					name="next"
					value="<fmt:message key="${lbl}"/>"
					onclick="document.getElementById('dispatch').value='next'"/>

		</div>
		
	</html:form>
	
	<script type="text/javascript">
		<c:if test="${incomplete}">
			<c:url var="url" scope="page" value="/adm/customizing/view.do">
    		<c:param name="dispatch" value="list"/>
     	</c:url>
			check = confirm('<fmt:message key="label.nicht.komplett"/>');
			if (check) {
				window.location.href = '${url}';
			}
		</c:if>
	</script>
	
</div>