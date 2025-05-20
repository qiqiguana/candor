<xsl:transform version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>

<!-- Transforming parts orders from Incoming to Outgoing structure -->



	<!-- for the document root -->
	<xsl:template match="/PartsOrderIncoming">
		<!-- There is only a records section - no header before. -->
		<PartsOrderOutgoing>
			<!-- There is only a records section - no header before. -->
			<xsl:apply-templates select="Records" />
		</PartsOrderOutgoing>
	</xsl:template>

	<!-- for the records -->
	<xsl:template match="Records">
		<Records>
			<!-- Record order of the source is "structured". 
				 Thus <RecordGroup> group records belonging together. -->
			<xsl:apply-templates select="RecordGroup" />
		</Records>
	</xsl:template>


	
	<!-- for the record groups in the source -->
	<xsl:template match="RecordGroup">
		<xsl:apply-templates select="OrderHeader|OrderLine|UrgentOrderHeader|UrgentOrderLine" />
	</xsl:template>


	
	<!-- for header records of an order -->
	<xsl:template match="OrderHeader|UrgentOrderHeader">
		<OrderHeader>
			<RecordType>S1</RecordType>
			<CustomerNumber>
				<xsl:value-of select="CustomerNumber" />
			</CustomerNumber>
			<DispatchMode>
				<!-- Execute value mapping if not blank -->
				<xsl:if test="normalize-space(DispatchMode)!=''">
					<xsl:element name="XBUS_Stylet">
						<xsl:attribute name="Name">Value</xsl:attribute>
						<xsl:attribute name="Section">DispatchModeIn2Out</xsl:attribute>
						<xsl:attribute name="Key">
							<xsl:value-of select="DispatchMode" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>		
			</DispatchMode>
			<CombinedPacking>
				<!-- Execute value mapping if not blank -->
				<xsl:if test="normalize-space(CombinedPacking)!=''">
					<xsl:element name="XBUS_Stylet">
						<xsl:attribute name="Name">Value</xsl:attribute>
						<xsl:attribute name="Section">CombinedPackingIn2Out</xsl:attribute>
						<xsl:attribute name="Key">
							<xsl:value-of select="CombinedPacking" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>		
			</CombinedPacking>
			<CustomerOrderNumber>
				<xsl:value-of select="CustomerOrderNumber" />
			</CustomerOrderNumber>
		</OrderHeader>
	</xsl:template>

	
	<!-- for line records of an order -->
	<xsl:template match="OrderLine|UrgentOrderLine">
		<OrderLine>
			<RecordType>S2</RecordType>
			<PartNumber>
				<xsl:value-of select="PartNumber" />
			</PartNumber>
			<CustomerNumber>
				<xsl:value-of select="CustomerNumber" />
			</CustomerNumber>
			<CustomerOrderNumber>
				<xsl:value-of select="CustomerOrderNumber" />
			</CustomerOrderNumber>
			<Quantity>
				<xsl:value-of select="Quantity" />
			</Quantity>
			<TextForDealerUse>
				<xsl:value-of select="substring(normalize-space(Remark),0,11)" />
			</TextForDealerUse>
		</OrderLine>
	</xsl:template>
	
	
</xsl:transform>
