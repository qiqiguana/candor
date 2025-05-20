<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib uri="/WEB-INF/customizing-nav.tld" prefix="nav" %>

<html:xhtml/>
<div id="navigation">
	<ul>
		<li>
			<nav:navigation name="customizing">
				<html:link
						action="/adm/customizing/view.do?dispatch=prepare"
						styleClass="heading">
					<fmt:message key="navigation.customizing"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="projektattraktivitaet">
				<html:link
						action="/adm/projektattraktivitaet/view.do"
						styleClass="heading">
					<fmt:message key="navigation.customizing.projektattraktivitaet"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="strategischeZiele">
				<html:link action="/adm/strategischeZiele/view.do?dispatch=prepare">
					<fmt:message key="navigation.customizing.strategische.ziele"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="detailziele">
				<html:link action="/adm/detailziele/view.do?dispatch=prepare">
					<fmt:message key="navigation.customizing.detailziele"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="realisierbarkeit">
				<html:link action="/adm/realisierbarkeit/view.do?dispatch=prepare">
					<fmt:message key="navigation.customizing.realisierbarkeit"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="nutzenattraktivitaet">
				<html:link
						action="/adm/nutzenattraktivitaet.do"
						styleClass="heading">
					<fmt:message key="navigation.customizing.nutzenattraktivitaet"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="kategorienNa">
				<html:link action="/adm/kategorien/view.do?dispatch=prepare&amp;opNu=false">
					<fmt:message key="navigation.customizing.kategorien"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="nutzenkriterienNa">
				<html:link action="/adm/nutzenkriterien/view.do?dispatch=prepare&amp;opNu=false">
					<fmt:message key="navigation.customizing.nutzenkriterien"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="fragenNa">
				<html:link action="/adm/fragen/view.do?dispatch=populate&amp;opNu=false">
					<fmt:message key="navigation.customizing.fragen"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="abstufungenNa">
				<html:link action="/adm/abstufungen.do?dispatch=select&amp;opNu=false">
					<fmt:message key="navigation.customizing.stufenbeschriebe"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="gewichtungenNa">
				<html:link action="/adm/gewichtungstyp.do?dispatch=select&amp;opNu=false">
					<fmt:message key="navigation.customizing.gewichtung"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="opNu">
				<html:link
						action="/adm/operativer/nutzen.do"
						styleClass="heading">
					<fmt:message key="navigation.customizing.operativer.nutzen"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="kategorienOpNu">
				<html:link action="/adm/kategorien/view.do?dispatch=prepare&amp;opNu=true">
					<fmt:message key="navigation.customizing.kategorien"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="nutzenkriterienOpNu">
				<html:link action="/adm/nutzenkriterien/view.do?dispatch=prepare&amp;opNu=true">
					<fmt:message key="navigation.customizing.nutzenkriterien"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="fragenOpNu">
				<html:link action="/adm/fragen/view.do?dispatch=populate&amp;opNu=true">
					<fmt:message key="navigation.customizing.fragen"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="abstufungenOpNu">
				<html:link action="/adm/abstufungen.do?dispatch=select&amp;opNu=true">
					<fmt:message key="navigation.customizing.stufenbeschriebe"/>
				</html:link>
			</nav:navigation>
		</li>
		<li>
			<nav:navigation name="gewichtungenOpNu">
				<html:link action="/adm/gewichtungstyp.do?dispatch=select&amp;opNu=true">
					<fmt:message key="navigation.customizing.gewichtung"/>
				</html:link>
			</nav:navigation>
		</li>
	</ul>
	
	<form action="#">
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/customizing/view.do">
			 	<c:param name="dispatch" value="list"/>
			</c:url>
			<input
					type="button"
					class="largeButton"
					value="<fmt:message key="button.label.zu.den.customizings"/>"
					onclick="window.location='<c:out value="${url}"/>'"/>
		</div>
	</form>

</div>