<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<div class="largeBoxHeader">
		<div class="title">
			<fmt:message key="label.detailziele"/> (${step-1})
		</div>
	</div>

	<div class="largeBoxContent">

		<h1><fmt:message key="label.beschreibung"/></h1>
		<p>
			<fmt:message key="label.detailziele.beschreibung.1"/>
		</p>
		<p>
			<fmt:message key="label.detailziele.beschreibung.2"/>
		</p>

		<h1><c:out value="${strategischesZielName}"/></h1>

		<html:messages id="error" message="true">
			<p><span class="error"><c:out value="${error}"/></span></p>
		</html:messages>

		<table class="gewichtungsTable">
			<tr>
				<td><fmt:message key="label.detailziele.quantifizierbar"/></td>
				<td><fmt:message key="label.detailziele.nicht.quantifizierbar"/></td>
			</tr><tr>
				<td>
					<html:form action="/adm/detailziele.do">
				   	<html:hidden styleId="dispatch" property="dispatch" value="set"/>
				   	<input type="hidden" name="quantifizierbar" id="quantifizierbar" value="true"/>
				    <input type="hidden" name="step" value="${step}"/>
				    <input type="hidden" name="lastStep" value="${lastStep}"/>
				    <input type="hidden" name="strategischesZielId" value="${strategischesZielId}"/>
						<html:select property="detailzielId">
					    <c:forEach var="zieleMap" items="${detailzieleQ}" varStatus="status">
					    	<c:if test="${zieleMap.projektattraktivitaetId == null || zieleMap.projektattraktivitaetId != projektattraktivitaetId}">
									<html:option value="${zieleMap.detailzielId}">${zieleMap.name}</html:option>
								</c:if>
							</c:forEach>
				    </html:select>
			 			<input type="submit" class="button" value="übernehmen"/>
					</html:form>
				</td>
				<td>
					<html:form action="/adm/detailziele.do">
				   	<html:hidden styleId="dispatch" property="dispatch" value="set"/>
				   	<input type="hidden" name="quantifizierbar" id="quantifizierbar" value="false"/>
				    <input type="hidden" name="step" value="${step}"/>
				    <input type="hidden" name="lastStep" value="${lastStep}"/>
				    <input type="hidden" name="strategischesZielId" value="${strategischesZielId}"/>
						<html:select property="detailzielId">
					    <c:forEach var="zieleMap" items="${detailzieleNQ}" varStatus="status">
					    	<c:if test="${zieleMap.projektattraktivitaetId == null || zieleMap.projektattraktivitaetId != projektattraktivitaetId}">
									<html:option value="${zieleMap.detailzielId}">${zieleMap.name}</html:option>
								</c:if>
							</c:forEach>
				    </html:select>
			 			<input type="submit" class="button" value="übernehmen"/>
					</html:form>
				</td>
			</tr>
		</table><br />

		<h1><fmt:message key="label.eigene.detailziele.erfassen"/></h1>
		<html:form action="/adm/detailziele.do">
	   	<html:hidden styleId="dispatch" property="dispatch" value="add"/>
	    <input type="hidden" name="step" value="${step}"/>
	    <input type="hidden" name="lastStep" value="${lastStep}"/>
	    <input type="hidden" name="strategischesZielId" value="${strategischesZielId}"/>
			<table class="gewichtungsTable">
				<tr>
					<td><label for="ziel" style="width:6em;"><fmt:message key="label.detailziel"/>:</label></td>
					<td><html:text property="name" styleId="ziel" styleClass="mediumInputText"/></td>
					<td>
						<input type="radio" name="quantifizierbar" value="true" checked="checked"/> quantifizierbar<br />
						<input type="radio" name="quantifizierbar" value="false"/> nicht quantifizierbar
					</td>
					<td><input type="submit" class="button" id="add" name="add" value="<fmt:message key="label.uebernehmen"/>"/></td>
				</tr>
			</table>
		</html:form>

		<html:messages id="error" property="name">
			<span class="error"><c:out value="${error}"/></span>
		</html:messages><br />

		<h1><fmt:message key="label.gewaehlte.detailziele"/> <fmt:message key="label.detailziele.quantifizierbar"/></h1>
		<table>
			<tr>
				<th><fmt:message key="label.detailziel"/></th>
				<th><fmt:message key="label.strategisches.ziel"/></th>
				<th class="remove"><fmt:message key="label.entfernen"/></th>
			</tr>
	    <c:forEach var="zieleMap" items="${detailzieleAssignedQ}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td><c:out value="${zieleMap.name}"/></td>
					<td><c:out value="${zieleMap.strategischesZielName}"/></td>
					<c:url var="url" scope="page" value="/adm/detailziele/view.do">
	        	<c:param name="detailzielId" value="${zieleMap.detailzielId}"/>
	        	<c:param name="projektattraktivitaetId" value="${zieleMap.projektattraktivitaetId}"/>
	        	<c:param name="quantifizierbar" value="${zieleMap.quantifizierbar}"/>
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
		</table><br />

		<h1><fmt:message key="label.gewaehlte.detailziele"/> <fmt:message key="label.detailziele.nicht.quantifizierbar"/></h1>
		<table>
			<tr>
				<th><fmt:message key="label.detailziel"/></th>
				<th><fmt:message key="label.strategisches.ziel"/></th>
				<th class="remove"><fmt:message key="label.entfernen"/></th>
			</tr>
	    <c:forEach var="zieleMap" items="${detailzieleAssignedNQ}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td><c:out value="${zieleMap.name}"/></td>
					<td><c:out value="${zieleMap.strategischesZielName}"/></td>
					<c:url var="url" scope="page" value="/adm/detailziele/view.do">
	        	<c:param name="detailzielId" value="${zieleMap.detailzielId}"/>
	        	<c:param name="projektattraktivitaetId" value="${zieleMap.projektattraktivitaetId}"/>
	        	<c:param name="quantifizierbar" value="${zieleMap.quantifizierbar}"/>
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

	<html:form action="/adm/detailziele.do">
   	<input type="hidden" name="dispatch" value="next"/>
    <input type="hidden" name="step" value="${step}"/>
    <input type="hidden" name="lastStep" value="${lastStep}"/>
    <input type="hidden" name="strategischesZielId" value="${strategischesZielId}"/>

		<div class="buttons">
			<c:choose>
				<c:when test="${step == 2}">
					<c:url var="url" scope="page" value="/adm/strategischeZiele/view.do">
			    	<c:param name="dispatch" value="prepare"/>
		      </c:url>
				</c:when>
				<c:otherwise>
					<c:url var="url" scope="page" value="/adm/detailziele.do">
			    	<c:param name="dispatch" value="prepare"/>
			    	<c:param name="quantifizierbar" value="${quantifizierbar}"/>
			    	<c:param name="step" value="${step - 1}"/>
				    <c:param name="lastStep" value="${lastStep}"/>
			    </c:url>
				</c:otherwise>
			</c:choose>
			<input
				type="button"
				class="button"
				name="back"
				value="<fmt:message key="button.label.zurueck"/>"
				onclick="window.location='${url}'"/>

			<input
				type="submit"
				class="button"
				name="next"
				value="<fmt:message key="button.label.weiter"/>"/>

		</div>
	</html:form>

</div>