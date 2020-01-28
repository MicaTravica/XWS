<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:output method="text"/>

    <xsl:template match="n:processPSP">
        <xsl:variable name="lastVersion" select="@lastVersion"/>
                <xsl:for-each select="./n:versions/*">
                <xsl:variable name="version" select="@version"/>
                    <xsl:if test="$version = $lastVersion">
                        <xsl:value-of select="./n:scientificPublication"></xsl:value-of>
                    </xsl:if>
                </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
