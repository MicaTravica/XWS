<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:import href="common_fo.xsl"/>
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="scientificPublication-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="scientificPublication-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align-last="center" font-size="24">
                        <xsl:value-of select="n:scientificPublication/n:caption"/>
                    </fo:block>
                    <fo:block>
                        <fo:table margin="10px auto 10px auto" border="1px">
                            <fo:table-body>
                                <xsl:for-each select="n:scientificPublication/n:authors/n:author">
                                   <fo:table-row>
                                           <fo:table-cell padding="2px" text-align-last="center">
                                               <xsl:call-template name="TAuthor">
                                                   <xsl:with-param name="author" select = "." />
                                               </xsl:call-template>
                                           </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    <fo:block font-style="italic" font-weight="bold" id="abstract">
                        Abstract<br/>
                        <xsl:for-each select="n:scientificPublication/n:abstract/n:paragraph">
                            <xsl:call-template name="TParagraph"/>
                        </xsl:for-each>
                        Keywords - 
                        <xsl:for-each select="n:scientificPublication/n:abstract/n:keywords/n:keyword">
                            <xsl:value-of select="."/>;
                        </xsl:for-each>
                    </fo:block>
                    <xsl:for-each select="n:scientificPublication/n:chapter">
                        <xsl:call-template name="TChapter">
                            <xsl:with-param name="chapter" select = "." />
                        </xsl:call-template>
                    </xsl:for-each>
                    <fo:block break-before='page' width="100%">
                        <fo:block font-size="18px">Table of content:</fo:block>
                        <fo:block>
                            <fo:basic-link internal-destination="abstract" font-size="15px">
                                <xsl:text>Abstract</xsl:text>
                                <!--<fo:leader leader-pattern="dots" />
                                <fo:page-number-citation ref-id="abstract" />-->
                            </fo:basic-link>
                        </fo:block>
                        <xsl:for-each select="n:scientificPublication/n:chapter">
                            <xsl:call-template name="TChapterContent">
                                <xsl:with-param name="chapter" select = "." />
                            </xsl:call-template>
                        </xsl:for-each>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
