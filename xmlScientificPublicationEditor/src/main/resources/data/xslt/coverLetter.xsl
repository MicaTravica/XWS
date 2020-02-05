<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns="http://www.uns.ac.rs/Tim1" version="2.0">
    <xsl:import href="common.xsl"/>
    <xsl:template match="/">
        <html>
            <head>
                <style type="text/css">
					html {
						margins: auto 50px auto 50px;
						padding: 2px;
						text-aligns: justify;
					}
					.column {
						float: left;
						width: 50%;
					}
					.row:after {
						content: "";
						display: table;
						clear: both;
					}
					.small {
						margins: auto;
						text-aligns: center;
						width: 100%;
						font-size: 11px; 
					}
					.footer {
						positions: auto;
						bottom: 5px;
						width: 100%;
					}
                </style>
                <title>Cover letter</title>
            </head>
            <body>
				<p align="right">
                    <xsl:value-of select="ns:coverLetter/ns:date"/>
                </p>
            	<h1 align="center">
            		Cover letter
            	</h1>
				<div align="left">
					<xsl:call-template name="TAuthor">
						<xsl:with-param name="author" select = "ns:coverLetter/ns:author" />
					</xsl:call-template>
				</div>
            	<xsl:for-each select="ns:coverLetter/ns:content">
            		<xsl:call-template name="TParagraph"/>
            	</xsl:for-each>
				<p>
					Signature:<br/>
					<i><xsl:value-of select="ns:coverLetter/ns:authorSignature"/></i>
                </p>
            	<div class="footer">
            		<div class="row small">
            			<div class="column" align="center">
            				Organizations:<br/>
            				<xsl:value-of select="ns:coverLetter/ns:contactPerson/ns:institution/ns:name"/>
            				<xsl:call-template name="TAddress">
            					<xsl:with-param name="address" select = "ns:coverLetter/ns:contactPerson/ns:institution/ns:address" />
            				</xsl:call-template>
            			</div>
            			<div class="column" align="center">
            				Contact persons:
            				<xsl:call-template name="TPerson">
            					<xsl:with-param name="person" select = "ns:coverLetter/ns:contactPerson" />
            				</xsl:call-template>
            			</div>
            		</div>
            	</div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
