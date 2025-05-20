<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:variable name="currentPage" select="//currentPage"/>
	<xsl:variable name="totalPages" select="//totalPages"/> 
	
	<!-- ##################################### -->
	<!-- HTML Result -->
	<!-- ##################################### -->
	<!-- 
	<div class='pageNum'>Page 1 of 4</div>
	<div class='pageNavArea'>
		<ul>
			<li class='pageNavFirst'>
				<a href='/videos.do'><span>First</span></a>
			</li>
			<li class='pageNavPrev'>
				<a href='/videos.do'><span>Prev</span></a>
			</li>
			<li>
				<a href='/videos.do'><span>1</span></a>
			</li>
			<li class='current'>
				<a href='/videos.do?&from=11&to=20'><span>2</span></a>
			</li>
			<li>
				<a href='/videos.do?&from=21&to=30'><span>3</span></a>
			</li>
			<li>
				<a href='/videos.do?&from=31&to=40'><span>4</span></a>
			</li>
			<li class='pageNavNext'>
				<a href='/videos.do'><span>Next</span></a>
			</li>
			<li class='pageNavLast'>
				<a href='/videos.do?&from=31&to=40'><span>Last</span></a>
			</li>
		</ul>
	</div>
	-->	
	<!-- ##################################### -->
	<!-- CSS Sample -->
	<!-- ##################################### -->
	<!--
	.pageNum {
		float: left;
		width: 200px;
		color: #000;
		padding: 2px 0;
		margin: 0 12px;
		line-height: 1.2em;	
	}

	.pageNavArea {
		float: right;
		width: 250px;
		padding: 2px 0;
		margin: 0 12px;
		line-height: 1.2em;
	}

	.pageNavArea ul {
		float: right;
		margin: 0;
	}

	.pageNavArea ul li {
		list-style: none;
		float: left;
		display: inline;
		position: relative;
		padding-left: 8px;
		margin: 0;
	}

	.pageNav a:link, .pageNav a:active, .pageNav a:visited {
		display: block;
		width: 16px;
		text-align: center;
		color: #0F0;
		text-decoration: underline;
	}

	.pageNav a:hover {
		text-decoration: none;
	}

	.pageNav .current a {
		width: 14px;
		background: #FFF;
		border: 1px solid #CCC;
		text-decoration: none;
		color: #0F0;
	}

	.pageNav .pageNavPrev a,
	.pageNav .pageNavNext a {
		width: 22px;
		text-align: left;
		color: #0F0;
	}
	
	.pageNav .pageNavNext a { text-align: right; }
	
	
	 -->
	
	<xsl:template match="/">
		<div class="pageNum">Page <xsl:value-of select="$currentPage"/> of <xsl:value-of select="$totalPages"/></div>
		<div class="pageNavArea">
			<ul>
				<xsl:apply-templates select="//firstPage"/>
				<xsl:apply-templates select="//previousPage"/>
				<xsl:apply-templates select="//page"/>				
				<xsl:apply-templates select="//nextPage"/>
				<xsl:apply-templates select="//lastPage"/>
			</ul>
		</div>
	</xsl:template>
	
	<xsl:template match="page">		
		<li>
			<xsl:if test="$currentPage = @n">
				<xsl:attribute name="class">current</xsl:attribute>
			</xsl:if>
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="./url/text()"/>
				</xsl:attribute>
				<span><xsl:value-of select="@n"/></span>
			</a>
		</li>
	</xsl:template>	
	
	<xsl:template match="firstPage">
		<li class="pageNavFirst">
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="./url/text()"/>
				</xsl:attribute>
				<span>First</span>
			</a>
		</li>
	</xsl:template>
	
	<xsl:template match="previousPage">
		<li class="pageNavPrev">
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="./url/text()"/>
				</xsl:attribute>
				<span>Prev</span>
			</a>
		</li>
	</xsl:template>
	
	<xsl:template match="nextPage">
		<li class="pageNavNext">
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="./url/text()"/>
				</xsl:attribute>
				<span>Next</span>
			</a>
		</li>
	</xsl:template>
	
	<xsl:template match="lastPage">
		<li class="pageNavLast">
			<a>
				<xsl:attribute name="href">
					<xsl:value-of select="./url/text()"/>
				</xsl:attribute>
				<span>Last</span>
			</a>
		</li>
	</xsl:template>
		
</xsl:stylesheet>