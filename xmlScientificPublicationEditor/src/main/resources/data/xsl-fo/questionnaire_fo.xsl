<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:import href="common_fo.xsl"/>
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="questionnaire-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="questionnaire-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align-last="justify">
                        <fo:leader leader-pattern="space" />
                        <xsl:value-of select="n:questionnaire/n:date"/>
                    </fo:block>
                    <xsl:variable name="sp" select="document(concat('http://', n:questionnaire/@href))"/>
                    <fo:block text-align-last="center">
                        <fo:inline font-size="24">
                            Questionnaire for <xsl:value-of select="$sp/n:scientificPublication/n:caption"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:table margin="10px auto 10px auto" border="1px">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell padding="2px" text-align-last="center">
                                        <fo:block>
                                            Authors:
                                            <xsl:for-each select="$sp/n:scientificPublication/n:authors/n:author">
                                                <fo:block>
                                                    <xsl:value-of select="./n:name"/>&#160;<xsl:value-of select="./n:surname"/><br/>
                                                </fo:block>
                                            </xsl:for-each>
                                        </fo:block>
                                        <fo:block>
                                            <xsl:value-of select="n:questionnaire/@authors"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2px" text-align-last="center">
                                        <fo:block>
                                            Reviewer:
                                            <xsl:call-template name="TAuthor">
                                                <xsl:with-param name="author" select = "n:questionnaire/n:reviewer" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    <fo:block margin-bottom="5px" margin-top="5px">
                        <xsl:for-each select="n:questionnaire/n:content">
                            <fo:block>
                                <xsl:call-template name="TParagraph"/>
                            </fo:block>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block margin-bottom="5px" margin-top="5px">
                        <xsl:for-each select="n:questionnaire/n:questions/n:question">
                            <fo:block margin-bottom="5px" margin-top="5px">
                                <xsl:call-template name="TQusetion">
                                    <xsl:with-param name="qusetion" select="."></xsl:with-param>
                                </xsl:call-template>
                            </fo:block>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-size="14px" margin-top="5px">
                        Mark: <xsl:value-of select="n:questionnaire/n:mark"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
