<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">
	
	<div class="largeBoxHeader">
		<div class="title">Frage</div>
	</div>
	
	<div class="largeBoxContent">
		<c:if test="${linked != null && !empty linked}">
			<h1>Wichtige Hinweise</h1>
			<p><span class="error">
				Verknüpfte Customizings müssen aus Sicheheitsgründen
	  		zuerst auf "inaktiv" gesetzt werden.
	  	</span></p>
			<p><span class="error">
				Verknüpfte, inaktive Customizings werden ohne weitere Warnung mitsamt allen
				Auswertungen gelöscht, wenn Sie diesen Eintrag löschen!
			</span></p>

		  <c:forEach var="link" items="${linked}">
		  	<h2>${link.key}</h2>
				<p>Die folgenden Einträge sind mit dem zu löschenden Eintrag verknüpft:</p>

				<table class="smallTable">
					<c:choose>
						<c:when test="${link.key == 'Customizings'}">
							<tr>
								<th>Customizing</th>
								<th>Status</th>
							</tr>
							<c:forEach var="item" items="${link.value}">
								<tr>
									<td>${item.name}</td>
									<td>${item.status ? 'aktiv' : 'inaktiv'}</td>
								</tr>
						  </c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<th>${link.key}</th>
							</tr>
							<c:forEach var="item" items="${link.value}">
					  		<tr><td>${item.name}</td></tr>
						  </c:forEach>
					  </c:otherwise>
 					</c:choose>
			  </table>
					  
		  </c:forEach>

		</c:if>
		
		<c:choose>
		
			<c:when test="${active}">
				<c:choose>
					<c:when test="${customizingDelete}">
						<p><span class="error">
							Dieses Customizing kann nicht gelöscht werden, da es noch
							aktiv ist. Setzten Sie zuerst den Status des Customizings
							auf "inaktiv".
						</span></p>
					</c:when>
					<c:otherwise>
						<p><span class="error">
							Dieser Eintrag kann nicht gelöscht werden, da noch
							aktive Customizings verknüpft sind. Setzten Sie bei den oben
							erwähnten Customizings zuerst den Status auf "inaktiv".
						</span></p>
					</c:otherwise>
				</c:choose>
		 		
		 		<c:url var="url" scope="page" value="${action}">
		  		<c:param name="dispatch" value="list"/>
		 		</c:url>
				<a href="<c:out value='${url}'/>">Ok</a>
			</c:when>
			
			<c:otherwise>
		 		<html:messages id="message" message="true">
					<p><span class="error"><c:out value="${message}"/></span></p>
				</html:messages>
		 		
		 		<c:url var="url" scope="page" value="${action}">
		  		<c:param name="dispatch" value="${dispatchYes}"/>
		  		<c:param name="${idName}" value="${idValue}"/>
		 		</c:url>
				<a href="<c:out value='${url}'/>">Ja</a> /
				
				<c:url var="url" scope="page" value="${action}">
		  		<c:param name="dispatch" value="${dispatchNo}"/>
		 		</c:url>
				<a href="<c:out value='${url}'/>">Nein</a>
			</c:otherwise>
			
		</c:choose>
	</div>
</div>