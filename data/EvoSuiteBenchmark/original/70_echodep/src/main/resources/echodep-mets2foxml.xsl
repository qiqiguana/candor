<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:mods="http://www.loc.gov/mods/v3"
	xmlns:mets="http://www.loc.gov/METS/"
	xmlns:foxml="info:fedora/fedora-system:def/foxml#"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:local="http://dli.grainger.uiuc.edu/echodep/#"
	xmlns:xlin="http://www.w3.org/1999/xlink"
	exclude-result-prefixes="#all">

<xsl:output method="xml" indent="yes" encoding="utf-8"/>
	
<!-- Fedora PID -->
<xsl:param name="pid"/>

<!-- Temp url -->
<xsl:param name="temp_url">http://localhost:8080/temp/echodep_42</xsl:param>
	
<!-- Output FOXML file path -->
<xsl:param name="fox_file">echodep-foxml.xml</xsl:param>

<!-- DC path  -->
<xsl:param name="dc_file">dublin_core.xml</xsl:param>

<!-- MODS path  -->
<xsl:param name="mods_file">mods.xml</xsl:param>
	
<!-- Master METS file name -->
<xsl:param name="mm_file">mastermets.xml</xsl:param>
	
<!-- Current METS path -->
<xsl:param name="mets_file">echodepmets_0.xml</xsl:param>	


<!-- frequently used values -->
<xsl:variable name="title"><xsl:value-of select="normalize-space(/mets:mets/@LABEL)"/></xsl:variable>

<!-- functions -->
<xsl:template name="substring-after-last">
<xsl:param name="string" />
<xsl:param name="delimiter" />
<xsl:choose>
	<xsl:when test="contains($string, $delimiter)">
		<xsl:call-template name="substring-after-last">
			<xsl:with-param name="string"
				select="substring-after($string, $delimiter)" />
			<xsl:with-param name="delimiter" select="$delimiter" />
		</xsl:call-template>
	</xsl:when>
	<xsl:otherwise><xsl:value-of select="$string" /></xsl:otherwise>
</xsl:choose>
</xsl:template>
	
	
<!-- *******************  -->
<!-- Load Dublin Core  -->
<!-- *******************  -->
<xsl:variable name="dc_var">
	<oai_dc:dc xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/" xmlns:dc="http://purl.org/dc/elements/1.1/">
		<xsl:for-each select="fn:doc($dc_file)/oai_dc:dc/*" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/">
			<xsl:element name="{name(.)}">
				<xsl:value-of select="normalize-space(./text())"/>
			</xsl:element>
		</xsl:for-each>
	</oai_dc:dc>
</xsl:variable>	
	
	
<!-- *******************  -->
<!-- Load MODS  -->
<!-- *******************  -->
<xsl:variable name="mods_var">
	<mods xmlns="http://www.loc.gov/mods/v3">
		<xsl:for-each select="fn:doc($mods_file)/mods:mods/*">
			<xsl:copy-of select="."/>
		</xsl:for-each>
	</mods>
</xsl:variable>	


<!-- *******************  -->
<!-- Generate Master METS FOXML  -->
<!-- *******************  -->
<xsl:template match="/">
<xsl:result-document href="{$fox_file}" method="xml">

<xsl:comment>************************************************************************</xsl:comment>
<xsl:text>
</xsl:text>
<xsl:comment> FEDORA DIGITAL OBJECT ENCODED USING FOXML </xsl:comment>
<xsl:text>
</xsl:text>
<xsl:comment>************************************************************************</xsl:comment>
<xsl:text>
        
</xsl:text>
<foxml:digitalObject xmlns:foxml="info:fedora/fedora-system:def/foxml#" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="info:fedora/fedora-system:def/foxml# http://www.fedora.info/definitions/1/0/foxml1-1.xsd">

	<xsl:attribute name="PID"><xsl:value-of select="$pid"/></xsl:attribute>
	<!-- xsl:attribute name="VERSION">1.1</xsl:attribute -->
	<foxml:objectProperties>
		<foxml:property NAME="info:fedora/fedora-system:def/model#state" VALUE="Active"/>
		<foxml:property NAME="info:fedora/fedora-system:def/model#label" VALUE="{$title}"/>
		<foxml:property NAME="info:fedora/fedora-system:def/model#ownerId" VALUE="fedoraAdmin"/>
		<foxml:property NAME="info:fedora/fedora-system:def/model#createdDate" VALUE="{current-dateTime()}"/>
		<foxml:property NAME="info:fedora/fedora-system:def/view#lastModifiedDate" VALUE="{current-dateTime()}"/>
	</foxml:objectProperties>
	
