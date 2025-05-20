<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">
	
	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.auswertung"/></div>
	</div>

	<div class="largeBoxContent">
	
		<table>
			<tr>
				<th><fmt:message key="label.projektgruppen"/></th>
			</tr>
			
			<c:forEach var="map" items="${projektgruppen}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
				
					<c:url var="url" scope="page" value="/adm/auswertungen/projekte/view.do">
	       		<c:param name="projektgruppeId" value="${map.projektgruppeId}"/>
	       		<c:param name="dispatch" value="list"/>
       		</c:url>
					<td><a href="<c:out value="${url}"/>"><c:out value="${map.name}"/></a></td>
					
				</tr>
			</c:forEach>
		</table>
		
	</div>
	
	<div class="buttons">
		<c:url var="url" scope="page" value="/adm/index.do"/>
		<input
				type="button"
				class="button"
				value="<fmt:message key="button.label.zurueck"/>"
				onclick="window.location='<c:out value="${url}"/>'"/>
	</div>

</div>