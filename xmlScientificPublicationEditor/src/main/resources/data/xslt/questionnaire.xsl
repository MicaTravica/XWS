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
					.dot {
						height: 5px;
						width: 5px;
						background-color: #bbb;
						border-radius: 50%;
						display: inline-block;
						margin: 2px;
					}
                </style>
                <title>Questionnaire</title>
            </head>
            <body>
            	<xsl:variable name="sp" select="document(concat('http://', n:questionnaire/@href))"/>
            	<p align="right">
            		<xsl:value-of select="n:questionnaire/n:date"/>
                </p>
            	<h1 align="center">
            		Questionnaire for <xsl:value-of select="$sp/n:scientificPublication/n:caption"/>
            	</h1>
				<div class="row">
					<div class="column" align="center">
						Authors:<br/>
						<xsl:for-each select="$sp/n:scientificPublication/n:authors/n:author">
							<xsl:value-of select="./n:name"/>&#160;<xsl:value-of select="./n:surname"/><br/>
						</xsl:for-each>
					</div>
					<div class="column" align="center">
						Reviewer:
						<xsl:call-template name="TAuthor">
							<xsl:with-param name="author" select = "n:questionnaire/n:reviewer" />
						</xsl:call-template>
					</div>
				</div>
            	<xsl:for-each select="n:questionnaire/n:content">
            		<xsl:call-template name="TParagraph"/>
            	</xsl:for-each>
            	<xsl:for-each select="n:questionnaire/n:questions/n:question">
            		<xsl:call-template name="TQusetion">
            			<xsl:with-param name="qusetion" select="."></xsl:with-param>
            		</xsl:call-template>
            	</xsl:for-each>
            	<h3>
            		Mark: <xsl:value-of select="n:questionnaire/n:mark"/>
            	</h3>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
