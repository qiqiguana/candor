<xsl:stylesheet version="2.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:fn="http://www.w3.org/2005/xpath-functions" 
        xmlns:mods="http://www.loc.gov/mods/v3"
        xmlns:mets="http://www.loc.gov/METS/" 
        xmlns:epdcx="http://purl.org/eprint/epdcx/2006-11-16/"
        xmlns:xs="http://www.w3.org/2001/XMLSchema" 
        xmlns:local="http://dli.grainger.uiuc.edu/echodep/#"
        xmlns:xlink="http://www.w3.org/1999/xlink" 
        exclude-result-prefixes="#all">

    <xsl:output method="xml" indent="yes" encoding="utf-8"/>

    <!-- Identifier -->
    <xsl:param name="id"/>

    <!-- Output METS file path -->
    <xsl:param name="out_filename">mets.xml</xsl:param>

    <!-- SWAP path  -->
    <xsl:param name="swap_file">swap.xml</xsl:param>

    <!-- Master METS file name -->
    <xsl:param name="mm_file">mastermets.xml</xsl:param>

    <!-- Current METS path -->
    <xsl:param name="mets_file">echodepmets_0.xml</xsl:param>


    <!-- frequently used values -->
    <xsl:variable name="title">
        <xsl:value-of select="normalize-space(/mets:mets/@LABEL)"/>
    </xsl:variable>

    <!-- functions -->
    <xsl:template name="substring-after-last">
        <xsl:param name="string"/>
        <xsl:param name="delimiter"/>
        <xsl:choose>
            <xsl:when test="contains($string, $delimiter)">
                <xsl:call-template name="substring-after-last">
                    <xsl:with-param name="string" select="substring-after($string, $delimiter)"/>
                    <xsl:with-param name="delimiter" select="$delimiter"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$string"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <!-- *******************  -->
    <!-- Load SWAP metadata  -->
    <!-- *******************  -->
    <xsl:variable name="swap_var">
        <descriptionSet xmlns="http://purl.org/eprint/epdcx/2006-11-16/">
            <xsl:for-each select="fn:doc($swap_file)/epdcx:descriptionSet/*">
                <xsl:copy-of select="."/>
            </xsl:for-each>
        </descriptionSet>
    </xsl:variable>


    <!-- *******************  -->
    <!-- Generate SWORD METS  -->
    <!-- *******************  -->
    <xsl:template match="/">
        <xsl:result-document href="{$out_filename}" method="xml">

<xsl:comment>*********************************************************************************</xsl:comment>
<xsl:text>
</xsl:text>
<xsl:comment> SWORD METS with Scholarly Works Application Profile (SWAP) Metadata </xsl:comment>
<xsl:text>
</xsl:text>
<xsl:comment>*********************************************************************************</xsl:comment>
<xsl:text>

</xsl:text>
            <mets xmlns="http://www.loc.gov/METS/"
                OBJID="{$id}"
                LABEL="{$title}"
                PROFILE="DSpace METS SIP Profile 1.0" 
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd">

                <metsHdr CREATEDATE="{current-dateTime()}" LASTMODDATE="{current-dateTime()}"/>

                <dmdSec ID="dmdSec1">
                    <mdWrap LABEL="SWAP Metadata" MDTYPE="OTHER" OTHERMDTYPE="EPDCX" MIMETYPE="text/xml">
                        <xmlData>
                            <xsl:copy-of select="$swap_var"/>
                        </xmlData>
                    </mdWrap>
                </dmdSec>
<xsl:text>
    
</xsl:text>                
                <fileSec>
                    <fileGrp USE="CONTENT">
                        <file ID="{$mm_file}">
                            <FLocat LOCTYPE="URL" xlink:href="{$mm_file}" />
                        </file>
<xsl:text>
    
</xsl:text>                        
                        <xsl:for-each select="/mets:mets/mets:structMap/mets:div/mets:div">
                            <xsl:variable name="filename">
                                <xsl:call-template name="substring-after-last">
                                    <xsl:with-param name="string" select="./mets:mptr/@xlink:href"/>
                                    <xsl:with-param name="delimiter" select="'/'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            
                            <file ID="{$filename}">
                                <FLocat LOCTYPE="URL" xlink:href="{$filename}" />
                            </file>
                        </xsl:for-each>
<xsl:text>
    
</xsl:text>                       
                        <xsl:for-each select="fn:doc($mets_file)/mets:mets/mets:fileSec/mets:fileGrp/mets:file">
                            <xsl:variable name="filename">
                                <xsl:call-template name="substring-after-last">
                                    <xsl:with-param name="string" select="./mets:FLocat/@xlink:href"/>
                                    <xsl:with-param name="delimiter" select="'/'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            
                            <file ID="{$filename}">
                                <FLocat LOCTYPE="URL" xlink:href="{$filename}" />
                            </file>
<xsl:text>

</xsl:text>
                        </xsl:for-each>
                    </fileGrp>
                </fileSec>
<xsl:text>
    
</xsl:text>                
                <structMap>
                    <div DMDID="dmdSec1">
                        <div>
                            <fptr FILEID="{$mm_file}"/>
                        </div>
<xsl:text>

</xsl:text>       
                        <xsl:for-each select="/mets:mets/mets:structMap/mets:div/mets:div">
                            <xsl:variable name="filename">
                                <xsl:call-template name="substring-after-last">
                                    <xsl:with-param name="string" select="./mets:mptr/@xlink:href"/>
                                    <xsl:with-param name="delimiter" select="'/'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            
                            <div>
                                <fptr FILEID="{$filename}"/>
                            </div>
                        </xsl:for-each>
<xsl:text>

</xsl:text>       
                        <xsl:for-each select="fn:doc($mets_file)/mets:mets/mets:fileSec/mets:fileGrp/mets:file">
                            <xsl:variable name="filename">
                                <xsl:call-template name="substring-after-last">
                                    <xsl:with-param name="string" select="./mets:FLocat/@xlink:href"/>
                                    <xsl:with-param name="delimiter" select="'/'"/>
                                </xsl:call-template>
                            </xsl:variable>
                            
                            <div>
                                <fptr FILEID="{$filename}"/>
                            </div>
<xsl:text>

</xsl:text>
                        </xsl:for-each>
                    </div>
                </structMap>
            </mets>
        </xsl:result-document>
    </xsl:template>

</xsl:stylesheet>
