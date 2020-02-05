<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:ns="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:template name="TPerson">
        <xsl:param name = "person"/>
        <fo:table>
            <fo:table-column/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell>
                        <fo:block>
                            <xsl:value-of select="$person/ns:name"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$person/ns:surname"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$person/ns:email"/>
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
                    <fo:table-cell id="{$author/@id}">
                        <fo:block>
                            <xsl:value-of select="$author/ns:name"/>&#160;
                            <xsl:value-of select="$author/ns:surname"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/ns:institution/ns:name"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/ns:institution/ns:address/ns:city"/>, <xsl:value-of select="$author/ns:institution/ns:address/ns:country"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/ns:phone"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$author/ns:email"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="ns:chapter" name="TChapter">
        <xsl:param name = "chapter"/>
        <fo:block font-size="20" margin-left="10px" margin-top="10px" margin-bottom="5px">
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
    
    <xsl:template match="ns:subchapter" name="TSubchapter">
        <fo:block margin-left="10px" margin-top="5px" margin-bottom="2px">
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
    
    <xsl:template match="ns:paragraph |ns:answer" name="TParagraph">
        <fo:block id="{./@id}">
        <xsl:for-each select="./*">
            <xsl:if test="name(.) = 'ns:text'">   
                <fo:block margin-top="2" margin-bottom="2" id="{./@id}">
                    <xsl:apply-templates></xsl:apply-templates>
                </fo:block>
            </xsl:if>
            <!--quote in notification-->
            <xsl:if test="name(.) ='ns:quote'">
                <fo:block margin-top="2" margin-bottom="2" font-style="italic" id="{./@id}">
                    <fo:basic-link internal-destination="{./@ref}" font-size="12px">
                        <xsl:apply-templates></xsl:apply-templates>
                    </fo:basic-link>
                </fo:block>
            </xsl:if>
            <xsl:if test="name(.) ='ns:formula'">
                <fo:block margin="10px" id="{./@id}">
                    <fo:block> Descriptions:
                        <xsl:apply-templates select="./ns:description"></xsl:apply-templates>
                    </fo:block>
                    <fo:block>
                        Formula:
                        <xsl:apply-templates select="./ns:content"></xsl:apply-templates>
                    </fo:block>
                </fo:block>
            </xsl:if>
            <!--make oredered and unordered list-->
            <xsl:if test="name(.) ='ns:list'">
                <xsl:choose>
                    <xsl:when test="@type='ordered'">
                        <fo:list-block id="{./@id}">
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
                        <fo:list-block id="{./@id}">
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
            <xsl:if test="name(.) ='ns:image'">
                <fo:block margin-top="10px" margin-bottom="10px" id="{./@id}">
                    <fo:external-graphic>
                        <xsl:attribute name="src">
                            <xsl:value-of select="./ns:source"/>
                        </xsl:attribute>
                        <xsl:attribute name="content-width">
                            <xsl:value-of select="./@width"/>
                        </xsl:attribute>
                        <xsl:attribute name="content-height">
                            <xsl:value-of select="./@height"/>
                        </xsl:attribute>
                    </fo:external-graphic>
                    <fo:block font-size="8">
                        <xsl:value-of select="./ns:description"/>
                    </fo:block>
                </fo:block>
            </xsl:if>
            <xsl:if test="name(.) ='ns:table'">
                <fo:table border="1px" margin-top="10px" margin-bottom="10px" id="{./@id}">
                    <fo:table-body>
                        <xsl:for-each select="./ns:table_row">
                            <fo:table-row border="1px solid black">
                                <xsl:for-each select="./ns:table_cell">
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
        </fo:block>
    </xsl:template>
    
    <xsl:template match="ns:listitem" name="TListItem">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./*">
                    <fo:block>
                        <xsl:apply-templates select="."></xsl:apply-templates>
                    </fo:block>
                </xsl:for-each>
    </xsl:template>

    <!-- TCursive and it's subnodes templates -->
    <xsl:template match="ns:cursive | ns:description" name="TCursive">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
                </xsl:for-each>
    </xsl:template>

    <xsl:template match="ns:bold">
        <fo:inline  font-weight="bold">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>

    <xsl:template match="ns:italic">
        <fo:inline font-style="italic">
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>

    <xsl:template match="ns:underline">
        <fo:inline border-after-width="1pt" border-after-style="solid">
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </fo:inline>
    </xsl:template>

    <!-- override rule: copy any text node beneath bold -->
    <xsl:template match="ns:bold/text()">
        <xsl:copy-of select="." />
    </xsl:template>
    
    <!-- override rule: copy any text node beneath italic -->
    <xsl:template match="ns:italic/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <!-- override rule: copy any text node beneath underline -->
    <xsl:template match="ns:underline/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="ns:description/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="ns:cursive/text()">
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
                            <xsl:value-of select="$address/ns:city"/>, 
                            <xsl:value-of select="$address/ns:country"/>
                        </fo:block>
                        <fo:block>
                            <xsl:value-of select="$address/ns:street"/> 
                            <xsl:value-of select="$address/ns:streetNumber"/> 
                            <xsl:value-of select="$address/ns:floorNumber"/>
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
        <xsl:for-each select="$chapter/ns:subchapter">
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
            <xsl:for-each select="./ns:subchapter">
                <xsl:call-template name="TSubchapterContent">
                    <xsl:with-param name="subchapter" select="."></xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </fo:block>
    </xsl:template>
    
    <xsl:template name="TQusetion">
        <xsl:param name="qusetion"/>
        <fo:block>
            - <xsl:value-of select="$qusetion/ns:questionText"/>
        </fo:block>
        <xsl:apply-templates select="$qusetion/ns:answer"></xsl:apply-templates>
    </xsl:template>
    
    
    <xsl:template name="TReference">
        <xsl:param name="reference"/>
        <xsl:if test="$reference/ns:book">
            <fo:block text-align="justify" id="{$reference/@id}">
                <xsl:value-of select="$reference/ns:book"/>
            </fo:block>
        </xsl:if>
        <xsl:if test="$reference/ns:link">
            <fo:block text-align="justify" id="{$reference/@id}">
                <fo:basic-link external-destination="{$reference/ns:link}" font-size="12px">
                    ref
                </fo:basic-link>
            </fo:block>
        </xsl:if>
    </xsl:template>
    
    <xsl:template name="TComment">
        <xsl:param name="comment"/>
        <fo:block text-align="justify" id="{$comment/@id}">
            <fo:basic-link internal-destination="{$comment/@ref}" font-size="12px">
                <xsl:value-of select="$comment"/>
            </fo:basic-link>
        </fo:block>
    </xsl:template>
</xsl:stylesheet>