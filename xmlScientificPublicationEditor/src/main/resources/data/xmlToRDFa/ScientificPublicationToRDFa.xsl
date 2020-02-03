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

    <xsl:template match="ns:scientificPublication">
        <ns:scientificPublication
                vocab="https://schema.org/ScholarlyArticle"
                about="localhost:8081/api/scientificPublication/{@id}"
                typeof="sh:ScholarlyArticle"
                property="sh:version" datatype="sh:Text" content="{@version}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:scientificPublication>
    </xsl:template>

    <xsl:template match="//ns:caption/ns:value">
        <ns:caption property="sh:headline" datatype="sh:Text" content="{.}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:caption>
    </xsl:template>
    
    <xsl:template match="//ns:authors">
        <xsl:for-each select="./ns:author">
            <ns:author
                property="sh:author" datatype="sh:Text" 
                content="{concat(./ns:name, ' ', ./ns:surname)}">
                <xsl:apply-templates select="node()|@*"/>
            </ns:author>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="//ns:keywords">
        <xsl:for-each select="./ns:keyword">
            <ns:keyword
                property="sh:keywords" datatype="sh:Text">
                <xsl:apply-templates select="node()|@*"/>
            </ns:keyword>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="//ns:created_at">
        <ns:received_at property="sh:dateCreated" datatype="sh:Date" content="{.}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:received_at>
    </xsl:template>

    <xsl:template match="//ns:revised_at">
        <revised property="sh:dateModified" datatype="sh:Date" content="{text()}">
            <xsl:apply-templates select="node()|@*"/>
        </revised>
    </xsl:template>

    <xsl:template match="//ns:received_at">
        <accepted property="sh:datePublished" datatype="sh:Date" content="{text()}">
            <xsl:apply-templates select="node()|@*"/>
        </accepted>
    </xsl:template>
    
    <xsl:template match="//ns:references">
            <xsl:for-each select="//ns:references/ns:reference">
                <xsl:if test="./ns:link">
                    <ns:reference property="sh:citation" datatype="sh:Text" content="{./ns:link}"/>
                </xsl:if>
            </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>