<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<div id="header">
	<div class="logo">
		<a href="http://www.bfh.ch"
				onclick="window.open(this.href);return false;"
				onkeypress="window.open(this.href);return false;"
				title="<fmt:message key="label.berner.fachhochschule"/> <fmt:message key="label.opens.in.new.window"/>">
			<img src="<html:rewrite page='/resources/images/logo_bfh.gif'/>" alt="BFH Logo" />
		</a>
	</div>

	<div class="bfh">
		<strong>
			<a href="http://www.bfh.ch"
					onclick="window.open(this.href);return false;"
					onkeypress="window.open(this.href);return false;"
					title="<fmt:message key="label.berner.fachhochschule"/> <fmt:message key="label.opens.in.new.window"/>">
				<fmt:message key="label.berner.fachhochschule"/>
			</a>
		</strong>
	</div>
	<div class="fb">
		<a href="http://www.e-government.bfh.ch"
				onclick="window.open(this.href);return false;"
				onkeypress="window.open(this.href);return false;"
				title="<fmt:message key="label.kompetenzzentrum.egovernment"/> <fmt:message key="label.opens.in.new.window"/>">
			<fmt:message key="label.kompetenzzentrum.egovernment"/>
		</a>
	</div>
</div>

<div id="titlebox">
	<div class="title">
		<span><fmt:message key="application.title"/></span>
		<% if(request.getSession().getAttribute("mandantId") != null) { %>
			<div class="navigation">
				<html:link action="/adm/index.do"><fmt:message key="navigation.header.home"/></html:link>&nbsp;&nbsp;&nbsp;
				<a href="<html:rewrite page='/adm/einfuehrung.do'/>"
					 onclick="window.open(this.href);return false;"
					 onkeypress="window.open(this.href);return false;"
					 title="<fmt:message key="navigation.header.einfuehrung"/> <fmt:message key="label.opens.in.new.window"/>">
					<fmt:message key="navigation.header.einfuehrung"/>
				</a>&nbsp;&nbsp;&nbsp;
				<a href="<html:rewrite page='/adm/administration.do'/>"
					 onclick="window.open(this.href);return false;"
					 onkeypress="window.open(this.href);return false;"
					 title="<fmt:message key="navigation.header.administrationsbereich"/> <fmt:message key="label.opens.in.new.window"/>">
					<fmt:message key="navigation.header.administrationsbereich"/>
				</a>&nbsp;&nbsp;&nbsp;
				<html:link action="/login.do?dispatch=logout"><fmt:message key="navigation.header.abmelden"/></html:link>
			</div>
		<% } %>
	</div>
	<div class="color">&nbsp;</div>
</div>









