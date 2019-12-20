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
						float: center;
						width: auto;
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
            	<title>Scientific publication</title>
            </head>
            <body>
            	<h1 align="center">
            		<xsl:value-of select="n:scientificPublication/n:caption"/>
            	</h1>
				<div class="row">
					<xsl:for-each select="n:scientificPublication/n:authors/n:author">
						<div class="column" align="center">
							<xsl:call-template name="TPerson">
								<xsl:with-param name="person" select = "." />
							</xsl:call-template>
						</div>
					</xsl:for-each>
				</div>
				
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
