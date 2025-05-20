<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>

<div id="content">

	<html:form action="/fragebogen/nutzenattraktivitaet/operativer/nutzen.do">
   	<html:hidden property="dispatch" value="save"/>
   	<html:hidden property="projektId"/>
   	<html:hidden property="stufen"/>
   	<html:hidden property="gewichtungsstufen"/>
   	<html:hidden property="opNu"/>
       	
		<div class="largeBoxHeader">
			<div class="title">
				<c:choose>
					<c:when test="${projektbetroffene}">
						<fmt:message key="label.fragebogen.operativer.nutzen"/>
					</c:when>
					<c:otherwise>
						<fmt:message key="label.fragebogen.nutzenattraktivitaet"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="largeBoxContent">
		
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
		
    	<c:forEach var="kategorie" items="${kategorien}" varStatus="status">
				<h1>${kategorie.name}</h1>
	
		    	<c:forEach var="nk" items="${nutzenkriterien}" varStatus="nStatus">
						<c:if test="${nk.kategorieId == kategorie.kategorieId}">
				    	<c:set var="frage" value="${nk.frageManagement}"/>
							<c:if test="${projektbetroffene}">
					    	<c:set var="frage" value="${nk.frageProjektbetroffene}"/>
				    	</c:if>
							<div class="fragebogenBox">
								<p>${frage}</p>
								<input
										type="hidden"
										name="nk[${nStatus.index}].nutzenkriteriumId"
										value="${nk.nutzenkriteriumId}"/>
								<c:forEach var="abstufung" items="${nk.abstufungen}" varStatus="aStatus">
									<div class="fragebogenRadioBox">
										<input
												type="radio"
												name="nk[${nStatus.index}].abstufung"
												value="${abstufung.level}"
												checked="checked"/>
											${abstufung.name}
									</div>
								</c:forEach>
								<div style="clear:left;"></div>
								<c:if test="${nk.gewichtungen != null}">
									<h2><fmt:message key="label.gewichtung"/></h2>
									<c:forEach var="gewichtung" items="${nk.gewichtungen}" varStatus="gStatus">
										<div class="fragebogenRadioBox">
											<input
													type="radio"
													name="nk[${nStatus.index}].gewichtung"
													value="${gewichtung.level}"
													checked="checked"/>
												${gewichtung.name}
										</div>
									</c:forEach>
									<div style="clear:left;"></div>
								</c:if>
							</div>
						</c:if>
					</c:forEach>
	   	</c:forEach>

		</div>
		
		<div class="buttons">
			<input type="submit" class="button" value="<fmt:message key="button.label.absenden"/>"/>
		</div>
		
	</html:form>
	
</div>