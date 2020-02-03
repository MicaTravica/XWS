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

    <xsl:variable name="coverLetterId">
        <xsl:value-of select="coverLetter/@id"/>
    </xsl:variable>

    <xsl:template match="ns:coverLetter">
        <ns:coverLetter
                vocab="https://schema.org/CreativeWork"
                about="localhost:8081/api/coverLetter/{@id}"
                typeof="sh:CreativeWork">
            <xsl:apply-templates select="node()|@*"/>
        </ns:coverLetter>
    </xsl:template>

    <xsl:template match="//ns:date">
        <ns:date property="sh:dateCreated" datatype="sh:Date" content="{.}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:date>
    </xsl:template>

    <xsl:template match="//ns:author">
        <ns:author vocab="https://schema.org/CreativeWork"
                typeof="sh:Person"
                rel="sh:author"
                href="localhost:8081/api/person/{@id}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:author>
    </xsl:template>

    <xsl:template match="//ns:contactPerson">
        <ns:contactPerson vocab="https://schema.org/Person"
                          typeof="sh:Person"
                          rel="sh:director"
                          href="localhost:8081/api/person/{@id}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:contactPerson>
    </xsl:template>

</xsl:stylesheet>