<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns="http://www.uns.ac.rs/Tim1" version="2.0">
    <xsl:import href="questionnaire.xsl"/>
    <xsl:template match="/">
        
            	<xsl:for-each select="ns:qs/ns:questionnaire">
            		<xsl:apply-templates select="."></xsl:apply-templates>
            	</xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
