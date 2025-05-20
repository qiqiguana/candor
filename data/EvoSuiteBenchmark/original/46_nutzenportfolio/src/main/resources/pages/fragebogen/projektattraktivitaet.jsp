<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<html:xhtml/>

<div id="content">

	<html:form action="fragebogen/projektattraktivitaet.do">
   	<html:hidden property="dispatch" value="save"/>
   	<html:hidden property="projektId"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.fragebogen.projektattraktivitaet"/></div>
		</div>
		
		<div class="largeBoxContent">
		
			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
		
	    <h1><fmt:message key="label.messbarkeit.des.quantifizierbaren.nutzens"/></h1>
			<logic:present name="strategischeZiele"> 
				<logic:iterate id="sz" name="strategischeZiele">
					<h2><bean:write name="sz" property="name"/></h2>
					<table>
						<tr>
							<th><fmt:message key="label.detailziel"/></th>
							<th class="dropdownTh"><fmt:message key="label.messbarkeit"/></th>
							<th class="dropdownTh"><fmt:message key="label.eintrittswahrscheinlichkeit"/></th>
							<th class="dropdownTh"><fmt:message key="label.eintrittszeitpunkt"/></th>
						</tr>
						<logic:present name="detailziele"> 
							<c:forEach var="dz" items="${detailziele}" varStatus="dzStatus">
								<c:if test="${dz.quantifizierbar == 'true' && dz.strategischesZielId == sz.strategischesZielId}">
									<tr>
										<td><bean:write name="dz" property="name"/></td>
										<td>
											<input
													type="radio"
													name="dz[${dzStatus.index}].messbarkeit"
													value="true"
													checked="checked"/>
												<fmt:message key="label.messbar"/><br/>
											<input
													type="radio"
													name="dz[${dzStatus.index}].messbarkeit"
													value="false"/>
												<fmt:message key="label.nicht.messbar"/><br/>
										</td>
										<td>
											<c:forEach var="ew" items="${eintrittswahrscheinlichkeiten}" varStatus="status">
												<input
														type="radio"
														name="dz[${dzStatus.index}].eintrittswahrscheinlichkeitId"
														value="${ew.auswahlfeldId}"
														${status.index==0?'checked="checked"':''}/>
													${ew.name}<br/>
											</c:forEach>
										</td>
										<td>
											<c:forEach var="ez" items="${eintrittszeitpunkte}" varStatus="status">
												<input
														type="radio"
														name="dz[${dzStatus.index}].eintrittszeitpunktId"
														value="${ez.auswahlfeldId}"
														${status.index==0?'checked="checked"':''}/>
													${ez.name}<br/>
											</c:forEach>
										</td>
									</tr>
								</c:if>
					    </c:forEach>
			   		</logic:present>
			    </table>
	   		</logic:iterate>
   		</logic:present>
	    
	    <h1><fmt:message key="label.nachweisbarkeit.des.nicht.quantifizierbaren.nutzens"/></h1>
			<logic:present name="strategischeZiele"> 
				<logic:iterate id="sz" name="strategischeZiele">
					<h2><bean:write name="sz" property="name"/></h2>
					<table>
						<tr>
							<th><fmt:message key="label.detailziel"/></th>
							<th class="dropdownTh"><fmt:message key="label.nachweisbarkeit"/></th>
							<th class="dropdownTh"><fmt:message key="label.eintrittswahrscheinlichkeit"/></th>
							<th class="dropdownTh"><fmt:message key="label.eintrittszeitpunkt"/></th>
						</tr>
							<logic:present name="detailziele"> 
								<c:forEach var="dz" items="${detailziele}" varStatus="dzStatus">
									<c:if test="${dz.quantifizierbar == 'false' && dz.strategischesZielId == sz.strategischesZielId}">
										<tr>
											<td>${dz.name}</td>
											<td>
												<input
													type="radio"
													name="dz[${dzStatus.index}].nachweisbarkeit"
													value="true"
													checked="checked"/>
												<fmt:message key="label.nachweisbar"/><br/>
											<input
													type="radio"
													name="dz[${dzStatus.index}].nachweisbarkeit"
													value="false"/>
												<fmt:message key="label.nicht.nachweisbar"/><br/>
											</td>
											<td>
												<c:forEach var="ew" items="${eintrittswahrscheinlichkeiten}" varStatus="status">
													<input
															type="radio"
															name="dz[${dzStatus.index}].eintrittswahrscheinlichkeitId"
															value="${ew.auswahlfeldId}"
															${status.index==0?'checked="checked"':''}/>
														${ew.name}<br/>
												</c:forEach>
											</td>
											<td>
												<c:forEach var="ez" items="${eintrittszeitpunkte}" varStatus="status">
													<input
															type="radio"
															name="dz[${dzStatus.index}].eintrittszeitpunktId"
															value="${ez.auswahlfeldId}"
															${status.index==0?'checked="checked"':''}/>
														${ez.name}<br/>
												</c:forEach>
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</logic:present>
			    </table>
			  </logic:iterate>
			</logic:present>

		</div>
		
		<div class="buttons">
			<input type="submit" class="button" value="<fmt:message key="button.label.absenden"/>"/>
		</div>
		
	</html:form>
	
</div>