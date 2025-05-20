<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">
	
	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.neue.projektgruppe"/></div>
	</div>
	
	<div class="largeBoxContent">
		&gt;
		<html:link action="/adm/projektgruppen/view.do?dispatch=prepare">
			<fmt:message key="label.neue.projektgruppe.erstellen"/>
		</html:link>
	</div>
	
	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.bestehende.projektgruppen"/></div>
	</div>

	<div class="largeBoxContent">
	
		<table>
			<tr>
				<th><fmt:message key="label.projektgruppen"/></th>
				<th class="remove"><fmt:message key="label.loeschen"/></th>
			</tr>
			
			<c:forEach var="map" items="${projektgruppen}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
				
					<c:url var="url" scope="page" value="/adm/projektgruppen/view.do">
	       		<c:param name="projektgruppeId" value="${map.projektgruppeId}"/>
	       		<c:param name="dispatch" value="prepare"/>
       		</c:url>
					<td><a href="<c:out value="${url}"/>"><c:out value="${map.name}"/></a></td>
					
					<c:url var="url" scope="page" value="/adm/projektgruppen/view.do">
	       		<c:param name="projektgruppeId" value="${map.projektgruppeId}"/>
            <c:param name="dispatch" value="deleteQuestion"/>
          </c:url>
          <td class="centered">
						<a href="<c:out value="${url}"/>">
							<img src="<html:rewrite page='/resources/images/delete.gif'/>" alt="löschen" />
						</a>
					</td>
					
				</tr>
			</c:forEach>
		</table>
		
	</div>
	
	<div class="buttons">
		<c:url var="url" scope="page" value="/adm/projektadmin.do"/>
		<form action="${url}">
			<input type="submit" value="<fmt:message key="button.label.zurueck"/>"/>
		</form>
	</div>
</div>