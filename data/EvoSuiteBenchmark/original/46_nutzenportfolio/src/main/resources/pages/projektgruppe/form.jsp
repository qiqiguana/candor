<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>
<div id="content">

	<html:form action="/adm/projektgruppen" focus="name">
   	<html:hidden property="dispatch" value="save"/>
		<html:hidden property="projektgruppeId"/>
       	
		<div class="largeBoxHeader">
			<div class="title">
				<c:choose>
					<c:when test="${projektgruppeId != null && projektgruppeId != 0}">
						<fmt:message key="label.projektgruppe.bearbeiten"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.neue.projektgruppe"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="largeBoxContent">
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			<label for="name" class="largeLabel">
				<fmt:message key="label.name"/>:
			</label>
			<html:text property="name"/>
			<span class="error">
				&nbsp;
				<html:messages id="error" property="name">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
			<label for="customizing" class="largeLabel">
				<fmt:message key="label.customizing"/>:
			</label>
			<html:select property="customizingId">
				<html:option value="null">-</html:option>
		    <c:forEach var="map" items="${customizings}">
					<html:option value="${map.customizingId}">${map.name}</html:option>
				</c:forEach>
	    </html:select><br/>
			<label for="beschreibung" class="largeLabel">
				<fmt:message key="label.beschreibung"/>:
			</label>
			<html:textarea property="beschreibung"/>
			<span class="error">
				&nbsp;
				<html:messages id="error" property="beschreibung">
					<c:out value="${error}"/>
				</html:messages>
			</span><br/>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/projektgruppen/view.do">
	    	<c:param name="dispatch" value="list"/>
      </c:url>
			<input
					type="button"
					class="button"
					value="<fmt:message key="button.label.abbrechen"/>"
					onclick="window.location='<c:out value="${url}"/>'"/>
			<input
					type="submit"
					class="button"
					value="<fmt:message key="button.label.speichern"/>"/>
		</div>
		
	</html:form>
	
</div>