<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ep="http://eprints.org/ep2/data/2.0"
    xsi:schemaLocation="http://www.loc.gov/mods/v3
    http://www.loc.gov/standards/mods/v3/mods-3-0.xsd">

    <!-- This template translates eprints records to MODS.

        For mapping table, see the documentation.  -->

    <!-- written by Robert Manaster, April 2008 -->

    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">

        <mods
            xsi:schemaLocation="http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-2.xsd"
            xmlns="http://www.loc.gov/mods/v3">

            <!-- Title -->
            <titleInfo>
            <xsl:for-each select="eprints/ep:eprint/ep:title">
                <xsl:element name="title">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element> 
            </xsl:for-each>
            </titleInfo>
            
            <!--Creator, Personal -->
            <xsl:for-each select="eprints/ep:eprint/ep:creators/ep:item/ep:name">
                <xsl:element name="name">
                    <xsl:attribute name="type">personal</xsl:attribute>
                    
                    <xsl:element name="namePart">
                            <xsl:attribute name="type">given</xsl:attribute>
                        <xsl:if test="name(ep:given)">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:if>
                    </xsl:element>
                    <xsl:element name="namePart">
                           <xsl:attribute name="type">family</xsl:attribute>
                        <xsl:if test="name(ep:family)">
                            <xsl:value-of select="normalize-space(.)"/>
                        </xsl:if>
                    </xsl:element>

                    <role>
                        <roleTerm type="text">author</roleTerm>
                    </role>

                </xsl:element>
            </xsl:for-each>

            <!-- Abstract -->
            <xsl:for-each select="eprints/ep:eprint/ep:abstract">
                    <xsl:element name="abstract">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element> 
                </xsl:for-each>

            <!-- Subjects.... assuming lcc -->
            <xsl:for-each select="eprints/ep:eprint/ep:subjects">
                <xsl:element name="classification">
                    <xsl:attribute name="authority">lcc</xsl:attribute>
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element> 
            </xsl:for-each>

            <!-- Issue Date -->
            <xsl:for-each select="eprints/ep:eprint/ep:date">
                <originInfo>
                    <xsl:element name="dateIssued">
                        <xsl:attribute name="encoding">iso8061</xsl:attribute>
                        <!--  $value should be  =~ s/(-0+)+$// ;  -->
                        <xsl:value-of select="normalize-space(.)"/>
                </xsl:element>
               </originInfo>
            </xsl:for-each>
 
            <!-- Publisher -->
           <!-- 
               my $type = lc($dataobj->get_value( "type" ));
               if( $type eq "thesis" and $dataobj->is_set( "institution" ) )
               {
               $val = $dataobj->get_value( "institution" );
               if( $dataobj->is_set( "department" ))
               {
               $val .= ";" . $dataobj->get_value( "department" );
               }
               }
               else
               {
               $val = $dataobj->get_value( "publisher" );
               }
           -->
            <xsl:for-each select="eprints/ep:eprint/ep:publisher">
                <originInfo>
                    <xsl:element name="publisher">
                        <xsl:value-of select="normalize-space(.)"/>
                    </xsl:element>
                </originInfo>
            </xsl:for-each>
            
            <!-- Genre -->
            <xsl:for-each select="eprints/ep:eprint/ep:type">
                <xsl:element name="genre">
                    <xsl:value-of select="normalize-space(.)"/>
                </xsl:element> 
            </xsl:for-each>
            
            <!--Place of Publication-->

            <xsl:for-each select="eprintsdata / record / field[@name='place_of_pub']">

                <xsl:element name="originInfo">

                    <xsl:element name="place">

                        <xsl:element name="placeTerm">

                            <xsl:value-of select="normalize-space(.)"/>

                        </xsl:element>

                    </xsl:element>

                </xsl:element>

            </xsl:for-each>

            <!--Resource Type-->

            <xsl:if test="eprintsdata / record / field[@name = 'publisher'] or eprintsdata / record / field[@name =

                'date_issue'] or eprintsdata / record / field[@name = 'frequency'] or eprintsdata / record /

                field[@name = 'datestamp']">

                <xsl:element name="originInfo">

                    <!--Publisher-->

                    <xsl:for-each select="eprintsdata / record / field[@name = 'publisher']">

                        <xsl:element name="publisher">

                            <xsl:value-of select="normalize-space(.)"/>

                        </xsl:element>

                    </xsl:for-each>

                    <!--Date Issued-->

                    <xsl:for-each select="eprintsdata / record / field[@name = 'date_issue']">

                        <xsl:element name="dateIssued">

                            <xsl:value-of select="normalize-space(.)"/>

                        </xsl:element>

                    </xsl:for-each>

                    <!--Date Captured-->

                    <xsl:for-each select="eprintsdata / record / field[@name = 'datestamp']">

                        <xsl:element name="dateCaptured">

                            <xsl:value-of select="normalize-space(.)"/>

                        </xsl:element>

                    </xsl:for-each>

                    <!--Frequency of Publication-->

                    <xsl:for-each select="eprintsdata / record / field[@name = 'frequency']">

                        <xsl:element name="frequency">

                            <xsl:value-of select="normalize-space(.)"/>

                        </xsl:element>

                    </xsl:for-each>

                </xsl:element>

            </xsl:if>

            <!--Note-->

            <xsl:for-each select="eprintsdata / record / field[@name = 'note']">

                <xsl:element name="note">

                    <xsl:value-of select="normalize-space(.)"/>

                </xsl:element>

            </xsl:for-each>

            <!--Subject, Topic-->

            <!--Subject, Name-->

            <!--Subject, Occupation-->

            <!--Classification, Unspecified-->

            <!--Subject, geographic-->

            <!--Subject, temporal-->

            <!--Subject, LCSH-->

            <!--Classification, DDC-->

            <!--Classification, LCC-->

            <xsl:for-each select="eprintsdata / record / field[@name = 'subjects']">

                <xsl:element name="subject">

                    <xsl:element name="topic">

                        <xsl:value-of select="normalize-space(.)"/>

                    </xsl:element>

                </xsl:element>

            </xsl:for-each>

            <!--Identifier, issn-->

            <xsl:for-each select="eprintsdata / record / field [@name = 'issn']">

                <xsl:element name="identifier">

                    <xsl:attribute name="type">issn</xsl:attribute>

                    <xsl:value-of select="normalize-space(.)"/>

                </xsl:element>

            </xsl:for-each>

            <!--Location, url-->

            <xsl:for-each select="eprintsdata / record / field[@name = 'official_url']">

                <xsl:element name="location">

                    <xsl:element name="url">

                        <xsl:value-of select="normalize-space(.)"/>

                    </xsl:element>

                </xsl:element>

            </xsl:for-each>

        </mods>

    </xsl:template>

</xsl:stylesheet>

