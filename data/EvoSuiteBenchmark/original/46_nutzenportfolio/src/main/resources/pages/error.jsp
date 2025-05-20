<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>
<div id="content">

	<div class="largeBoxHeader">
		<div class="title"><fmt:message key="label.fehler"/></div>
	</div>
	
	<div class="largeBoxContent">

		<c:if test='${ !empty requestScope["org.apache.struts.action.ERROR"] }'>
	    <p>
		    <span class="error">
		      <html:messages id="error">
		        <c:out value="${error}" escapeXml="false"/>
		      </html:messages>
		    </span>
	    </p>
		</c:if>
		
		<p><fmt:message key="label.fehler.kontakt"/></p>
		<p><fmt:message key="label.fehler.beschreiben"/></p>
		<p><fmt:message key="label.fehler.information"/></p>
		<%
		if (null == exception) {
			exception = (Throwable) request.getAttribute(
					"org.apache.struts.action.EXCEPTION");
		}
		%>
		<pre style="font-size:12px">
		<%
		if (null == exception) {
			out.write("Fehlerquelle unbekannt.");
		}
		else {
	    java.io.StringWriter sw = new java.io.StringWriter();
	    java.io.PrintWriter pw = new java.io.PrintWriter(sw);
	
	    exception.printStackTrace(pw);
	    out.write(sw.getBuffer().toString());
		}
		%>
		</pre>
	</div>

</div>
