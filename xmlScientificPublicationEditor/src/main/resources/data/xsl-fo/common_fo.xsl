<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">


    <xsl:template name="TPerson">
        <xsl:param name = "person"/>
        <fo:table>
            <fo:table-column/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell>
                        <fo:block>
                            <xsl:value-of select="$person/n:name"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$person/n:surname"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$person/n:email"/>
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
                        <xsl:choose>
                            <xsl:when test="@type='ordered'">
                                <fo:list-block>
                                    <xsl:for-each select="./*">
                                        <fo:list-item space-after="0.5em">
                                            <fo:list-item-label start-indent="1em">
                                                <fo:block>
                                                    <xsl:number value="position()" format="1"/>.
                                                </fo:block>
                                            </fo:list-item-label>
                                            <fo:list-item-body start-indent="2em">
                                                <fo:block>
                                                    <xsl:apply-templates select="."></xsl:apply-templates>
                                                </fo:block>
                                            </fo:list-item-body>
                                        </fo:list-item>
                                    </xsl:for-each>
                                </fo:list-block>
                            </xsl:when>
                            <xsl:otherwise>
                                <fo:list-block>
                                    <xsl:for-each select="./*">
                                        <fo:list-item space-after="0.5em">
                                            <fo:list-item-label start-indent="1em">
                                                <fo:block>-</fo:block>
                                            </fo:list-item-label>
                                            <fo:list-item-body start-indent="2em">
                                                <fo:block>
                                                    <xsl:apply-templates select="."></xsl:apply-templates>
                                                </fo:block>
                                           </fo:list-item-body>
                                        </fo:list-item>
                                    </xsl:for-each>
                                </fo:list-block>
                            </xsl:otherwise>
                        </xsl:choose>
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
        <fo:inline  font-weight="bold">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>

    <xsl:template match="n:italic">
        <fo:inline font-style="italic">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>

    <xsl:template match="n:underline">
        <fo:inline border-after-width="1pt" border-after-style="solid">
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
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
    
    <xsl:template name="TAddress">
        <xsl:param name="address"/>
        <fo:table>
            <fo:table-column/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell>
                        <fo:block>
                            <xsl:value-of select="$address/n:city"/>, 
                            <xsl:value-of select="$address/n:country"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$address/n:street"/> 
                            <xsl:value-of select="$address/n:streetNumber"/> 
                            <xsl:value-of select="$address/n:floorNumber"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>
</xsl:stylesheet>