<xsl:text>
        
</xsl:text>
<xsl:comment>DC Datastream</xsl:comment>
<xsl:text>
</xsl:text>
	<foxml:datastream ID="DC" STATE="A" CONTROL_GROUP="X" VERSIONABLE="true">
		<foxml:datastreamVersion ID="DC.0" MIMETYPE="text/xml" LABEL="Default Dublin Core Record">
			<foxml:xmlContent>
				<xsl:copy-of select="$dc_var"/>
			</foxml:xmlContent>
		</foxml:datastreamVersion>
	</foxml:datastream>
	
<xsl:text>
        
</xsl:text>
<xsl:comment>MODS Datastream</xsl:comment>
<xsl:text>
</xsl:text>
	<foxml:datastream ID="MODS" STATE="A" CONTROL_GROUP="X" VERSIONABLE="true">
		<foxml:datastreamVersion ID="MODS.0" MIMETYPE="text/xml" LABEL="MODS Record">
			<foxml:xmlContent>
				<xsl:copy-of select="$mods_var"/>
			</foxml:xmlContent>
		</foxml:datastreamVersion>
	</foxml:datastream>
	
<xsl:text>
	
</xsl:text>
<xsl:comment>Master METS Datastream</xsl:comment>
<xsl:text>
</xsl:text>
	<foxml:datastream CONTROL_GROUP="M" ID="MasterMETS" STATE="A" VERSIONABLE="true">
		<foxml:datastreamVersion ID="MasterMETS.0" MIMETYPE="application/xml">
			<xsl:attribute name="LABEL"><xsl:value-of select="$mm_file"/></xsl:attribute>
			<foxml:contentLocation REF="{concat($temp_url, $mm_file)}" TYPE="URL"/>
		</foxml:datastreamVersion>
	</foxml:datastream>


<xsl:apply-templates select="/mets:mets/mets:structMap/mets:div/mets:div"/>

<xsl:call-template name="current_mets"/>

</foxml:digitalObject>
</xsl:result-document>
</xsl:template>
	
<xsl:template match="/mets:mets/mets:structMap/mets:div/mets:div">
<xsl:variable name="order" select="./@ORDER"/>	
<xsl:text>
	
</xsl:text>
<xsl:comment>ECHODep METS Datastream, version <xsl:value-of select="$order"/></xsl:comment>
<xsl:text>
</xsl:text>
	<foxml:datastream CONTROL_GROUP="M" ID="METS{$order}" STATE="A" VERSIONABLE="true">
		<foxml:datastreamVersion ID="METS{$order}.0" MIMETYPE="application/xml">
			<xsl:attribute name="LABEL"><xsl:value-of select="./mets:mptr/@xlin:href"/></xsl:attribute>
			<foxml:contentLocation REF="{concat($temp_url,./mets:mptr/@xlin:href)}" TYPE="URL"/>
		</foxml:datastreamVersion>
	</foxml:datastream>
</xsl:template>
	
<xsl:template name="current_mets">
<xsl:variable name="mets" select="fn:doc($mets_file)"/>
<xsl:text>
	
</xsl:text>
<xsl:comment>Content Files</xsl:comment>
<xsl:text>
</xsl:text>
	
<xsl:for-each select="fn:doc($mets_file)/mets:mets/mets:fileSec/mets:fileGrp/mets:file">
<xsl:variable name="filename">
	<xsl:call-template name="substring-after-last">
		<xsl:with-param name="string" select="./mets:FLocat/@xlin:href" />
		<xsl:with-param name="delimiter" select="'/'" />
	</xsl:call-template>
</xsl:variable>
	<foxml:datastream CONTROL_GROUP="M" ID="{./@ID}" STATE="A" VERSIONABLE="true">
		<foxml:datastreamVersion ID="{./@ID}.0" MIMETYPE="{./@MIMETYPE}">
			<xsl:attribute name="LABEL"><xsl:value-of select="$filename"/></xsl:attribute>
			<foxml:contentLocation REF="{concat($temp_url, $filename)}" TYPE="URL"/>
		</foxml:datastreamVersion>
	</foxml:datastream>
<xsl:text>
	
</xsl:text>
</xsl:for-each>
	
</xsl:template>

</xsl:stylesheet>
