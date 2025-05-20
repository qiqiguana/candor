<xsl:transform version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>

<!-- Transforming list of companies with their employees to list of names -->

	<xsl:template match="/Address">
		<NameList>
			<xsl:apply-templates select="Company/Employee" />
		</NameList>
	</xsl:template>

	
	<xsl:template match="Company/Employee">
	<Person>
		<xsl:value-of select="Name" />
	</Person>
	</xsl:template>
	
	
</xsl:transform>
