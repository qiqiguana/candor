<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<div id="content">

	<h1><fmt:message key="label.administrationsbereich"/></h1>

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/verwaltung/strategischeZiele/view.do?dispatch=list">
					<fmt:message key="label.verwaltung"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.verwaltung.beschreibung"/>
		</div>
	</div>

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/benutzer/view.do?dispatch=list">
					<fmt:message key="label.benutzer"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.benutzer.beschreibung"/>
		</div>
	</div>

  <div class="clearLeft"></div>

	<div class="buttons">
		<c:url var="url" scope="page" value="/adm/index.do"/>
		<form action="${url}">
			<input type="submit" value="<fmt:message key="button.label.zurueck"/>"/>
		</form>
	</div>

  <div class="clearLeft"></div>

</div>
