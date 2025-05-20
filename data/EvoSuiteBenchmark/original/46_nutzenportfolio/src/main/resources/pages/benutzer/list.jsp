<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">
	
	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.neuer.benutzer"/></div>
	</div>
	
	<div class="largeBoxContent">
		<c:url var="url" scope="page" value="/adm/benutzer/view.do">
  		<c:param name="dispatch" value="prepare"/>
 		</c:url>
		&gt; <a href="${url}"><fmt:message key="label.neuer.benutzer.erstellen"/></a>
	</div>
	
	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.bestehende.benutzer"/></div>
	</div>

	<div class="largeBoxContent">
	
		<table>
			<tr>
				<th><fmt:message key="label.benutzer"/></th>
				<th class="remove"><fmt:message key="label.loeschen"/></th>
			</tr>
			
			<c:forEach var="benutzerMap" items="${benutzer}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
				
					<c:url var="url" scope="page" value="/adm/benutzer/view.do">
	       		<c:param name="benutzerId" value="${benutzerMap.benutzerId}"/>
	       		<c:param name="dispatch" value="prepare"/>
       		</c:url>
					<td><a href="<c:out value="${url}"/>"><c:out value="${benutzerMap.benutzername}"/></a></td>
					
					<c:url var="url" scope="page" value="/adm/benutzer/view.do">
						<c:param name="benutzerId" value="${benutzerMap.benutzerId}"/>
            <c:param name="dispatch" value="deletequestion"/>
          </c:url>
					<td class="centered">
						<a href="<c:out value="${url}"/>">
							<img src="<html:rewrite page='/resources/images/delete.gif'/>" alt="<fmt:message key="label.loeschen"/>" />
						</a>
					</td>
					
				</tr>
			</c:forEach>
		</table>
		
	</div>
	
	<div class="buttons">
		<c:url var="url" scope="page" value="/adm/administration.do"/>
		<form action="${url}">
			<input type="submit" value="<fmt:message key="button.label.zurueck"/>"/>
		</form>
	</div>
</div>