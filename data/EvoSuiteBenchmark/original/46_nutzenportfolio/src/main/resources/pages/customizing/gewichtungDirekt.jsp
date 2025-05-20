<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<html:form action="/adm/gewichtung/direkt">
   	<html:hidden property="dispatch" value="saveDirekteGewichtung" styleId="dispatch"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.gewichtung"/></div>
		</div>
		
		<div class="largeBoxContent">
		
		<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.direkte.gewichtung.beschreibung.1"/>
			</p>
			<p>
				<fmt:message key="label.direkte.gewichtung.beschreibung.2"/>
			</p>
			<p>
				<fmt:message key="label.direkte.gewichtung.beschreibung.3"/>
				<a href="<html:rewrite page='/resources/files/Erlaeuterung_Software.pdf'/>" target="_blank"><fmt:message key="label.direkte.gewichtung.beschreibung.3.link"/></a>).
			</p>
		
			<h1><fmt:message key="label.direkte.gewichtung"/></h1>

			<input type="button" class="gewichtenButton" value="<fmt:message key="button.label.gewichten"/>" onclick="ausgleichen();" />

			<html:messages id="error" message="true">
				<p><span class="error"><c:out value="${error}"/></span></p>
			</html:messages>
			
			<table class="gewichtungsTable">
				<tr class="bottomBorder">
					<th colspan="3">Direkte Gewichtung</th>
					<th>Resultat</th>
				</tr>
			<c:set var="katNr" value="1"/>
			<c:set var="nkNr" value="0"/>
			<c:forEach var="nutzenkriterium" items="${nutzenkriterien}" varStatus="status">
				<c:if test="${status.index == 0 || nutzenkriterium.kategorieId != kId}">
					<c:if test="${status.index != 0}">
						<tr>
							<td colspan="2">&nbsp;</td>
							<td colspan="2"><fmt:message key="label.muss.hundert.prozent.sein"/></td>
						</tr>
					</c:if>
					<tr>
						<th>${katNr}</th>
						<td colspan="3">
							<c:set var="kId" value="${nutzenkriterium.kategorieId}"/>
							<input
									type="hidden"
									name="nutzenkriterium[${status.index}].kategorieId"
									value="${kId}"/>
							<input
									type="text"
									size="4"
									name="nutzenkriterium[${status.index}].kategorieGewichtung"
									id="k_${nutzenkriterium.kategorieId}"
									onchange="checkNumber('k_${kId}');calculateNkResult(${kId});"
									value="${nutzenkriterium.kategorieGewichtung}"/>%
							<strong>${nutzenkriterium.kategorieName}</strong><br/>
						</td>
					</tr>
					<c:set var="katNr" value="${katNr + 1}"/>
					<c:set var="nkNr" value="0"/>
				</c:if>
				<c:set var="nkNr" value="${nkNr + 1}"/>
				<tr>
					<th>${katNr-1}.${nkNr}</th>
					<td style="width:55px">&nbsp;</td>
					<td>
						<input
								type="hidden"
								name="nutzenkriterium[${status.index}].nutzenkriteriumId"
								value="${nutzenkriterium.nutzenkriteriumId}"/>
						<input
								type="text"
								name="nutzenkriterium[${status.index}].nk"
								id="nk_${nutzenkriterium.nutzenkriteriumId}"
								onchange="checkNumber('nk_${nutzenkriterium.nutzenkriteriumId}');calculateResult(${kId}, ${nutzenkriterium.nutzenkriteriumId});"
								size="4"/>%
						${nutzenkriterium.name}
					</td>
					<td>
						<input
								type="text"
								name="nutzenkriterium[${status.index}].gewichtung"
								id="result_${nutzenkriterium.nutzenkriteriumId}"
								onchange="checkNumber('result_${nutzenkriterium.nutzenkriteriumId}');calculateNk(${kId}, ${nutzenkriterium.nutzenkriteriumId});"
								value="${nutzenkriterium.gewichtung}"
								size="4"/>%
					</td>
				</tr>
				<c:if test="${status.last}">
					<tr>
						<td colspan="2">&nbsp;</td>
						<td colspan="2"><fmt:message key="label.muss.hundert.prozent.sein"/></td>
					</tr>
				</c:if>
		 	</c:forEach>
				<tr><td colspan="4" style="border-bottom:1px solid black;">&nbsp;</td></tr>
				<tr>
					<th>&nbsp;</th>
					<th style="width:60px;"><input type="text" disabled="disabled" id="katTotal" size="4"/>%</th>
					<th>&nbsp;</th>
					<th><input type="text" disabled="disabled" id="resTotal" size="4"/>%</th>
				</tr>
		 </table>
		 
	</div>
		
	<div class="buttons">
		<c:url var="url" scope="page" value="/adm/gewichtungstyp.do">
    	<c:param name="dispatch" value="select"/>
     </c:url>
     <input
     		type="button"
     		class="button"
     		name="back"
     		value="<fmt:message key="button.label.zurueck"/>"
     		onclick="window.location='${url}'"/>
		<c:choose>
			<c:when test="${opNu}">
				<c:set var="lbl" value="button.label.fertigstellen"/>
			</c:when>
			<c:otherwise>
				<c:set var="lbl" value="button.label.weiter"/>
			</c:otherwise>
		</c:choose>
		<input
				type="submit"
				class="button"
				name="next"
				value="<fmt:message key="${lbl}"/>"/>
	</div>
		
	</html:form>
	
	<script type="text/javascript">
	
		nutzenkriterienCounter = 0;
		kategorienCounter = 0;
		nutzenkriterienIndex = -1;
		kategorien = new Array();
		<c:forEach var="nutzenkriterium" items="${nutzenkriterien}" varStatus="status">
			<c:if test="${status.index == 0 || nutzenkriterium.kategorieId != kId}">
				<c:set var="kId" value="${nutzenkriterium.kategorieId}"/>
				kategorien[${kId}] = new Array();
				nutzenkriterienIndex = -1;
				kategorienCounter++;
			</c:if>
			nutzenkriterienIndex++;
			kategorien[${kId}][nutzenkriterienIndex] = ${nutzenkriterium.nutzenkriteriumId};
			nutzenkriterienCounter++;
		</c:forEach>
		
		function calculateNk(kId, nkId) {
			var katElem = document.getElementById('k_' + kId);
			var katValue = isNaN(katElem.value) ? 0 : parseFloat(katElem.value);
			var nkElem = document.getElementById('nk_' + nkId);
			var resElem = document.getElementById('result_' + nkId);
			var resValue = isNaN(resElem.value) ? 0 : parseFloat(resElem.value);
			if (katElem.value != 0) {
				nkElem.value = resElem.value / katElem.value * 100;
			}
			else {
				katElem.value = 0;
			}
			calculateTotals();
		}
	
		function calculateResult(kId, nkId) {
			var katElem = document.getElementById('k_' + kId);
			var nkElem = document.getElementById('nk_' + nkId);
			var resElem = document.getElementById('result_' + nkId);
			resElem.value = katElem.value / 100 * nkElem.value;
			calculateTotals();
		}
		
		function calculateNkResult(kId) {
			var katElem = document.getElementById('k_' + kId);
			var katValue = parseFloat(katElem.value);
			var kategorie = kategorien[kId];
			for (var j = 0; j < kategorie.length; j++) {
				//alert('j=' + j + ' nk=' + kategorie[j]);
				var nkElem = document.getElementById('nk_' + kategorie[j]);
				var resElem = document.getElementById('result_' + kategorie[j]);
				var nkValue = parseFloat(nkElem.value);
				if (isNaN(nkValue)) {
					nkElem.value = 0;
					resElem.value = 0;
				}
				else if (nkValue != 0) {
					resElem.value = katValue / 100 * nkValue;
				}
				else if (!isNaN(resElem.value)) {
					nkElem.value = resElem.value / katValue * 100;
				}
			}
			calculateTotals();
		}
		
		function calculateTotals() {
			var katTotal = 0.0;
			var resTotal = 0.0;
			for (var i = 0; i < kategorien.length; i++) {
				var kategorie = kategorien[i];
				if (typeof kategorie != "undefined") {
					var katElem = document.getElementById('k_' + i);
					katTotal += parseFloat(katElem.value);
					for (j = 0; j < kategorie.length; j++) {
						//alert('j=' + j + ' nk=' + kategorie[j]);
						var resElem = document.getElementById('result_' + kategorie[j]);
						resTotal += parseFloat(resElem.value);
					}
				}
			}
			var katTotalElem = document.getElementById('katTotal');
			katTotalElem.value = katTotal;
			var resTotalElem = document.getElementById('resTotal');
			resTotalElem.value = resTotal;
		}
	   
		function checkNumber(id) {
			var elem = document.getElementById(id);
			var elemValue = elem.value;
			var parts = elemValue.split('/');
			if (parts.length == 2 && !isNaN(parts[0]) && !isNaN(parts[1])
					&& parts[1] != 0) {
				elem.value = parts[0] / parts[1] * 100;
			}
			if (isNaN(parseFloat(elem.value)) ||
					elem.value == '') {
				elem.value = 0;
				return false;
			}
			if (elem.value > 100) {
				elem.value = 100;
			}
			elem.value = parseFloat(elem.value);
			return true;
		}

		function ausgleichen() {
			var even = 100 / nutzenkriterienCounter;
			for (var i = 0; i < kategorien.length; i++) {
				var kategorie = kategorien[i];
				if (typeof kategorie != "undefined") {
					var nkEven = 100 / kategorie.length;
					for (var j = 0; j < kategorie.length; j++) {
						//alert('j=' + j + ' nk=' + kategorie[j]);
						document.getElementById('result_' + kategorie[j]).value = even;
						document.getElementById('nk_' + kategorie[j]).value = nkEven;
					}
				}
			}
			
			for (var i = 0; i < kategorien.length; i++) {
				var kategorie = kategorien[i];
				if (typeof kategorie != "undefined") {
					var katValue = kategorie.length / nutzenkriterienCounter * 100;
					document.getElementById('k_' + i).value = katValue;
				}
			}
			calculateTotals();
		}
		
		var katTotal = 0.0;
		var resTotal = 0.0;
		<c:forEach var="nutzenkriterium" items="${nutzenkriterien}" varStatus="status">
			<c:if test="${status.index == 0 || nutzenkriterium.kategorieId != kId}">
				<c:set var="kId" value="${nutzenkriterium.kategorieId}"/>
				checkNumber('k_${kId}');
				var katElem = document.getElementById('k_${kId}');
				var katValue = parseFloat(katElem.value);
				katTotal += katValue;
			</c:if>
			checkNumber('nk_${nutzenkriterium.nutzenkriteriumId}');
			//calculateResult(${kId}, ${nutzenkriterium.nutzenkriteriumId});
			var resElem = document.getElementById('result_${nutzenkriterium.nutzenkriteriumId}');
			var resValue = parseFloat(resElem.value);
			var nkElem = document.getElementById('nk_${nutzenkriterium.nutzenkriteriumId}');
			if (isNaN(resValue)) {
				resElem.value = 0;
				resValue = 0;
			}
			if (katValue != 0) {
				nkElem.value = resValue / katValue * 100;
			}
			else {
				nkElem.value = 0;
			}
			resTotal += parseFloat(resElem.value);
		</c:forEach>
		var katTotalElem = document.getElementById('katTotal');
		katTotalElem.value = katTotal;
		var resTotalElem = document.getElementById('resTotal');
		resTotalElem.value = resTotal;

		<c:if test="${incomplete}">
			<c:url var="url" scope="page" value="/adm/customizing/view.do">
    		<c:param name="dispatch" value="list"/>
     	</c:url>
			check = confirm('<fmt:message key="label.nicht.komplett"/>');
			if (check) {
				window.location.href = '${url}';
			}
		</c:if>
	</script>
	
</div>