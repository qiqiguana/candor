<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
    
<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"  %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" >

	<head>
		<tiles:insert attribute="header" />
	</head>
	
	<body>
		<tiles:insert attribute="heading" />
		<tiles:insert attribute="navigation" />
		<tiles:insert attribute="content" />
	</body>
	
</html>
