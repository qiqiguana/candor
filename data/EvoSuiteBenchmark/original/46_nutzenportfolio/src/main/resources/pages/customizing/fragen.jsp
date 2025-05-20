<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/fragen">
   	<html:hidden property="dispatch" styleId="dispatch" value="save"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.fragen"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<c:choose>
					<c:when test="${opNu}">
						<fmt:message key="label.fragen.operativer.nutzen.beschreibung"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.fragen.beschreibung"/>
					</c:otherwise>
				</c:choose>
			</p>
					
			<h1>
				<c:choose>
					<c:when test="${opNu}">
						<fmt:message key="label.fragen.fuer.die.projektbetroffenen.erfassen"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.fragen.fuer.das.projektmanagement.erfassen"/>
					</c:otherwise>
				</c:choose>
			</h1>
			
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			
	    <c:forEach var="nutzenkriterium" items="${nutzenkriterien}" varStatus="status">
	    	<c:set var="frage" value="${nutzenkriterium.frageManagement}"/>
				<c:if test="${projektbetroffene}">
		    	<c:set var="frage" value="${nutzenkriterium.frageProjektbetroffene}"/>
	    	</c:if>
				<c:if test="${status.index == 0 || nutzenkriterium.kategorieId != kId}">
					<c:set var="kId" value="${nutzenkriterium.kategorieId}"/>
					<h2>${nutzenkriterium.kategorieName}</h2>
				</c:if>
				<label for="nutzenkriteriumId" class="veryLargeLabel">${nutzenkriterium.name}:</label><br/>
				<input
						type="hidden"
						name="nutzenkriterium[${status.index}].nutzenkriteriumId"
						value="${nutzenkriterium.nutzenkriteriumId}"/>
				<textarea
						cols="85"
						rows="3"
						name="nutzenkriterium[${status.index}].frage">${frage}</textarea>
				<div style="clear:left;"></div>
				<html:messages id="error" property="nutzenkriterium_${nutzenkriterium.nutzenkriteriumId}">
					<p><span class="error"><c:out value="${error}"/></span></p>
				</html:messages>
			</c:forEach>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/nutzenkriterien/view.do">
	    	<c:param name="dispatch" value="prepare"/>
				<c:choose>
					<c:when test="${totalKategorien > 0}">
			    	<c:param name="step" value="${totalKategorien+1}"/>
			    	<c:param name="lastStep" value="true"/>
					</c:when>
				</c:choose>
      </c:url>
      <input
      		type="button"
      		class="button"
      		name="back"
      		value="<fmt:message key="button.label.zurueck"/>"
      		onclick="window.location='${url}'"/>
			<input
					type="submit"
					class="button"
					name="next"
					value="<fmt:message key="button.label.weiter"/>"/>
		</div>
		
	</html:form>
	
</div>