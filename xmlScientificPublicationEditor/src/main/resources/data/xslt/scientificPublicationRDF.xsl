<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns = "http://www.uns.ac.rs/Tim1" version="2.0"
    xmlns:pred = "http://www.uns.ac.rs/Tim1/predicate/"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <xsl:output method="xml" indent="yes"/>
    
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:variable name="spId">
        <xsl:value-of select="ns:scientificPublication/@id"/>
    </xsl:variable>
    
    <xsl:variable name="caption">
        <xsl:value-of select="ns:scientificPublication/ns:caption/ns:value"/>
    </xsl:variable>
    
    
    <xsl:template match="ns:scientificPublication">
        <ns:scientificPublication
            about="localhost:8081/api/scientificPublication/{$spId}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:scientificPublication>
    </xsl:template>
    
    <xsl:template match="//ns:caption">
        <ns:caption 
            property="pred:caption"
            content="{$caption}">
            <xsl:apply-templates select="node()|@*"/>
        </ns:caption>
    </xsl:template>
    
    <xsl:template match="//ns:authors">
        <xsl:for-each select="./ns:author">
            <ns:author
                href="localhost:8081/api/person/{./ns:email}" 
                rel="pred:writtenBy">
                <xsl:apply-templates select="node()|@*"/>
            </ns:author>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="//ns:keywords">
        <xsl:for-each select="./ns:keyword">
            <ns:keyword
                property="pred:keyword">
                <xsl:apply-templates select="node()|@*"/>
            </ns:keyword>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>