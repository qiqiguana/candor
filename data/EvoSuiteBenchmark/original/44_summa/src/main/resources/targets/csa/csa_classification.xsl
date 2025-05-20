<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:Index="http://statsbiblioteket.dk/2004/Index"
		xmlns:xs="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:xalan="http://xml.apache.org/xalan"
		xmlns:java="http://xml.apache.org/xalan/java"
		exclude-result-prefixes="java xs xalan xsl oai_dc dc"
		version="1.0" xmlns:dc="http://purl.org/dc/elements/1.1/"
		xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.openarchiv">
	<xsl:output version="1.0" encoding="UTF-8" indent="yes" method="xml"/>
	<xsl:template name="classification">
			
			 <Index:group Index:name="cl" Index:navn="cl" >			
		<xsl:for-each select="cl">
		<Index:field Index:repeat="true" Index:name="class_other" Index:navn="klassif" Index:type="token" Index:suggest="true" Index:boostFactor="5">
			<xsl:value-of select="."/>
								</Index:field>
																					</xsl:for-each>
																					
							</Index:group>
        <Index:group Index:suggest="true" Index:navn="lcl" Index:name="lcl">
									<xsl:for-each select="cl">
									<Index:field Index:repeat="false" Index:name="lcl_csa" Index:navn="lcl_csa" Index:type="keyword" Index:boostFactor="10">
									<xsl:value-of select="."/>
									</Index:field>
                                        <Index:field Index:repeat="false" Index:name="lcl_all" Index:navn="lcl_all" Index:type="keyword" Index:boostFactor="10">
									<xsl:value-of select="."/>
									</Index:field>
                                    </xsl:for-each>
							
								</Index:group>
																						
								</xsl:template>

    
</xsl:stylesheet>

