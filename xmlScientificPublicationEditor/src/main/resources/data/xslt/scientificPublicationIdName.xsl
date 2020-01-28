<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="n:scientificPublication">
        <sp>
            <scientificPublicationId>
                <xsl:value-of select="@id"></xsl:value-of>
            </scientificPublicationId>
            <scientificPublicationName>
                <xsl:value-of select="./n:caption/n:value"></xsl:value-of>
            </scientificPublicationName>
            <authors>
                <xsl:for-each select="./n:authors/*">
                    <author>
                        <name>
                            <xsl:value-of select="./n:name"></xsl:value-of>
                        </name>
                        <surname>
                            <xsl:value-of select="./n:surname"></xsl:value-of>
                        </surname>
                    </author>
                </xsl:for-each>
            </authors>
        </sp>
    </xsl:template>

</xsl:stylesheet>
