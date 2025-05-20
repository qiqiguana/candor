<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:mods="http://www.loc.gov/mods/v3" 
    xmlns:epdcx="http://purl.org/eprint/epdcx/2006-11-16/"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    exclude-result-prefixes="#all">


    <xsl:output method="xml" indent="yes" encoding="utf-8"/>

    <xsl:template match="/">
        <epdcx:descriptionSet
            xmlns:epdcx="http://purl.org/eprint/epdcx/2006-11-16/"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://purl.org/eprint/epdcx/2006-11-16/ http://purl.org/eprint/epdcx/xsd/2006-11-16/epdcx.xsd">
        
<xsl:text>

</xsl:text>
<xsl:comment>Work</xsl:comment>
<xsl:text>
</xsl:text>
            <epdcx:description>
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type"
                    epdcx:valueURI="http://purl.org/eprint/entityType/ScholarlyWork"/>
                <xsl:apply-templates select="mods:mods/mods:identifier"/>
                <xsl:apply-templates select="mods:mods/mods:titleInfo"/>
                <xsl:apply-templates select="mods:mods/mods:name"/>
                <xsl:apply-templates select="mods:mods/mods:abstract"/>
                <xsl:apply-templates select="mods:mods/mods:subject"/>
                <xsl:apply-templates select="mods:mods/mods:classification"/>
                <epdcx:statement epdcx:propertyURI="http://purl.org/eprint/terms/isExpressedAs"
                    epdcx:valueRef="expression1"/>
            </epdcx:description>

<xsl:text>

</xsl:text>
<xsl:comment>Expression</xsl:comment>
<xsl:text>
</xsl:text>
            <epdcx:description epdcx:resourceId="expression1">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/eprint/entityType/Expression"/>
                <xsl:apply-templates select="mods:mods/mods:genre"/>
                <xsl:apply-templates select="mods:mods/mods:typeOfResource"/>
                <xsl:apply-templates select="mods:mods/mods:language"/>
                <xsl:apply-templates select="mods:mods/mods:relatedItem"/>
                <epdcx:statement epdcx:propertyURI="http://purl.org/eprint/terms/isManifestedAs"
                    epdcx:valueRef="manifestation1"/>
            </epdcx:description>

<xsl:text>

