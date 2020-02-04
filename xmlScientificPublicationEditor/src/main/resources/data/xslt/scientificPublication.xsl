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
                </style>
            	<title>Scientific publication</title>
            </head>
            <body>
                <p align="right">
                    <xsl:if test="ns:scientificPublication/ns:dateMetaData/ns:accepted_at">
                        Accepted at:
                        <xsl:value-of select="ns:scientificPublication/ns:dateMetaData/ns:accepted_at"/>
                    </xsl:if>
                    <xsl:if test="ns:scientificPublication/ns:dateMetaData/ns:recived_at">
                        Recived at:
                        <xsl:value-of select="ns:scientificPublication/ns:dateMetaData/ns:recived_at"/>
                    </xsl:if>
                    <xsl:if test="ns:scientificPublication/ns:dateMetaData/ns:created_at">
                        Created at:
                        <xsl:value-of select="ns:scientificPublication/ns:dateMetaData/ns:created_at"/>
                    </xsl:if>
                </p>
            	<h1 align="center">
            		<xsl:value-of select="ns:scientificPublication/ns:caption"/>
            	</h1>
            	<table style="width:100%">
            		<tr>
            			<xsl:for-each select="ns:scientificPublication/ns:authors/ns:author">
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
            			<xsl:for-each select="ns:scientificPublication/ns:abstract/ns:paragraph">
            				<xsl:call-template name="TParagraph"/>
            			</xsl:for-each>
            			Keywords - 
            			<xsl:for-each select="ns:scientificPublication/ns:abstract/ns:keywords/ns:keyword">
            				<xsl:value-of select="."/>;
            			</xsl:for-each>
            		</i></b>
            	</p>
            	<xsl:for-each select="ns:scientificPublication/ns:chapter">
            		<xsl:call-template name="TChapter">
            			<xsl:with-param name="chapter" select = "." />
            		</xsl:call-template>
            	</xsl:for-each>
                <xsl:if test="ns:scientificPublication/ns:references">
                   <div id="references">
                       <h2>References:</h2>
                       <xsl:for-each select="ns:scientificPublication/ns:references/ns:reference">
                           <xsl:call-template name="TReference">
                               <xsl:with-param name="reference" select = "." />
                           </xsl:call-template>
                       </xsl:for-each>
                   </div>
                </xsl:if>
            	<div>
            		<h2>Table of content:</h2>
            		<a href="#abstract" style="font-size:20px;">Abstract</a><br/>
            		<xsl:for-each select="ns:scientificPublication/ns:chapter">
            			<xsl:call-template name="TChapterContent">
            				<xsl:with-param name="chapter" select = "." />
            			</xsl:call-template>
            		</xsl:for-each>
            	    <xsl:if test="ns:scientificPublication/ns:references">
            	       <a href="#references" style="font-size:20px;">References</a><br/>
            	    </xsl:if>
            	</div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
