<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns="http://www.uns.ac.rs/Tim1"
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
                    <xsl:if test="ns:scientificPublication/ns:dateMetaData/ns:accepted_at">
                        <fo:block text-align-last="justify">
                            <fo:leader leader-pattern="space" />
                            Accepted at:
                            <xsl:value-of select="ns:scientificPublication/ns:dateMetaData/ns:accepted_at"/>
                        </fo:block>
                    </xsl:if>
                    <xsl:if test="ns:scientificPublication/ns:dateMetaData/ns:recived_at">
                        <fo:block text-align-last="justify">
                            <fo:leader leader-pattern="space" />
                            Recived at:
                            <xsl:value-of select="ns:scientificPublication/ns:dateMetaData/ns:recived_at"/>
                        </fo:block>
                    </xsl:if>
                    <xsl:if test="ns:scientificPublication/ns:dateMetaData/ns:created_at">
                        <fo:block text-align-last="justify">
                            <fo:leader leader-pattern="space" />
                            Created at:
                            <xsl:value-of select="ns:scientificPublication/ns:dateMetaData/ns:created_at"/>
                        </fo:block>
                    </xsl:if>
                    <fo:block text-align-last="center" font-size="24">
                        <xsl:value-of select="ns:scientificPublication/ns:caption"/>
                    </fo:block>
                    <xsl:if test="ns:scientificPublication/ns:authors">
                    <fo:block>
                        <fo:table margin="10px auto 10px auto" border="1px">
                            <fo:table-body>
                                <xsl:for-each select="ns:scientificPublication/ns:authors/ns:author">
                                   <fo:table-row>
                                           <fo:table-cell padding="2px" text-align-last="center" margin="5px">
                                               <xsl:call-template name="TAuthor">
                                                   <xsl:with-param name="author" select = "." />
                                               </xsl:call-template>
                                           </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    </xsl:if>
                    <fo:block break-before='page' text-align-last="center" font-size="24" margin="5px">
                        <xsl:value-of select="ns:scientificPublication/ns:caption"/>
                    </fo:block>
                    <fo:block font-style="italic" font-weight="bold" id="abstract">
                        Abstract<br/>
                        <xsl:for-each select="ns:scientificPublication/ns:abstract/ns:paragraph">
                            <xsl:call-template name="TParagraph"/>
                        </xsl:for-each>
                        Keywords - 
                        <xsl:for-each select="ns:scientificPublication/ns:abstract/ns:keywords/ns:keyword">
                            <xsl:value-of select="."/>;
                        </xsl:for-each>
                    </fo:block>
                    <xsl:for-each select="ns:scientificPublication/ns:chapter">
                        <xsl:call-template name="TChapter">
                            <xsl:with-param name="chapter" select = "." />
                        </xsl:call-template>
                    </xsl:for-each>
                    <xsl:if test="ns:scientificPublication/ns:references">
                         <fo:block  break-before='page' width="100%" id="references">
                             References<br/>
                             <xsl:for-each select="ns:scientificPublication/ns:references/ns:reference">
                                 <xsl:call-template name="TReference">
                                     <xsl:with-param name="reference" select = "." />
                                 </xsl:call-template>
                             </xsl:for-each>
                         </fo:block>
                    </xsl:if>
                    <fo:block break-before='page' width="100%">
                        <fo:block font-size="18px">Table of content:</fo:block>
                        <fo:block text-align="justify">
                            <fo:basic-link internal-destination="abstract" font-size="15px">
                                <xsl:text>Abstract</xsl:text>
                            </fo:basic-link>
                            <xsl:text> </xsl:text>
                            <fo:leader leader-length.minimum="75%" leader-length.optimum="100%"
                                leader-length.maximum="100%" leader-pattern="dots"/>
                            <xsl:text> </xsl:text>
                            <fo:page-number-citation ref-id="abstract"/>
                        </fo:block>
                        <xsl:for-each select="ns:scientificPublication/ns:chapter">
                            <xsl:call-template name="TChapterContent">
                                <xsl:with-param name="chapter" select = "." />
                            </xsl:call-template>
                        </xsl:for-each>
                        <xsl:if test="ns:scientificPublication/ns:references">
                            <fo:block text-align="justify">
                                <fo:basic-link internal-destination="references" font-size="15px">
                                    <xsl:text>References</xsl:text>
                                </fo:basic-link>
                                <xsl:text> </xsl:text>
                                <fo:leader leader-length.minimum="75%" leader-length.optimum="100%"
                                    leader-length.maximum="100%" leader-pattern="dots"/>
                                <xsl:text> </xsl:text>
                                <fo:page-number-citation ref-id="references"/>
                            </fo:block>
                        </xsl:if>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
