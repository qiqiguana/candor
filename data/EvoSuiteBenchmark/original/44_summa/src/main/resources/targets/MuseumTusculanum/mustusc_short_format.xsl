<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns="http://www.openarchives.org/OAI/2.0/"
        xmlns:oai="http://www.openarchives.org/OAI/2.0/"
        xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:Index="http://statsbiblioteket.dk/2004/Index"
		xmlns:xs="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:xalan="http://xml.apache.org/xalan"
		xmlns:java="http://xml.apache.org/xalan/java"
        exclude-result-prefixes="java xs xalan xsl dc oai_dc oai"
		version="1.0" xmlns:dc="http://purl.org/dc/elements/1.1/"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.openarchiv"
				 xmlns:mdc="http://www.mtp.hum.ku.dk/library/uni/sta/oai2v1/"
				xmlns:mtp_dc="http://www.mtp.hum.ku.dk/library/uni/sta/oai2v1/">


    <xsl:output version="1.0" encoding="UTF-8" indent="yes" method="xml"/>
	<xsl:template name="shortformat">
		
					<Index:field Index:name="shortformat" Index:type="stored" Index:freetext="false">
						<xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
						<shortrecord>							
							<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/">
								<rdf:Description>
									
									<xsl:for-each select="metadata">
										<xsl:for-each select="mdc">
											<xsl:for-each select="title">
												<dc:title>
													<xsl:value-of select="."/>
													<xsl:if test="substring(normalize-space(../subtitle[position()=1]),0)!=''">
													<xsl:text>&#32;:&#32;</xsl:text>
													<xsl:value-of select="../subtitle [position()=1]"/>
													</xsl:if>
												
															 <xsl:text>;&#32;</xsl:text>
															 <xsl:if test="../volume!= '' ">
																	 <xsl:value-of select="../volume"/>
														
															 </xsl:if>
															 
															 <xsl:if test="../issue!= '' ">
															 	 <xsl:text>&#32;(</xsl:text>
															 <xsl:value-of select="../issue"/>
															 <xsl:text>)</xsl:text>
															 </xsl:if>
												</dc:title>
											</xsl:for-each>
											<dc:creator>
											<xsl:choose>
										<xsl:when test="substring(author,0)!=''">
										<xsl:value-of select="author[position()=1]"/>
										</xsl:when>
											<xsl:when test="substring(editor,0)!=''">
										<xsl:value-of select="editor[position()=1]"/>
										</xsl:when>
											</xsl:choose>
                                           
                              		</dc:creator>              
											<xsl:for-each select="date">
												<dc:date>
													<xsl:value-of select="."/>
												</dc:date>
											</xsl:for-each>
                                            <xsl:for-each select="year_of_publ">
                                                <dc:date>
                                                    <xsl:value-of select="."/>
                                                </dc:date>
                                            </xsl:for-each>

												<xsl:for-each select=".">
												<dc:type xml:lang="da">netdokument</dc:type>
											</xsl:for-each>
											<xsl:for-each select=".">
												<dc:type xml:lang="en">net document</dc:type>
											</xsl:for-each>
											<xsl:for-each select="identifier">
												<xsl:choose>
													<xsl:when test="starts-with(.,'http://')">
														<dc:identifier>
															<xsl:value-of select="."/>
														</dc:identifier>
													</xsl:when>
												</xsl:choose>
											</xsl:for-each>
                                            <xsl:for-each select="oai_dc:identifier">
                                                <xsl:choose>
                                                    <xsl:when test="starts-with(.,'http://')">
                                                        <dc:identifier>
                                                            <xsl:value-of select="."/>
                                                        </dc:identifier>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </xsl:for-each>
                                              <xsl:for-each select=".">
												<dc:format>todo</dc:format>
											</xsl:for-each>
										</xsl:for-each>
									</xsl:for-each>
								
								
								</rdf:Description>
							</rdf:RDF>
						
						</shortrecord>
						<xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
					</Index:field>
				
	</xsl:template>
</xsl:stylesheet>
