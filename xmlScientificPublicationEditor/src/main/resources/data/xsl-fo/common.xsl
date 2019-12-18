<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">


<xsl:template name="TPerson">
    <xsl:param name = "person"/>
    <fo:table font-family="serif" margin="50px auto 50px auto" border="1px">
        <fo:table-column column-width="85%"/>
        <fo:table-body>
            <fo:table-row border="1px solid darkgrey">
                <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                    <fo:block>
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="$person/n:name"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="$person/n:surname"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="$person/n:email"/>
                        </fo:inline>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
        </fo:table-body>
    </fo:table>
</xsl:template>

<xsl:template name="TChapter">
        <xsl:param name = "paragraph" />
        <xsl:for-each select="$paragraph/*">
                    <xsl:if test="name(.) = 'text'">   
                        <fo:block>
                            <xsl:apply-templates></xsl:apply-templates>
                        </fo:block>
                    </xsl:if>
                    <!--quote in notification-->
                    <xsl:if test="name(.) ='quote'">
                        <fo:block>
                            <xsl:apply-templates></xsl:apply-templates>
                        </fo:block>
                    </xsl:if>
                    <xsl:if test="name(.) ='formula'">
                        <fo:block>
                            <fo:block> Description:
                                <xsl:apply-templates select="./n:description"></xsl:apply-templates>
                            </fo:block>
                            <fo:block>
                                Formula:
                                <xsl:apply-templates select="./n:content"></xsl:apply-templates>
                            
                            </fo:block>
                        </fo:block>
                    </xsl:if>
                    <!--make oredered and unordered list-->
                    <xsl:if test="name(.) ='list'">
                        <xsl:if test="@type='ordered'">
                            <fo:block>
                                <xsl:for-each select="./*">
                                    <xsl:apply-templates select="."></xsl:apply-templates>
                                </xsl:for-each>
                            </fo:block>
                        </xsl:if>
                        <xsl:if test="@type='unordered'">
                            <fo:block>
                                <xsl:for-each select="./*">
                                    <xsl:apply-templates select="."></xsl:apply-templates>
                                </xsl:for-each>
                            </fo:block>
                        </xsl:if>
                    </xsl:if>
                </xsl:for-each>
    </xsl:template>

    <xsl:template match="n:listItem/n:text" name="TListItem">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./*">
                    <fo:block>
                        <xsl:apply-templates select="."></xsl:apply-templates>
                    </fo:block>
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
        <fo:block>
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:block>
    </xsl:template>

    <xsl:template match="n:italic">
        <fo:block>
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:block>
    </xsl:template>

    <xsl:template match="n:underline">
        <fo:block>
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:block>
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

</xsl:stylesheet>