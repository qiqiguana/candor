<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

<xsl:param name="title"/>

<xsl:param name="rowlen"/>
                
<xsl:template match="/">
<html>
  <head>
    <title><xsl:value-of select="$title"/></title>
  </head>

  <body>
    <h1><xsl:value-of select="$title"/></h1>
	<table>
	<xsl:for-each select="dirlist/file[(position()-1) mod $rowlen = 0]">
    <tr>
        <xsl:variable name="thisPos" select="1+(position()-1)*$rowlen"/>
        <xsl:for-each select="../file[(position() >= $thisPos) and (position() &lt; $thisPos+$rowlen)]">
        <td><a href="images/{@filename}"><img border="0" src="thumbnails/{@filename}" 
            alt=""/><br/><xsl:value-of select="@date"/><xsl:text> </xsl:text> <xsl:value-of select="substring(@time, 1, 5)"/></a>
        </td>
        </xsl:for-each>
    </tr>
    </xsl:for-each>    
	</table>
  </body>
</html>
</xsl:template>

</xsl:stylesheet>

