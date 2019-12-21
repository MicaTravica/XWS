<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:template name="TPerson">
        <xsl:param name = "person"/>
        <div>
            <xsl:value-of select="$person/n:name"/><br/>
            <xsl:value-of select="$person/n:surname"/><br/>
            <xsl:value-of select="$person/n:email"/>
        </div>
    </xsl:template>

    <xsl:template name="TChapter">
        <xsl:param name = "paragraph" />
        <xsl:for-each select="$paragraph/*">
            <xsl:if test="name(.) = 'text'">   
                <p>
                    <xsl:apply-templates></xsl:apply-templates>
                </p>
            </xsl:if>
            <!--quote in notification-->
            <xsl:if test="name(.) ='quote'">
                <q>
                    <xsl:apply-templates></xsl:apply-templates>
                </q>
            </xsl:if>
            <xsl:if test="name(.) ='formula'">
                <div>
                   <p> Description:<br/>
                       <xsl:apply-templates select="./n:description"></xsl:apply-templates><br/>
                       Formula:<br/>
                       <xsl:apply-templates select="./n:content"></xsl:apply-templates>
                   </p>
                </div>
            </xsl:if>
            <!--make oredered and unordered list-->
            <xsl:if test="name(.) ='list'">
                <xsl:choose>
                    <xsl:when test="@type='ordered'">
                        <ol>
                            <xsl:for-each select="./*">
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </xsl:for-each>
                        </ol>
                    </xsl:when>
                    <xsl:otherwise>
                        <ul>
                            <xsl:for-each select="./*">
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </xsl:for-each>
                        </ul>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="n:listItem/n:text" name="TListItem">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./*">
                    <li>  
                        <xsl:apply-templates select="."></xsl:apply-templates>
                    </li>
                </xsl:for-each>
    </xsl:template>

    <!-- TCursive and it's subnodes templates -->
    <xsl:template match="n:cursive | n:description" name="TCursive">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
                </xsl:for-each>
    </xsl:template>

    <xsl:template match="n:bold">
        <b>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </b>
    </xsl:template>

    <xsl:template match="n:italic">
        <i>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </i>
    </xsl:template>

    <xsl:template match="n:underline">
        <u>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </u>
    </xsl:template>

    <!-- override rule: copy any text node beneath bold -->
    <xsl:template match="n:bold/text()">
        <xsl:copy-of select="." />
    </xsl:template>
    
    <!-- override rule: copy any text node beneath italic -->
    <xsl:template match="n:italic/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <!-- override rule: copy any text node beneath underline -->
    <xsl:template match="n:underline/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:description/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:cursive/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:address | n:organisationAddress" name="TAddress">
        <xsl:param name = "address"/>
        <div>
            <xsl:value-of select="$address/n:city"/>, 
            <xsl:value-of select="$address/n:country"/>
            <br/>
            <xsl:value-of select="$address/n:street"/> 
            <xsl:value-of select="$address/n:streetNumber"/> 
            <xsl:value-of select="$address/n:floorNumber"/>
            <br/>
        </div>
    </xsl:template>
</xsl:stylesheet>