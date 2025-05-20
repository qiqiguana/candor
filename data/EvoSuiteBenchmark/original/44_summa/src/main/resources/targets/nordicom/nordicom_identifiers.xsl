<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:Index="http://statsbiblioteket.dk/2004/Index"
                xmlns:xs="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:java="http://xml.apache.org/xalan/java" xmlns:mc="http://www.loc.gov/MARC21/slim"
                exclude-result-prefixes="java xs xalan xsl"
                version="1.0">

    <xsl:template name="identifiers">
        <xsl:choose>
            <xsl:when test="mc:datafield[@tag='001']/mc:subfield[@code='f']='new'">
                <Index:group Index:name="numbers" Index:navn="nr">
                    <xsl:for-each select="mc:datafield[@tag='021']/mc:subfield[@code='a']">
                        <Index:field Index:name="isbn" Index:navn="ib" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="translate(.,'- ','')"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='021']/mc:subfield[@code='a']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="translate(.,'- ','')"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='558']/mc:subfield[@code='z']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="translate(.,'- ','')"/>
                        </Index:field>
                    </xsl:for-each>
                    <xsl:for-each select="mc:datafield[@tag='021']/mc:subfield[@code='x']">
                        <Index:field Index:name="isbn" Index:navn="ib" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="translate(.,'- ','')"/>
                        </Index:field>
                    </xsl:for-each>
                    <xsl:for-each select="mc:datafield[@tag='021']/mc:subfield[@code='x']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="translate(.,'- ','')"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='440']/mc:subfield[@code='z']">
                        <Index:field Index:name="issn" Index:navn="in" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="."/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='440']/mc:subfield[@code='z']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="."/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='557']/mc:subfield[@code='z']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="."/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='021']/mc:subfield[@code='a']">
                        <Index:field Index:name="isbn" Index:navn="ib" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="java:dk.statsbiblioteket.sbandex.plugins.ISBN.isbnNorm(.)"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='558']/mc:subfield[@code='z']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="java:dk.statsbiblioteket.sbandex.plugins.Normalize.normalize(.)"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='021']/mc:subfield[@code='x']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="java:dk.statsbiblioteket.sbandex.plugins.Normalize.normalize(.)"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='440']/mc:subfield[@code='z']">
                        <Index:field Index:name="issn" Index:navn="in" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="java:dk.statsbiblioteket.sbandex.plugins.Normalize.normalize(.)"/>
                        </Index:field>
                    </xsl:for-each>

                    <xsl:for-each select="mc:datafield[@tag='557']/mc:subfield[@code='z']">
                        <Index:field Index:name="standard_number" Index:navn="is" Index:type="number" Index:boostFactor="6">
                            <xsl:value-of select="java:dk.statsbiblioteket.sbandex.plugins.Normalize.normalize(.)"/>
                        </Index:field>
                    </xsl:for-each>

                </Index:group>
            </xsl:when>

            <!-- Det gamle format -->

            <xsl:otherwise>
                <Index:group Index:name="numbers" Index:navn="nr">
                    <xsl:if test="contains(mc:record/mc:datafield[@tag='150']/mc:subfield[@code='a'],ISSN)">
                        <xsl:variable name="issn" select="normalize-space(substring-after(mc:datafield[@tag='150']/mc:subfield[@code='a'],'ISSN'))"/>
                        <Index:field Index:name="issn" Index:navn="in" Index:type="number" Index:boostFactor="10">
                            <xsl:value-of select="substring($issn,1,9)"/>
                        </Index:field>
                    </xsl:if>
                </Index:group>

            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>