</xsl:text>
<xsl:comment>Manifestation</xsl:comment>
<xsl:text>
</xsl:text>
            <epdcx:description epdcx:resourceId="manifestation1">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type"
                    epdcx:valueURI="http://purl.org/eprint/entityType/Manifestation"/>
                <xsl:apply-templates select="mods:mods/mods:location"/>
                <xsl:apply-templates select="mods:mods/mods:tableOfContents"/>
                <xsl:apply-templates select="mods:mods/mods:note"/>
                <xsl:apply-templates select="mods:mods/mods:originInfo"/>
                <xsl:apply-templates select="mods:mods/mods:physicalDescription"/>
                <xsl:apply-templates select="mods:mods/mods:mimeType"/>
                <xsl:apply-templates select="mods:mods/mods:accessCondition"/>
            </epdcx:description>
        </epdcx:descriptionSet>
    </xsl:template>

    <xsl:template match="mods:titleInfo[not(attribute::type)]">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/title">
            <epdcx:valueString>
                <xsl:value-of select="mods:nonSort"/>
                <xsl:if test="mods:nonSort">
                    <xsl:text> </xsl:text>
                </xsl:if>
                <xsl:value-of select="mods:title"/>
                <xsl:if test="mods:subTitle">
                    <xsl:text>: </xsl:text>
                    <xsl:value-of select="mods:subTitle"/>
                </xsl:if>
                <xsl:if test="mods:partNumber">
                    <xsl:text>. </xsl:text>
                    <xsl:value-of select="mods:partNumber"/>
                </xsl:if>
                <xsl:if test="mods:partName">
                    <xsl:text>. </xsl:text>
                    <xsl:value-of select="mods:partName"/>
                </xsl:if>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template
        match="mods:titleInfo[@type='abbreviated' or @type='uniform' or @type='alternative' or @type='translated']">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/titleAlternative">
            <epdcx:valueString>
                <xsl:value-of select="mods:title"/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:name">
        <xsl:choose>
            <xsl:when
                test="mods:role/mods:roleTerm[@type='text']='creator' or mods:role/mods:roleTerm[@type='code']='cre' ">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/creator">
                    <epdcx:valueString>
                        <xsl:call-template name="name"/>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:when>

            <xsl:otherwise>
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/contributor">
                    <epdcx:valueString>
                        <xsl:call-template name="name"/>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mods:classification">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/subject">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template
        match="mods:subject[mods:topic | mods:name | mods:occupation | mods:geographic | mods:hierarchicalGeographic | mods:cartographics | mods:temporal] ">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/subject">
            <epdcx:valueString>
                <xsl:for-each select="mods:topic">
                    <xsl:value-of select="."/>
                    <xsl:if test="position()!=last()">--</xsl:if>
                </xsl:for-each>

                <xsl:for-each select="mods:occupation">
                    <xsl:value-of select="."/>
                    <xsl:if test="position()!=last()">--</xsl:if>
                </xsl:for-each>

                <xsl:for-each select="mods:name">
                    <xsl:call-template name="name"/>
                </xsl:for-each>
            </epdcx:valueString>
        </epdcx:statement>

        <xsl:for-each select="mods:titleInfo/mods:title">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/subject">
                <epdcx:valueString>
                    <xsl:value-of select="mods:titleInfo/mods:title"/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:for-each select="mods:geographic">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/spacial">
                <epdcx:valueString>
                    <xsl:value-of select="."/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:for-each select="mods:hierarchicalGeographic">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/spacial">
                <epdcx:valueString>
                    <xsl:for-each
                        select="mods:continent|mods:country|mods:provence|mods:region|mods:state|mods:territory|mods:county|mods:city|mods:island|mods:area">
                        <xsl:value-of select="."/>
                        <xsl:if test="position()!=last()">--</xsl:if>
                    </xsl:for-each>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:for-each select="mods:cartographics/*">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/spacial">
                <epdcx:valueString>
                    <xsl:value-of select="."/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:if test="mods:temporal">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/temporal">
                <epdcx:valueString>
                    <xsl:for-each select="mods:temporal">
                        <xsl:value-of select="."/>
                        <xsl:if test="position()!=last()">-</xsl:if>
                    </xsl:for-each>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:if>

        <!-- TODO: Ask Tom -->
        <xsl:if test="*[1][local-name()='topic'] and *[local-name()!='topic']">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/subject">
                <epdcx:valueString>
                    <xsl:for-each
                        select="*[local-name()!='cartographics' and local-name()!='geographicCode' and local-name()!='hierarchicalGeographic'] ">
                        <xsl:value-of select="."/>
                        <xsl:if test="position()!=last()">--</xsl:if>
                    </xsl:for-each>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:if>
    </xsl:template>

    <xsl:template match="mods:abstract">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/abstract">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:tableOfContents">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/tableOfContents">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:note">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/description">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:originInfo">
        <xsl:apply-templates select="*[@point='start']"/>
        <xsl:for-each select="mods:dateIssued[@point!='start' and @point!='end']">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/issued">
                <epdcx:valueString>
                    <xsl:value-of select="."/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:for-each select="mods:dateCreated[@point!='start' and @point!='end']">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/created">
                <epdcx:valueString>
                    <xsl:value-of select="."/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:for-each
            select="mods:dateCaptured[@point!='start' and @point!='end'] | mods:dateOther[@point!='start' and @point!='end']">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/date">
                <epdcx:valueString>
                    <xsl:value-of select="."/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>

        <xsl:for-each select="mods:publisher">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/publisher">
                <epdcx:valueString>
                    <xsl:value-of select="."/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="mods:dateIssued">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/issued">
            <epdcx:valueString>
                <xsl:choose>
                    <xsl:when test="@point='start'">
                        <xsl:value-of select="."/>
                        <xsl:text> - </xsl:text>
                    </xsl:when>
                    <xsl:when test="@point='end'">
                        <xsl:value-of select="."/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="."/>
                    </xsl:otherwise>
                </xsl:choose>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:dateCreated">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/created">
            <epdcx:valueString>
                <xsl:choose>
                    <xsl:when test="@point='start'">
                        <xsl:value-of select="."/>
                        <xsl:text> - </xsl:text>
                    </xsl:when>
                    <xsl:when test="@point='end'">
                        <xsl:value-of select="."/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="."/>
                    </xsl:otherwise>
                </xsl:choose>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:dateOther | mods:dateCaptured">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/date">
            <epdcx:valueString>
                <xsl:choose>
                    <xsl:when test="@point='start'">
                        <xsl:value-of select="."/>
                        <xsl:text> - </xsl:text>
                    </xsl:when>
                    <xsl:when test="@point='end'">
                        <xsl:value-of select="."/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="."/>
                    </xsl:otherwise>
                </xsl:choose>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:genre">
        <xsl:choose>
            <xsl:when test="@authority='dct'">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type">
                    <epdcx:valueString>
                        <xsl:value-of select="."/>
                    </epdcx:valueString>
                </epdcx:statement>
                <xsl:for-each select="mods:typeOfResource">
                    <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type">
                        <epdcx:valueString>
                            <xsl:value-of select="."/>
                        </epdcx:valueString>
                    </epdcx:statement>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type">
                    <epdcx:valueString>
                        <xsl:value-of select="."/>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mods:typeOfResource">
        <xsl:if test="@collection='yes'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Collection"/>
        </xsl:if>
        <xsl:if test=". ='software' and ../mods:genre='database'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/DataSet"/>
        </xsl:if>
        <xsl:if test=".='software' and ../mods:genre='online system or service'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Service"/>
        </xsl:if>
        <xsl:if test=".='software'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Software"/>
        </xsl:if>
        <xsl:if test=".='cartographic material'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Image"/>
        </xsl:if>
        <xsl:if test=".='multimedia'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type"
                epdcx:valueURI="http://purl.org/dc/terms/InteractiveResource"/>
        </xsl:if>
        <xsl:if test=".='moving image'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/MovingImage"/>
        </xsl:if>
        <xsl:if test=".='three-dimensional object'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/PhysicalObject"/>
        </xsl:if>
        <xsl:if test="starts-with(.,'sound recording')">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Sound"/>
        </xsl:if>
        <xsl:if test=".='still image'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/StillImage"/>
        </xsl:if>
        <xsl:if test=". ='text'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Text"/>
        </xsl:if>
        <xsl:if test=".='notated music'">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/type" epdcx:valueURI="http://purl.org/dc/dcmitype/Text"/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="mods:physicalDescription">
        <xsl:if test="mods:extent">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/extent">
                <epdcx:valueString>
                    <xsl:value-of select="mods:extent"/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:if>
        <xsl:if test="mods:form">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/format">
                <epdcx:valueString>
                    <xsl:value-of select="mods:form"/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:if>
        <xsl:if test="mods:internetMediaType">
            <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/medium">
                <epdcx:valueString>
                    <xsl:value-of select="mods:internetMediaType"/>
                </epdcx:valueString>
            </epdcx:statement>
        </xsl:if>
    </xsl:template>

    <xsl:template match="mods:mimeType">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/format">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:identifier">
        <xsl:variable name="type"
            select="translate(@type,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/>
        <xsl:choose>
            <xsl:when test="contains ('isbn issn doi lccn', $type)">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/identifier">
                    <epdcx:valueString>
                        <xsl:value-of select="$type"/>: <xsl:value-of select="."
                        /></epdcx:valueString>
                </epdcx:statement>
            </xsl:when>
            <xsl:when test="contains ('uri', $type)">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/identifier">
                    <epdcx:valueString epdcx:sesURI="http://purl.org/dc/terms/URI">
                        <xsl:value-of select="."/>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:when>
            <xsl:otherwise>
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/identifier">
                    <epdcx:valueString>
                        <xsl:value-of select="."/>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mods:location">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/identifier">
            <epdcx:valueString>
                <xsl:for-each select="mods:url">
                    <xsl:value-of select="."/>
                </xsl:for-each>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:language">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/language">
            <epdcx:valueString>
                <xsl:value-of select="normalize-space(.)"/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template
        match="mods:relatedItem[mods:titleInfo | mods:name | mods:identifier | mods:location]">
        <xsl:choose>
            <xsl:when test="@type='original'">
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/source">
                    <epdcx:valueString>
                        <xsl:for-each
                            select="mods:titleInfo/mods:title | mods:identifier | mods:location/mods:url">
                            <xsl:if test="normalize-space(.)!= ''">
                                <xsl:value-of select="."/>
                                <xsl:if test="position()!=last()">--</xsl:if>
                            </xsl:if>
                        </xsl:for-each>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:when>
            <xsl:when test="@type='series'"/>
            <xsl:otherwise>
                <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/relation">
                    <epdcx:valueString>
                        <xsl:for-each
                            select="mods:titleInfo/mods:title | mods:identifier | mods:location/mods:url">
                            <xsl:if test="normalize-space(.)!= ''">
                                <xsl:value-of select="."/>
                                <xsl:if test="position()!=last()">--</xsl:if>
                            </xsl:if>
                        </xsl:for-each>
                    </epdcx:valueString>
                </epdcx:statement>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mods:accessCondition">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/accessRights">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:targetAudience">
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/audience">
            <epdcx:valueString>
                <xsl:value-of select="."/>
            </epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template name="name">
        <xsl:variable name="name">
            <xsl:for-each select="mods:namePart[not(@type)]">
                <xsl:value-of select="."/>
                <xsl:text> </xsl:text>
            </xsl:for-each>
            <xsl:value-of select="mods:namePart[@type='family']"/>
            <xsl:if test="mods:namePart[@type='given']">
                <xsl:text>, </xsl:text>
                <xsl:value-of select="mods:namePart[@type='given']"/>
            </xsl:if>
            <xsl:if test="mods:namePart[@type='date']">
                <xsl:text>, </xsl:text>
                <xsl:value-of select="mods:namePart[@type='date']"/>
                <xsl:text/>
            </xsl:if>
            <xsl:if test="mods:displayForm">
                <xsl:text> (</xsl:text>
                <xsl:value-of select="mods:displayForm"/>
                <xsl:text>) </xsl:text>
            </xsl:if>
            <xsl:for-each select="mods:role[mods:roleTerm[@type='text']!='creator']">
                <xsl:text> (</xsl:text>
                <xsl:value-of select="normalize-space(.)"/>
                <xsl:text>) </xsl:text>
            </xsl:for-each>
        </xsl:variable>
        <xsl:value-of select="normalize-space($name)"/>
    </xsl:template>

    <xsl:template match="mods:dateIssued[@point='start']">
        <xsl:variable name="dateName" select="local-name()"/>
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/issued">
            <epdcx:valueString>
                <xsl:value-of select="."/>-<xsl:value-of
                    select="../*[local-name()=$dateName][@point='end']"/></epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:dateCreated[@point='start']">
        <xsl:variable name="dateName" select="local-name()"/>
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/terms/created">
            <epdcx:valueString>
                <xsl:value-of select="."/>-<xsl:value-of
                    select="../*[local-name()=$dateName][@point='end']"/></epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:dateCaptured[@point='start'] | mods:dateOther[@point='start'] ">
        <xsl:variable name="dateName" select="local-name()"/>
        <epdcx:statement epdcx:propertyURI="http://purl.org/dc/elements/1.1/date">
            <epdcx:valueString>
                <xsl:value-of select="."/>-<xsl:value-of
                    select="../*[local-name()=$dateName][@point='end']"/></epdcx:valueString>
        </epdcx:statement>
    </xsl:template>

    <xsl:template match="mods:temporal[@point='start']  ">
        <xsl:value-of select="."/>-<xsl:value-of select="../mods:temporal[@point='end']"/>
    </xsl:template>

    <xsl:template match="mods:temporal[@point!='start' and @point!='end']  ">
        <xsl:value-of select="."/>
    </xsl:template>

    <!-- suppress all else:-->
    <xsl:template match="*"/>
</xsl:stylesheet>
