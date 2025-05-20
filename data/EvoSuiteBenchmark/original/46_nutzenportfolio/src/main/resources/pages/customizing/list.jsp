<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">

	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.neues.customizing"/></div>
	</div>
	
	<div class="largeBoxContent">
		<c:url var="url" scope="page" value="/adm/customizing/view.do">
  		<c:param name="dispatch" value="prepare"/>
 		</c:url>
		&gt; <a href="${url}"><fmt:message key="label.neues.customizing.erstellen"/></a>
	</div>

	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.bestehende.customizings"/></div>
	</div>
	
	<div class="largeBoxContent">
		<table>
			<tr>
				<th><fmt:message key="label.customizing"/></th>
				<th class="remove"><fmt:message key="label.kopieren"/></th>
				<th class="remove"><fmt:message key="label.status"/></th>
				<th class="remove"><fmt:message key="label.loeschen"/></th>
			</tr>
			
	    <c:forEach var="custMap" items="${customizings}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
			
					<c:url var="url" scope="page" value="/adm/customizing/view.do">
						<c:param name="customizingId" value="${custMap.customizingId}"/>
						<c:param name="dispatch" value="prepare"/>
					</c:url>
					<td><a href="<c:out value="${url}"/>"><c:out value="${custMap.name}"/></a></td>
					
					<c:url var="url" scope="page" value="/adm/customizing/view.do">
	        	<c:param name="customizingId" value="${custMap.customizingId}"/>
	          <c:param name="dispatch" value="copy"/>
	        </c:url>
					<td class="centered">
						<a href="<c:out value="${url}"/>">
							<img
									src="<html:rewrite page='/resources/images/copy.gif'/>"
									alt="<fmt:message key="label.kopie"/>"
									title="<fmt:message key="label.kopie"/>"/>
						</a>
					</td>
					
					<c:url var="url" scope="page" value="/adm/customizing/view.do">
	        	<c:param name="customizingId" value="${custMap.customizingId}"/>
	          <c:param name="dispatch" value="changeStatus"/>
	        </c:url>
					<td class="centered">
						<a href="<c:out value="${url}"/>">
							<c:choose>
								<c:when test="${custMap.status}">
									<img
											src="<html:rewrite page='/resources/images/active.gif'/>"
											alt="<fmt:message key="label.aktiv"/>"
											title="<fmt:message key="label.aktiv"/>"/>
								</c:when>
								<c:otherwise>
									<img
											src="<html:rewrite page='/resources/images/inactive.gif'/>"
											alt="<fmt:message key="label.inaktiv"/>"
											title="<fmt:message key="label.inaktiv"/>"/>
								</c:otherwise>
							</c:choose>
						</a>
					</td>
					
					<c:url var="url" scope="page" value="/adm/customizing/view.do">
	        	<c:param name="customizingId" value="${custMap.customizingId}"/>
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
		<c:url var="url" scope="page" value="/adm/index.do"/>
		<form action="${url}">
			<input type="submit" value="<fmt:message key="button.label.zurueck"/>"/>
		</form>
	</div>
	
</div>