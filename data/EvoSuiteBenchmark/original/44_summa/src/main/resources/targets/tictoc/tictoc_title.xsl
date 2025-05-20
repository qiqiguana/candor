<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:Index="http://statsbiblioteket.dk/2004/Index"
		xmlns:xs="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:xalan="http://xml.apache.org/xalan"
		xmlns:java="http://xml.apache.org/xalan/java"
		exclude-result-prefixes="java xs xalan xsl"
		version="1.0" xmlns:dc="http://purl.org/dc/elements/1.1/"
		xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
        xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
        xmlns:purl="http://purl.org/rss/1.0/"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.openarchiv">
	<xsl:output version="1.0" encoding="UTF-8" indent="yes" method="xml"/>


    <!-- Title -->
    <xsl:template name="title">

        <Index:group Index:name="ti" Index:navn="ti" Index:suggest="true">
            <xsl:for-each select="title">
                <Index:field Index:repeat="true" Index:name="main_titel" Index:navn="ht" Index:type="token" Index:boostFactor="6">
                    <xsl:value-of select="."/>
                </Index:field>
                <xsl:call-template name="sortTitle">
                    <xsl:with-param name="title" select="." />
                    <xsl:with-param name="language" select="../../language" />
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="dc:title">
                <Index:field Index:repeat="true" Index:name="main_titel" Index:navn="ht" Index:type="token" Index:boostFactor="6">
                    <xsl:value-of select="."/>
                </Index:field>
                <xsl:call-template name="sortTitle">
                    <xsl:with-param name="title" select="." />
                    <xsl:with-param name="language" select="'en'" />  <!-- Language harcoded, da det ikke optræder i paagaeldende XML -->
                </xsl:call-template>
            </xsl:for-each>
        </Index:group>

    </xsl:template>

    <xsl:template name="sortTitle">
        <xsl:param name="title"/>
        <xsl:param name="language"/>
        <Index:field Index:name="sort_title" Index:sortLocale="da" Index:navn="sort_titel" Index:type="keyword" Index:boostFactor="6">

    <!--        <xsl:for-each select="$title [position()=1]">    -->
                <xsl:choose>
                    <xsl:when test="starts-with($title,'The ') and (starts-with($language,'en'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'The ') and (starts-with($language,'En'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'A ') and (starts-with($language,'en'))">
                        <xsl:value-of select="substring(.,3)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'A ') and (starts-with($language,'En'))">
                        <xsl:value-of select="substring(.,3)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'An ') and (starts-with($language,'en'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'An ') and (starts-with($language,'En'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'La ') and (starts-with($language,'fr'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'La ') and (starts-with($language,'Fr'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Le ') and (starts-with($language,'fr'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Le ') and (starts-with($language,'Fr'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Les ') and (starts-with($language,'fr'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Les ') and (starts-with($language,'Fr'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Der ') and (contains($language,'de'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Der ') and (starts-with($language,'ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Der ') and (starts-with($language,'Ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Die ') and (contains($language,'de'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Die ') and (starts-with($language,'ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Die ') and (starts-with($language,'Ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Das ') and (contains($language,'de'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Das ') and (starts-with($language,'ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Das ') and (starts-with($language,'Ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Ein ') and (contains($language,'de'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Ein ') and (starts-with($language,'ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Ein ') and (starts-with($language,'Ger'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Eine ') and (contains($language,'de'))">
                        <xsl:value-of select="substring(.,6)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Eine ') and (starts-with($language,'ger'))">
                        <xsl:value-of select="substring(.,6)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Eine ') and (starts-with($language,'Ger'))">
                        <xsl:value-of select="substring(.,6)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Las ') and (contains($language,'es'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Las ') and (starts-with($language,'spa'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Las ') and (starts-with($language,'Spa'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Los ') and (contains($language,'es'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Los ') and (starts-with($language,'spa'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Los ') and (starts-with($language,'Spa'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Un ') and (contains($language,'es'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Un ') and (starts-with($language,'spa'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Un ') and (starts-with($language,'Spa'))">
                        <xsl:value-of select="substring(.,4)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Una ') and (contains($language,'es'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Una ') and (starts-with($language,'spa'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:when test="starts-with($title,'Una ') and (starts-with($language,'Spa'))">
                        <xsl:value-of select="substring(.,5)"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="."/>
                    </xsl:otherwise>
                </xsl:choose>
        <!--    </xsl:for-each>   -->

        </Index:field>

    </xsl:template>

</xsl:stylesheet>