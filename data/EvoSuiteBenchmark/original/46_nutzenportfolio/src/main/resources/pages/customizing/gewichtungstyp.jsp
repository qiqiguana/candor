<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/gewichtungstyp">
   	<html:hidden property="dispatch" value="saveGewichtungstyp"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.gewichtung"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<c:choose>
					<c:when test="${opNu}">
						<fmt:message key="label.gewichtungstyp.operativer.nutzen.beschreibung"/>
					</c:when>
					<c:otherwise>
							<fmt:message key="label.gewichtungstyp.beschreibung"/>
					</c:otherwise>
				</c:choose>
			</p>
			
			<h1><fmt:message key="label.gewichtungstyp.bestimmung"/></h1>
			
			<fmt:message key="label.gewichtung.wird.bestimmt.durch"/>:<br/>
			<input type="radio" name="gewichtungstyp" value="1" checked="checked" class="checkbox"/>
			<fmt:message key="label.direkt"/><br/>
			<c:if test="${gewichtungNotDirect}">
				<c:set var="checked" value="checked='checked'"/>
			</c:if>
			<c:choose>
				<c:when test="${opNu}">
					<input
							type="radio"
							name="gewichtungstyp"
							value="3"
							class="checkbox"
							${checked}
					/> <fmt:message key="label.von.den.projektbetroffenen"/>
				</c:when>
				<c:otherwise>
					<input
							type="radio"
							name="gewichtungstyp"
							value="2"
							class="checkbox"
							${checked}
					/> <fmt:message key="label.vom.management"/><br/>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="buttons">
			<c:choose>
				<c:when test="${totalKategorien > 0}">
					<c:url var="url" scope="page" value="/adm/stufenbeschriebe.do">
	  		  	<c:param name="dispatch" value="prepare"/>
				   	<c:param name="step" value="${totalKategorien+1}"/>
				   	<c:param name="lastStep" value="true"/>
		      </c:url>
				</c:when>
				<c:otherwise>
					<c:url var="url" scope="page" value="/adm/abstufungen.do">
	  		  	<c:param name="dispatch" value="prepare"/>
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
					value="<fmt:message key="button.label.weiter"/>"/>
		</div>
		
	</html:form>
	
</div>