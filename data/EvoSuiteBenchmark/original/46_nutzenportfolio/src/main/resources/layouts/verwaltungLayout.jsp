<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
    
<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" >

	<head>
		<tiles:insert attribute="header" />
	</head>
	
	<body>
		<tiles:insert attribute="heading" />
		<div id="content">
	
			<div class="largeBoxHeader">
				<div class="title">Auswahl</div>
			</div>
			
			<div class="largeBoxContent">
			
				<div class="smallBox">
					<c:url var="url" scope="page" value="/adm/verwaltung/strategischeZiele/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Strategische Ziele</a><br/>
					
					<c:url var="url" scope="page" value="/adm/verwaltung/kategorien/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Kategorien</a>					
				</div>
				
				<div class="smallBox">
					<c:url var="url" scope="page" value="/adm/verwaltung/detailziele/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Detailziele</a><br/>
					
					<c:url var="url" scope="page" value="/adm/verwaltung/nutzenkriterien/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Nutzenkriterien</a>
				</div>
				
				<div class="smallBox">
					<c:url var="url" scope="page" value="/adm/verwaltung/eintrittswahrscheinlichkeiten/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Eintrittswahrscheinlichkeiten</a><br/>
					
					<c:url var="url" scope="page" value="/adm/verwaltung/abstufungen/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Abstufungen</a>
				</div>
				
				<div class="smallBox">
					<c:url var="url" scope="page" value="/adm/verwaltung/eintrittszeitpunkte/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Eintrittszeitpunkte</a><br/>
					
					<c:url var="url" scope="page" value="/adm/verwaltung/gewichtungen/view.do">
			  		<c:param name="dispatch" value="list"/>
			 		</c:url>
					&gt; <a href="${url}">Gewichtungen</a>
				</div>

				<div class="clearLeft"></div>
				
			</div>
			
			<tiles:insert attribute="content" />
			
			<div class="buttons">
				<c:url var="url" scope="page" value="/adm/administration.do"/>
				<form action="${url}">
					<input type="submit" value="<fmt:message key="button.label.zurueck"/>"/>
				</form>
			</div>

		</div>
	</body>
	
</html>
