<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<div id="content">

	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.einfuehrung"/></div>
	</div>
	
	<div class="largeBoxContent">

		<h1><fmt:message key="label.theorie"/></h1>
		<p>
			&gt; <a href="<html:rewrite page='/resources/files/Theorie_NPA.pdf'/>" target="_blank">
				<fmt:message key="label.theorie.pdf"/>
			</a>
		</p>
		
		<h1><fmt:message key="label.softwareunterstuetzung"/></h1>
		<p>
			&gt; <a href="<html:rewrite page='/resources/files/Erlaeuterung_Software.pdf'/>" target="_blank">
				<fmt:message key="label.softwareunterstuetzung.pdf"/>
			</a>
		</p>
			
	</div>
	
	<div class="buttons">
		<form>
			<input type="button" value="schliessen" onclick="self.close();"/>
		</form>
	</div>
</div>
<script>
	self.focus();
</script>