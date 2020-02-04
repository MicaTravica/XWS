<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns="http://www.uns.ac.rs/Tim1" version="2.0">
    <xsl:import href="common.xsl"/>
    <xsl:template match="ns:questionnaire">
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
					.dot {
						height: 5px;
						width: 5px;
						background-color: #bbb;
						border-radius: 50%;
						display: inline-block;
						margins: 2px;
					}
                </style>
                <title>Questionnaire</title>
            </head>
            <body><p align="right">
            		<xsl:value-of select="./ns:date"/>
                </p>
            	<h1 align="center">
            		Questionnaire
            	</h1>
            	<xsl:if test="./ns:reviewer">
				<div class="row">
					<div class="column" align="center">
						Reviewer:
						<xsl:call-template name="TAuthor">
							<xsl:with-param name="author" select = "./ns:reviewer" />
						</xsl:call-template>
					</div>
				</div>
				</xsl:if>
            	<xsl:for-each select="./ns:content">
            		<xsl:call-template name="TParagraph"/>
            	</xsl:for-each>
            	<xsl:for-each select="./ns:questions/ns:question">
            		<xsl:call-template name="TQusetion">
            			<xsl:with-param name="qusetion" select="."></xsl:with-param>
            		</xsl:call-template>
            	</xsl:for-each>
            	<h3>
            		Mark: <xsl:value-of select="./ns:mark"/>
            	</h3>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
