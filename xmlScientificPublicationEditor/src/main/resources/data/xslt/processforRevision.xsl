<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="n:processPSP">
        <xsl:variable name="lastVersion" select="@lastVersion"/>
        <processPSP>
            <processId>
                <xsl:value-of select="@id"/>
            </processId>
            <processState>
                <xsl:value-of select="@state"/>
            </processState>
            <lastVersion>
                <xsl:value-of select="$lastVersion" />
            </lastVersion>
        </processPSP>
    </xsl:template>

</xsl:stylesheet>
