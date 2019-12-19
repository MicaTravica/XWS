<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">
    <xsl:import href="common.xsl"/>
    <xsl:template match="/">
        <html>
            <head>
                <style type="text/css">
					html {
						margin: auto 50px auto 50px;
						padding: 2px;
						text-align: justify;
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
						margin: auto;
						text-align: center;
						width: 50%;
						font-size: 11px; 
					}
                </style>
                <title>Cover letter</title>
            </head>
            <body>
				<p align="right">
                    <xsl:value-of select="n:coverLetter/@date"/>
                </p>
            	<h1 align="center">Cover letter for <xsl:value-of select="n:coverLetter/@scientificPublication"/></h1>
				
				<div class="row">
					<div class="column" align="center">
						Authors:<br/>
						<xsl:value-of select="n:coverLetter/@authors"/>
					</div>
					<div class="column" align="center">
						Reviewer:
						<xsl:call-template name="TPerson">
							<xsl:with-param name="person" select = "n:coverLetter/n:author" />
						</xsl:call-template>
					</div>
				</div>
				<xsl:for-each select="n:coverLetter/n:content">
						<xsl:call-template name="TChapter">
							<xsl:with-param name="paragraph" select = "." />
						</xsl:call-template>
				</xsl:for-each>
				<p>
					Signature:<br/>
					<i><xsl:value-of select="n:coverLetter/n:authorSignature"/></i>
                </p>
            	<div class="row small">
            		<div class="column" align="center">
            			Organization:<br/>
            			<xsl:value-of select="n:coverLetter/n:contactInformation/n:organisationName"/>
            			<xsl:call-template name="TAddress">
            				<xsl:with-param name="address" select = "n:coverLetter/n:contactInformation/n:organisationAddress" />
            			</xsl:call-template>
            		</div>
            		<div class="column" align="center">
            			Contact person:
            			<xsl:call-template name="TPerson">
            				<xsl:with-param name="person" select = "n:coverLetter/n:contactInformation/n:contactPerson" />
            			</xsl:call-template>
            		</div>
            	</div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
