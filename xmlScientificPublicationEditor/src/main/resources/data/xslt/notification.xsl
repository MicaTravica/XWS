<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">
    <xsl:import href="common.xsl"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Notification</title>
            </head>
            <body>
                <h3>
                    Notification about scientific publication 
                    "<xsl:value-of select="n:notification/n:sp"/>"<br/>
                </h3>
                <xsl:for-each select="n:notification/n:content">
                    <xsl:call-template name="TParagraph"/>
                </xsl:for-each>
                <br/>
                Date:
                <xsl:value-of select="n:notification/n:date"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
