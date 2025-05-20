<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<html:xhtml/>

<div id="narrowContent">

	<script>
		function radioWert(rObj) {
			for(var i=0; i<rObj.length; i++)
				if(rObj[i].checked)
					return rObj[i].value;
			return false;
		}

		function checkForm(theForm) {
			if(radioWert(theForm.weiter) == "ende")
				return confirm('<fmt:message key="label.nicht.komplett"/>');
			return true;
		}
	</script>

	<html:form styleId="opnuForm" action="/adm/kategorien/view.do" onsubmit="return checkForm(this);">
		<input type="hidden" name="dispatch" id="dispatch" value="prepare"/>
		<input type="hidden" name="opNu" value="true"/>
       	
		<div class="largeBoxHeader">
			<div class="title"><fmt:message key="label.customizing.operativer.nutzen"/></div>
		</div>
		
		<div class="largeBoxContent">
			<h1><fmt:message key="label.beschreibung"/></h1>
			<p>
				<fmt:message key="label.operativer.nutzen.beschreibung.1"/>
			</p>
			<p>
				<fmt:message key="label.operativer.nutzen.beschreibung.2"/>
			</p>
			<p>
				<input type="radio" name="weiter" value="weiter" class="checkbox" onclick="document.getElementById('opnuForm').action='<html:rewrite page='/adm/kategorien/view.do'/>'; document.getElementById('dispatch').value='prepare';" checked="checked"/>
				<fmt:message key="label.operativer.nutzen.radio.weiter"/><br />
				<input type="radio" name="weiter" value="ende" class="checkbox" onclick="document.getElementById('opnuForm').action='<html:rewrite page='/adm/customizing/view.do'/>'; document.getElementById('dispatch').value='list';"/>
				<fmt:message key="label.operativer.nutzen.radio.ende"/>
			</p>
		</div>
		
		<div class="buttons">
			<c:url var="url" scope="page" value="/adm/gewichtungstyp.do">
	    	<c:param name="dispatch" value="select"/>
	    	<c:param name="opNu" value="false"/>
      </c:url>
			<input
					type="button"
					class="button"
					value="<fmt:message key="button.label.zurueck"/>"
					onclick="window.location='<c:out value="${url}"/>'"/>
			<input
					type="submit"
					class="button"
					name="next"
					value="<fmt:message key="button.label.weiter"/>"/>
		</div>
		
	</html:form>
	
</div>