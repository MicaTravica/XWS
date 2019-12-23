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
                </style>
            	<title>Scientific publication</title>
            </head>
            <body>
            	<h1 align="center">
            		<xsl:value-of select="n:scientificPublication/n:caption"/>
            	</h1>
            	<table style="width:100%">
            		<tr>
            			<xsl:for-each select="n:scientificPublication/n:authors/n:author">
            				<td>
            					<div align="center">
            						<xsl:call-template name="TAuthor">
            							<xsl:with-param name="author" select = "." />
            						</xsl:call-template>
            					</div>
            				</td>
            			</xsl:for-each>
            		</tr>
            	</table>
            	<p id="abstract">
            		<b><i>
            			Abstract<br/>
            			<xsl:for-each select="n:scientificPublication/n:abstract/n:paragraph">
            				<xsl:call-template name="TParagraph"/>
            			</xsl:for-each>
            			Keywords - 
            			<xsl:for-each select="n:scientificPublication/n:abstract/n:keywords/n:keyword">
            				<xsl:value-of select="."/>;
            			</xsl:for-each>
            		</i></b>
            	</p>
            	<xsl:for-each select="n:scientificPublication/n:chapter">
            		<xsl:call-template name="TChapter">
            			<xsl:with-param name="chapter" select = "." />
            		</xsl:call-template>
            	</xsl:for-each>
            	<div>
            		<h2>Table of content:</h2>
            		<a href="#abstract" style="font-size:20px;">Abstract</a><br/>
            		<xsl:for-each select="n:scientificPublication/n:chapter">
            			<xsl:call-template name="TChapterContent">
            				<xsl:with-param name="chapter" select = "." />
            			</xsl:call-template>
            		</xsl:for-each>
            	</div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
