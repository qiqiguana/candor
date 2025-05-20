<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">
	
	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.auswertung"/></div>
	</div>

	<div class="largeBoxContent">
	
		<c:url var="url" scope="page" value="/adm/auswertungen/AuswertungGrafik">
		 	<c:param name="projekt" value=""/>
		</c:url>
		<img src="${url}" alt="Grafik Auswertung"/>
		
		<c:forEach var="resultat" items="${resultate}">
		<h2>${resultat.name}</h2>
		<fmt:message key="${resultat.label}"/>
		<strong><fmt:message key="label.projektattraktivitaet"/> (<fmt:message key="label.wert"/>: ${resultat.paResultat})</strong><br/>
		<strong><fmt:message key="label.nutzenattraktivitaet"/> (<fmt:message key="label.wert"/>: ${resultat.naResultat})</strong><br/>
		<c:if test="${resultat.opNuResultat != null}">
		<strong><fmt:message key="label.operativer.nutzen"/> (<fmt:message key="label.wert"/>: ${resultat.opNuResultat})</strong><br/>
		</c:if>
		</c:forEach>
		
	</div>
	
	<div class="buttons">
		<c:url var="url" scope="page" value="/adm/auswertungen/projekte/view.do">
		 	<c:param name="dispatch" value="list"/>
		 	<c:param name="projektgruppeId" value="${projektgruppeId}"/>
		</c:url>
		<input
				type="button"
				class="button"
				value="<fmt:message key="button.label.zurueck"/>"
				onclick="window.location='<c:out value="${url}"/>'"/>
	</div>

</div>