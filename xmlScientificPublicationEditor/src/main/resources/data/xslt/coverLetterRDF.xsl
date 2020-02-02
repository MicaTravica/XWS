<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns = "http://www.uns.ac.rs/Tim1" version="2.0"
    xmlns:pred = "http://www.uns.ac.rs/Tim1/predicate/">
    <xsl:output method="xml" indent="yes"/>
        <xsl:template match="node()|@*">
            <xsl:copy>
                <xsl:apply-templates select="node()|@*"/>
            </xsl:copy>
        </xsl:template>
        
        <xsl:variable name="clId">
            <xsl:value-of select="ns:coverLetter/@id"/>
        </xsl:variable>
    
       <xsl:variable name="processId">
           <xsl:value-of select="ns:coverLetter/@processId"/>
       </xsl:variable>
    
        <xsl:variable name="email">
            <xsl:value-of select="ns:coverLetter/ns:author/ns:email"/>
        </xsl:variable>
    
        <xsl:template match="ns:coverLetter">
            <ns:coverLetter
                about="localhost:8081/api/coverLetter/{$clId}" 
                href="localhost:8081/api/scientificPublication/process/{$processId}" 
                rel="pred:writtenFor">
                <xsl:apply-templates select="node()|@*"/>
            </ns:coverLetter>
        </xsl:template>
 
        <xsl:template match="//ns:date">
            <ns:date 
                property="pred:writtenAt">
                <xsl:apply-templates select="node()|@*"/>
            </ns:date>
        </xsl:template>
    
        <xsl:template match="//ns:author">
          <ns:author
              href="localhost:8081/api/person/{$email}" 
              rel="pred:writtenBy">
              <xsl:apply-templates select="node()|@*"/>
          </ns:author>
        </xsl:template>

</xsl:stylesheet>