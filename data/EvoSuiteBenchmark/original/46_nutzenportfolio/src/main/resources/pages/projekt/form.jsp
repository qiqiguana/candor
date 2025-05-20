<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>
<div id="content">

	<html:form action="/adm/projekte" focus="name">
   	<html:hidden property="dispatch" value="save"/>
		<html:hidden property="projektId"/>
		<html:hidden property="projektUID"/>
       	
		<div class="largeBoxHeader">
			<div class="title">
				<c:choose>
					<c:when test="${projektgruppeId != null && projektgruppeId != 0}">
						<fmt:message key="label.projekt.bearbeiten"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.neues.projekt"/>
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
			<label for="projektgruppe" class="largeLabel">
				<fmt:message key="label.projektgruppe"/>:
			</label>
			<html:select property="projektgruppeId">
				<html:option value="null">-</html:option>
				<html:options
						collection="projektgruppeMap"
						property="projektgruppeId"
						labelProperty="name"/>
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
			<c:url var="url" scope="page" value="${abortURL}">
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