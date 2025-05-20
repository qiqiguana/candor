<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div class="largeBoxHeader">
	<div class="title">Eintrittszeitpunkte</div>
</div>

<div class="largeBoxContent">

	<table>
		<tr>
			<th>Eintrittszeitpunkt</th>
			<th>Level</th>
			<th class="remove">löschen</th>
		</tr>
		
		<c:forEach var="map" items="${auswahlfelder}" varStatus="status">
			<tr class="${status.index%2==0?'even':'odd'}">
			
				<c:url var="url" scope="page" value="/adm/verwaltung/eintrittszeitpunkte/view.do">
       		<c:param name="auswahlfeldId" value="${map.auswahlfeldId}"/>
       		<c:param name="dispatch" value="edit"/>
      		</c:url>
				<td><a href="<c:out value="${url}"/>"><c:out value="${map.name}"/></a></td>
				
				<td>${map.level}</td>
				
				<c:url var="url" scope="page" value="/adm/verwaltung/eintrittszeitpunkte/view.do">
					<c:param name="auswahlfeldId" value="${map.auswahlfeldId}"/>
           <c:param name="dispatch" value="deletequestion"/>
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