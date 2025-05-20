<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:mods="http://www.loc.gov/mods/v3"
    xmlns:date="http://exslt.org/dates-and-times" exclude-result-prefixes="dc mods date">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:param name="userId">1</xsl:param>
        <!-- written by Robert Manaster (& Kyle Rimkus) -->
        <eprints>
            <eprint xmlns="http://eprints.org/ep2/data/2.0">
            <!-- eprints specific parameters;  values to be given at time of transformation -->
            <eprintid/>
            <eprint_status>archive</eprint_status>
            <userid>
                <xsl:value-of select="$userId"/>
            </userid>
            <type>other</type>
            <metadata_visibility>show</metadata_visibility>

            <!--Title-->
            <!--Title, alternative-->
            <xsl:for-each select="//mods:titleInfo">
                <xsl:element name="title">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!--Creator-->
            <xsl:for-each select="//mods:name">
                <xsl:element name="creators">
                    <xsl:element name="item">
                       <xsl:element name="name">
                          <xsl:for-each select="./mods:namePart">
                             <xsl:choose>
                                 <xsl:when test="@type ='given'">
                                    <xsl:element name="given">
                                    <xsl:value-of select="normalize-space(.)"/>
				    </xsl:element>
                                 </xsl:when>
                                 <xsl:when test="@type ='family'">
                                    <xsl:element name="family">
                                    <xsl:value-of select="normalize-space(.)"/>
				    </xsl:element>
				 </xsl:when>
                                 <xsl:otherwise>
                                    <xsl:element name="family">
                                    <xsl:value-of select="normalize-space(.)"/>
				    </xsl:element>
                                 </xsl:otherwise>
                             </xsl:choose> 
                          </xsl:for-each> 
                       </xsl:element>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>

            <!--Place of Publication -->
            <xsl:for-each select="//mods:originInfo/mods:place">
                <xsl:element name="place_of_pub">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!--Date Issued-->
            <xsl:for-each select="//mods:originInfo/mods:dateIssued">
                <xsl:element name="date">
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>

            <!--Frequency-->
            <xsl:for-each select="//mods:originInfo/mods:frequency">
                <xsl:element name="frequency">
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>

            <!--Publisher-->
            <xsl:for-each select="//mods:originInfo/mods:publisher">
                <xsl:element name="publisher">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!--Location, url-->
            <xsl:for-each select="//mods:location/mods:url">
                <xsl:element name="official_url">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!-- Subjects -->
            <xsl:for-each select="//mods:subject">
                <xsl:element name="subjects">
                   <xsl:element name="item">
                      <!--Subject, Name-->
                      <xsl:for-each select="./mods:name">
                         <xsl:value-of select="normalize-space()"/>
                         <xsl:text> -- </xsl:text>
                      </xsl:for-each>
                      <!--Subject, occupation-->
                      <xsl:for-each select="./mods:occupation">
                         <xsl:value-of select="normalize-space()"/>
                         <xsl:text> -- </xsl:text>
                      </xsl:for-each>
                      <!--Subject, Topic-->
                      <xsl:for-each select="./mods:topic">
                         <xsl:value-of select="normalize-space()"/>
                         <xsl:text> -- </xsl:text>
                      </xsl:for-each>
		   </xsl:element>
                </xsl:element>
            </xsl:for-each>

            <!-- Coverage -->
            <xsl:for-each select="//mods:subject/mods:geographic">
                <xsl:element name="field">
                    <xsl:attribute name="name">subjects</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!-- Coverage, coordinates -->
            <xsl:for-each select="//mods:subject/mods:cartographics/mods:coordinates">
                <xsl:element name="field">
                    <xsl:attribute name="name">subjects</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!-- Classification -->
            <xsl:for-each select="//mods:classification">
                <xsl:element name="field">
                    <xsl:attribute name="name">subjects</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!--Abstract-->
            <xsl:for-each select="//mods:abstract">
                <xsl:element name="abstract">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!--Note-->
            <xsl:for-each select="//mods:note">
                <xsl:element name="note">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!--Identifier, issn-->
            <xsl:for-each select="//mods:identifier[@type='issn']">
                <xsl:element name="issn">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
            </xsl:for-each>

            <!-- Supply placeholder node for documents.  Values to be given at time of transformation -->
            <documents/>

          </eprint>
        </eprints>
    </xsl:template>
</xsl:stylesheet>
