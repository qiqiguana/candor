<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<div id="content">

	<br />

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/einfuehrung.do">
					<fmt:message key="label.1.einfuehrung"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.erster.schritt.beschreibung"/>
		</div>
	</div>

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/customizing/view.do?dispatch=list">
					<fmt:message key="label.2.customizing"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.zweiter.schritt.beschreibung"/>
		</div>
	</div>

	<div class="descBox">
		<h2><fmt:message key="label.schritte"/></h2>
		<ol>
			<li>Wählen Sie <a href="<html:rewrite page='/adm/einfuehrung.do'/>"
					 onclick="window.open(this.href);return false;"
					 onkeypress="window.open(this.href);return false;"
					 title="Einführung <fmt:message key="label.opens.in.new.window"/>">Einführung</a>, um einen kurzen Überblick über die Theorie der Methodik und die Anwendung der Software zu erhalten.</li>
			<li>Unter <html:link action="/adm/customizing/view.do?dispatch=list">Einrichten</html:link> haben Sie die Möglichkeit Fragebögen zu erstellen und die Methodik auf Ihre Bedürfnisse anzupassen.</li>
			<li>Bei <html:link action="/adm/projektadmin.do">Projekte erfassen</html:link> können Sie Projekte, die sie innerhalb einer Projektgruppe vergleichen möchten, definieren.</li>
			<li>Unter <html:link action="/adm/projekte/view.do?dispatch=list">Unter Projekte bewerten</html:link> finden Sie die Fragebögen, die Sie für die Anwendung der Methodik benötigen.</li>
			<li>Unter <html:link action="/adm/auswertungen/projektgruppen.do?dispatch=list">Auswertung</html:link> finden Sie die grafischen Resultate zu den Projektvergleichen.</li>
		</ol>
	</div>

	<div class="clearLeft"></div>

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/projektadmin.do">
					<fmt:message key="label.3.erfassen"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.dritter.schritt.beschreibung"/>
		</div>
	</div>

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/projekte/view.do?dispatch=list">
					<fmt:message key="label.4.bewerten"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.vierter.schritt.beschreibung"/>
		</div>
	</div>

	<div class="clearLeft"></div>

	<div class="mainBox">
		<div class="mainBoxHeader">
			<div class="title">
				<html:link action="/adm/auswertungen/projektgruppen.do?dispatch=list">
					<fmt:message key="label.5.auswertungen"/>
				</html:link>
			</div>
		</div>
		<div class="mainBoxContent">
			<fmt:message key="label.fuenfter.schritt.beschreibung"/>
		</div>
	</div>

	<div class="clearLeft"></div>

</div>

<div id="footer">&nbsp;</div>