<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">
	
	<html:form action="/adm/auswertungen/projekte.do">
   	<html:hidden property="dispatch" value="auswertung"/>
   	<html:hidden property="projektgruppeId"/>
	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.auswertung"/></div>
		</div>
	
		<div class="largeBoxContent">
		
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
		
			<table>
				<tr>
					<th><fmt:message key="label.projekte"/></th>
					<th class="remove"><fmt:message key="label.auswahl"/></th>
				</tr>
				
				<c:forEach var="map" items="${projekte}" varStatus="status">
					<c:if test="${map.projektgruppeId == param.projektgruppeId || map.projektgruppeId == projektgruppeId}">
						<tr class="${status.index%2==0?'even':'odd'}">
							<td><c:out value="${map.name}"/></td>
							
		          <td class="centered">
								<html:multibox
										styleClass="checkbox"
										property="projektIds"
										value="${map.projektId}"/>
							</td>
							
						</tr>
					</c:if>
				</c:forEach>
			</table>
			
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/auswertungen/projektgruppen.do">
	    	<c:param name="dispatch" value="list"/>
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
					value="<fmt:message key="button.label.weiter"/>"/>
		</div>
		
	</html:form>
</div>