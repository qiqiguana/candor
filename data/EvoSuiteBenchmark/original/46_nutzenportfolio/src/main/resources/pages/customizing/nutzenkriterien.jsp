<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/nutzenkriterien.do">
   	<html:hidden styleId="dispatch" property="dispatch" value="set"/>
    <input type="hidden" name="step" value="${step}"/>
    <input type="hidden" name="lastStep" value="${lastStep}"/>
    <input type="hidden" name="kategorieId" value="${kategorieId}"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.nutzenkriterien"/> (${step-1})
			</div>
		</div>
		
		<div class="largeBoxContent">
		
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.nutzenkriterien.beschreibung.1"/>
			</p>
			<p>
				<fmt:message key="label.nutzenkriterien.beschreibung.2"/>
			</p>
		
			<h1><c:out value="${kategorie}"/></h1>
			
			<html:select property="nutzenkriteriumId">
		    <c:forEach var="nutzenkriterien" items="${nutzenkriterien}" varStatus="status">
		    	<c:if test="${nutzenkriterien.nutzenattraktivitaetOperativerNutzenId == null ||
		    			nutzenkriterien.nutzenattraktivitaetOperativerNutzenId != naOpNuId}">
						<html:option value="${nutzenkriterien.nutzenkriteriumId}">${nutzenkriterien.name}</html:option>
					</c:if>
				</c:forEach>
	    </html:select>
 			<input type="submit" class="button" value="<fmt:message key="label.uebernehmen"/>"/>
	    
			<h1><fmt:message key="label.eigene.nutzenkriterien.erfassen"/></h1>
			
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			
			<label for="ziel" class="largeLabel"><fmt:message key="label.nutzenkriterium"/>:</label>
			<html:text property="name" styleId="ziel" styleClass="mediumInputText"/>
			<input
					type="submit"
					class="button"
					id="add"
					name="add"
					value="<fmt:message key="label.uebernehmen"/>"
					onclick="document.getElementById('dispatch').value='add'"/>
			<br/>
			<html:messages id="error" property="name">
				<span class="error"><c:out value="${error}"/></span>
			</html:messages>
		
			<h1><fmt:message key="label.gewaehlte.nutzenkriterien"/></h1>
			<table>
				<tr>
					<th><fmt:message key="label.nutzenkriterium"/></th>
					<th><fmt:message key="label.kategorie"/></th>
					<th class="remove"><fmt:message key="label.entfernen"/></th>
				</tr>
				
		    <c:forEach var="nutzenkriterien" items="${nutzenkriterienAssigned}" varStatus="status">
					<tr class="${status.index%2==0?'even':'odd'}">
						<td><c:out value="${nutzenkriterien.name}"/></td>
						<td><c:out value="${nutzenkriterien.kategorieName}"/></td>
						
						<c:url var="url" scope="page" value="/adm/nutzenkriterien/view.do">
		        	<c:param name="nutzenkriteriumId" value="${nutzenkriterien.nutzenkriteriumId}"/>
		        	<c:param name="naOpNuId" value="${nutzenkriterien.nutzenattraktivitaetOperativerNutzenId}"/>
		          <c:param name="dispatch" value="remove"/>
		          <c:param name="step" value="${step}"/>
		        </c:url>
						<td class="centered">
							<a href="<c:out value="${url}"/>">
								<img src="<html:rewrite page='/resources/images/inactive.gif'/>" alt="<fmt:message key="label.entfernen"/>" />
							</a>
						</td>
					
					</tr>
		    </c:forEach>
			</table>
			
		</div>
		
		<div class="buttons">
			<c:choose>
				<c:when test="${step == 2}">
					<c:url var="url" scope="page" value="/adm/kategorien/view.do">
			    	<c:param name="dispatch" value="prepare"/>
		      </c:url>
				</c:when>
				<c:otherwise>
					<c:url var="url" scope="page" value="/adm/nutzenkriterien/view.do">
			    	<c:param name="dispatch" value="prepare"/>
			    	<c:param name="step" value="${step - 1}"/>
				    <c:param name="lastStep" value="${lastStep}"/>
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
					name="next"
					value="<fmt:message key="button.label.weiter"/>"
					onclick="document.getElementById('dispatch').value='next'"/>
		</div>
		
	</html:form>
	
</div>