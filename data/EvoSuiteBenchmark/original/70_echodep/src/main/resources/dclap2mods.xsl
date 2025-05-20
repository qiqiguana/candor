<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.loc.gov/mods/v3"
    xsi:schemaLocation="http://www.loc.gov/mods/v3
    http://www.loc.gov/standards/mods/v3/mods-3-0.xsd" xmlns:dc="http://purl.org/dc/elements/1.1/"
    >
    <!-- This template translates DSPACE DC-LAP records to MODS.
        For mapping table, see the documentation.  -->
    <!-- written by Kyle Rimkus, May 2006. -->
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/">
        <mods xsi:schemaLocation="http://www.loc.gov/mods/v3
            http://www.loc.gov/standards/mods/v3/mods-3-0.xsd">
            <!-- Title -->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'title'] [@qualifier='none']">
                <xsl:element name="titleInfo">
                    <xsl:element name="title">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!-- Title, Alternative -->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'title'] [@qualifier =
                'alternative'                 ]">
                <xsl:element name="titleInfo">
                    <xsl:attribute name="type">alternative</xsl:attribute>
                    <xsl:element name="title">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!--Creator, Personal -->
            <xsl:for-each select="dublin_core / dcvalue[@element='contributor']
                [@qualifier='author']">
                <xsl:element name="name">
                    <xsl:element name="namePart">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                    <xsl:element name="role">
                        <xsl:element name="roleTerm">
                            <xsl:attribute name="type">text</xsl:attribute>creator</xsl:element>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!--Contributor, Personal -->
            <xsl:for-each select="dublin_core / dcvalue[@element='contributor']
                [@qualifier='none']">
                <xsl:element name="name">
                    <xsl:element name="namePart">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                    <xsl:element name="role">
                        <xsl:element name="roleTerm">
                            <xsl:attribute name="type">text</xsl:attribute>contributor</xsl:element>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!--Resource Type-->
            <!--Genre-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'type' ]">
                <xsl:element name="extension">
                    <xsl:element name="dc:type">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <xsl:if test="dublin_core / dcvalue[@element = 'publisher'] or dublin_core /
                dcvalue[@element = 'date'] [qualifier =                 'issued' ] or dublin_core /
                dcvalue[@element = 'date'] [qualifier =                 'created' ] or dublin_core /
                dcvalue[@element = 'date']">
                <xsl:element name="originInfo">
                    <!--Publisher-->
                    <xsl:for-each select="dublin_core / dcvalue[@element = 'publisher']">
                        <xsl:element name="publisher">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                    <!--Date Issued-->
                    <xsl:for-each select="dublin_core / dcvalue[@element = 'date'] [qualifier =
                        'issued' ]">
                        <xsl:element name="dateIssued">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                    <!--Date Created-->
                    <xsl:for-each select="dublin_core / dcvalue[@element = 'date'] [qualifier =
                        'created' ]">
                        <xsl:element name="dateCreated">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                    <!--Copyright Date-->
                    <xsl:for-each select="dublin_core / dcvalue[@element = 'date'] [qualifier =
                        'copyright' ]">
                        <xsl:element name="copyrightDate">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                    <!--Date Captured-->
                    <!--Date Modified-->
                   
                    <!--Date, Other-->
                    <xsl:for-each select="dublin_core / dcvalue[@element =
                        'date'][@qualifier='none']">
                        <xsl:element name="dateOther">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:if>
            <!--Language, text-->
            <!--Language, ISO code-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'language']">
                <xsl:element name="language">
                    <xsl:element name="languageTerm">
                        <xsl:attribute name="type">text</xsl:attribute>
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <xsl:if test="dublin_core / dcvalue[@element = 'format' ] [@qualifier = 'extent'] or
                dublin_core / dcvalue[@element = 'format' ] [@qualifier = 'mimetype'] or dublin_core
                / dcvalue[@element = 'format' ][@qualifier='none'] ">
                <xsl:element name="physicalDescription">
                    <!--Extent-->
                    <xsl:for-each select="dublin_core / dcvalue[@element = 'format' ] [@qualifier =
                        'extent']">
                        <xsl:element name="extent">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                    <!--Mimetype-->
                    <xsl:for-each select="  dublin_core / dcvalue[@element = 'format' ] [@qualifier
                        = 'mimetype']">
                        <xsl:element name="internetMediaType">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                    <!--Physical Form-->
                    <xsl:for-each select="dublin_core                         / dcvalue[@element =
                        'format' ] [@qualifier = 'none' ]">
                        <xsl:element name="form">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:element>
            </xsl:if>
            <!--Abstract-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'description'] [@qualifier =
                'abstract']">
                <xsl:element name="abstract">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Table of Contents-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'description'] [@qualifier =
                'tableofcontents']">
                <xsl:element name="tableOfContents">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Note-->
            <xsl:for-each select="dublin_core / dcvalue[@element =
                'description'][@qualifier='none']">
                <xsl:element name="note">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Subject, Topic-->
            <!--Subject, Name-->
            <!--Subject, Occupation-->
            <!--Classification, Unspecified-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'subject'][@qualifier='none']">
                <xsl:element name="subject">
                    <xsl:element name="topic">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!--Subject, geographic-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'subject'] [@qualifier =
                'spatial' ]">
                <xsl:element name="subject">
                    <xsl:element name="geographic">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!--Subject, temporal-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'subject'] [@qualifier =
                'temporal'                 ]">
                <xsl:element name="subject">
                    <xsl:element name="temporal">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
            <!--Subject, LCSH-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'subject'] [@qualifier = 'lcsh']">
                <xsl:element name="subject">
                    <xsl:attribute name="authority">lcsh</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Classification, LCC-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'subject'] [@qualifier = 'lcc']">
                <xsl:element name="subject">
                    <xsl:attribute name="authority">lcc</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Classification, DDC-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'subject'] [@qualifier = 'ddc']">
                <xsl:element name="subject">
                    <xsl:attribute name="authority">ddc</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Identifier, uri-->
            <!--Identifier, handle-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'identifier'] [@qualifier =
                'uri']">
                <xsl:element name="identifier">
                    <xsl:attribute name="type">uri</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Identifier, isbn-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'identifier'] [@qualifier =
                'isbn']">
                <xsl:element name="identifier">
                    <xsl:attribute name="type">isbn</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Identifier, issn-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'identifier'] [@qualifier =
                'issn']">
                <xsl:element name="identifier">
                    <xsl:attribute name="type">issn</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Identifier, ismn-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'identifier'] [@qualifier =
                'ismn']">
                <xsl:element name="identifier">
                    <xsl:attribute name="type">ismn</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Identifier, sici-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'identifier'] [@qualifier =
                'sici']">
                <xsl:element name="identifier">
                    <xsl:attribute name="type">sici</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!-- Identifier, local -->
            <!--Location, url-->
            <xsl:for-each select="dublin_core / dcvalue [@element = 'identifier'] [@qualifier =
                'none']">
                <xsl:element name="identifier">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
            <!--Access Condition-->
            <xsl:for-each select="dublin_core / dcvalue[@element = 'rights' ]">
                <xsl:element name="accessCondition">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>
        </mods>
    </xsl:template>
</xsl:stylesheet>