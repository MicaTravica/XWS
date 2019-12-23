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

    <xsl:template name="TAuthor">
        <xsl:param name="author"/>
        <fo:table>
            <fo:table-column/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell>
                        <fo:block>
                            <xsl:value-of select="$author/n:name"/>
                            <xsl:value-of select="$author/n:surname"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/n:institution/n:name"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/n:institution/n:address/n:city"/>, <xsl:value-of select="$author/n:institution/n:address/n:country"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/n:email"/>&#160;<xsl:value-of select="$author/n:phone"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="n:chapter" name="TChapter">
        <xsl:param name = "chapter" />
        <fo:block font-size="20" margin-left="10px" margin-top="5px" margin-bottom="5px">
            <xsl:attribute name="id">
                <xsl:value-of select="$chapter/@id"/>
            </xsl:attribute>
            <fo:inline>
                <xsl:value-of select="$chapter/@title"/>
            </fo:inline>
        </fo:block>
        <xsl:for-each select=".">
            <xsl:apply-templates></xsl:apply-templates>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="n:subchapter" name="TSubchapter">
        <fo:block margin-left="10px" margin-top="2px" margin-bottom="2px">
            <fo:block margin-left="10px" font-size="15" margin-top="3px" margin-bottom="3px">
                <xsl:attribute name="id">
                    <xsl:value-of select="./@id"/>
                </xsl:attribute>
                <fo:inline>
                    <xsl:value-of select="./@title"/>
                </fo:inline>
            </fo:block>
            <xsl:for-each select=".">
                <xsl:apply-templates></xsl:apply-templates>
            </xsl:for-each>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="n:paragraph" name="TParagraph">
        <xsl:for-each select="./*">
            <xsl:if test="name(.) = 'text'">   
                <fo:block margin-top="2" margin-bottom="2">
                    <xsl:apply-templates></xsl:apply-templates>
                </fo:block>
            </xsl:if>
            <!--quote in notification-->
            <xsl:if test="name(.) ='quote'">
                <fo:block margin-top="2" margin-bottom="2" font-style="italic">
                    <xsl:apply-templates></xsl:apply-templates>
                </fo:block>
            </xsl:if>
            <xsl:if test="name(.) ='formula'">
                <fo:block margin="10px">
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
            <xsl:if test="name(.) ='image'">
                <fo:block margin-top="10px" margin-bottom="10px">
                    <fo:external-graphic>
                        <xsl:attribute name="src">
                            <xsl:value-of select="./n:source"/>
                        </xsl:attribute>
                        <xsl:attribute name="content-width">
                            <xsl:value-of select="./@width"/>
                        </xsl:attribute>
                        <xsl:attribute name="content-height">
                            <xsl:value-of select="./@height"/>
                        </xsl:attribute>
                    </fo:external-graphic>
                    <fo:block font-size="8">
                        <xsl:value-of select="./n:description"/>
                    </fo:block>
                </fo:block>
            </xsl:if>
            <xsl:if test="name(.) ='table'">
                <fo:table border="1px" margin-top="10px" margin-bottom="10px">
                    <fo:table-body>
                        <xsl:for-each select="./n:table_row">
                            <fo:table-row border="1px solid black">
                                <xsl:for-each select="./n:table_cell">
                                    <fo:table-cell border="1px solid black" padding="10px">
                                        <fo:block>
                                            <xsl:apply-templates></xsl:apply-templates>
                                        </fo:block>
                                    </fo:table-cell>
                                </xsl:for-each>
                            </fo:table-row>
                        </xsl:for-each>
                    </fo:table-body>
                </fo:table>
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
    
    <xsl:template name="TChapterContent">
        <xsl:param name="chapter"/>
        <xsl:variable name="link" select="$chapter/@id"/>
        <fo:block text-align="justify">
            <fo:basic-link internal-destination="{$link}" font-size="15px">
                <xsl:value-of select="$chapter/@title"/>
            </fo:basic-link>
            <xsl:text> </xsl:text>
            <fo:leader leader-length.minimum="75%" leader-length.optimum="100%"
                leader-length.maximum="100%" leader-pattern="dots"/>
            <xsl:text> </xsl:text>
            <fo:page-number-citation ref-id="{$link}"/>
        </fo:block>
        <xsl:for-each select="$chapter/n:subchapter">
            <xsl:call-template name="TSubchapterContent">
                <xsl:with-param name="subchapter" select="."></xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="TSubchapterContent">
        <xsl:param name="subchapter"/>
        <fo:block margin-left="10px">
            <xsl:variable name="link" select="./@id"/>
            <fo:block text-align="justify">
                <fo:basic-link internal-destination="{$link}" font-size="12px">
                    <xsl:value-of select="./@title"/>
                </fo:basic-link>
                <xsl:text> </xsl:text>
                <fo:leader leader-length.minimum="75%" leader-length.optimum="100%"
                    leader-length.maximum="100%" leader-pattern="dots"/>
                <xsl:text> </xsl:text>
                <fo:page-number-citation ref-id="{$link}"/>
            </fo:block>
            <xsl:for-each select="./n:subchapter">
                <xsl:call-template name="TSubchapterContent">
                    <xsl:with-param name="subchapter" select="."></xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </fo:block>
    </xsl:template>
</xsl:stylesheet>