<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://www.uns.ac.rs/Tim1"
                xmlns:sh="https://schema.org/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="https://schema.org/ ">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="ns:questionnaire">
        <ns:questionnaire
                vocab="https://schema.org/CreativeWork"
                about="localhost:8081/api/questionnaire/{@id}"
                typeof="sh:CreativeWork"
                href="localhost:8081/api/questionnaire/{@id}"
                rel="sh:about">
            <xsl:apply-templates select="node()|@*"/>
        </ns:questionnaire>
    </xsl:template>

    <xsl:template match="//ns:date">
        <ns:date property="sh:dateCreated" datatype="sh:Date" content="{.}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:date>
    </xsl:template>

    <xsl:template match="//ns:mark">
        <ns:mark property="sh:contentRating" datatype="sh:Text" content="{.}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:mark>
    </xsl:template>

    <xsl:template match="//ns:reviewer">
        <ns:reviewer vocab="https://schema.org/CreativeWork"
                   typeof="sh:Person"
                   rel="sh:author"
                   href="localhost:8081/api/person/{./ns:email}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:reviewer>
    </xsl:template>

</xsl:stylesheet>