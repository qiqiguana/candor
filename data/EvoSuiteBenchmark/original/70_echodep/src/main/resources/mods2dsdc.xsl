<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:mods="http://www.loc.gov/mods/v3"
    exclude-result-prefixes="#all">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/">
        <dublin_core>
            <xsl:for-each select="//mods:titleInfo/mods:title">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">title</xsl:attribute>
                    <xsl:attribute name="qualifier">none</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:titleInfo[@type='alternative']">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">title</xsl:attribute>
                    <xsl:attribute name="qualifier">alternative</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:name">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">contributor</xsl:attribute>
                    <xsl:choose>
                        <xsl:when test="mods:role/mods:roleTerm= 'creator'">
                            <xsl:attribute name="qualifier">author</xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:attribute name="qualifier">none</xsl:attribute>
                        </xsl:otherwise>
                    </xsl:choose>
                    <xsl:value-of select="mods:namePart"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:originInfo/mods:dateIssued">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">date</xsl:attribute>
                    <xsl:attribute name="qualifier">issued</xsl:attribute>
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:originInfo/mods:dateCreated">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">date</xsl:attribute>
                    <xsl:attribute name="qualifier">created</xsl:attribute>
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:originInfo/mods:dateCaptured">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">date</xsl:attribute>
                    <xsl:attribute name="qualifier">created</xsl:attribute>
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:originInfo/mods:publisher">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">publisher</xsl:attribute>
                    <xsl:attribute name="qualifier">none</xsl:attribute>
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:physicalDescription/mods:internetMediaType">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">format</xsl:attribute>
                    <xsl:attribute name="qualifier">mimetype</xsl:attribute>
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:physicalDescription/mods:form">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">format</xsl:attribute>
                    <xsl:attribute name="qualifier">none</xsl:attribute>
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:location/mods:url">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">identifier</xsl:attribute>
                    <xsl:attribute name="qualifier">uri</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:subject/mods:topic">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">subject</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:subject/mods:geographic">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">coverage</xsl:attribute>
                    <xsl:attribute name="qualifier">spatial</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:subject/mods:cartographics/mods:coordinates">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">coverage</xsl:attribute>
                    <xsl:attribute name="qualifier">spatial</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:subject/mods:temporal">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">coverage</xsl:attribute>
                    <xsl:attribute name="qualifier">temporal</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:identifier">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">identifier</xsl:attribute>
                    <xsl:choose>
                        <xsl:when test="./@type='local'">
                            <xsl:attribute name="qualifier">other</xsl:attribute>
                        </xsl:when>
                        <xsl:when test="./@type='uri'">
                            <xsl:attribute name="qualifier">uri</xsl:attribute>
                        </xsl:when>
                    </xsl:choose>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:for-each select="//mods:accessCondition">
                <xsl:element name="dcvalue">
                    <xsl:attribute name="element">rights</xsl:attribute>
                    <xsl:attribute name="qualifier">none</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
        </dublin_core>
    </xsl:template>
</xsl:stylesheet>
