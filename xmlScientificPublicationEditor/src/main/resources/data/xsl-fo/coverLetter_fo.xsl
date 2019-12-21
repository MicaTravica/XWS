<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:import href="common_fo.xsl"/>
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="coverLetter-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="coverLetter-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align-last="justify">
                        <fo:leader leader-pattern="space" />
                        <xsl:value-of select="n:coverLetter/@date"/>
                    </fo:block>
                    <fo:block text-align-last="center">
                        <fo:inline font-size="24">
                            Cover letter for <xsl:value-of select="n:coverLetter/@scientificPublication"/>
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
                                            Authors of scientific publication:
                                        </fo:block>
                                        <fo:block>
                                            <xsl:value-of select="n:coverLetter/@authors"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2px" text-align-last="center">
                                        <fo:block>
                                            Author:
                                            <xsl:call-template name="TPerson">
                                                <xsl:with-param name="person" select = "n:coverLetter/n:author" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    <xsl:for-each select="n:coverLetter/n:content">
                        <fo:block>
                            <xsl:call-template name="TChapter">
                                <xsl:with-param name="paragraph" select = "." />
                            </xsl:call-template>
                        </fo:block>
                    </xsl:for-each>
                    <fo:block>
                        Signature:
                    </fo:block>
                    <fo:block font-style="italic">
                        <xsl:value-of select="n:coverLetter/n:authorSignature"/>
                    </fo:block>
                    <fo:block>
                        <fo:table margin="10px auto 10px auto" border="1px" font-size="10">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row >
                                    <fo:table-cell padding="2px" text-align-last="center">
                                        <fo:block>
                                            Organization:
                                        </fo:block>
                                        <fo:block>
                                            <xsl:value-of select="n:coverLetter/n:contactInformation/n:organisationName"/>
                                            <xsl:call-template name="TAddress">
                                                <xsl:with-param name="address" select = "n:coverLetter/n:contactInformation/n:organisationAddress" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2px" text-align-last="center">
                                        <fo:block>
                                            Contact person:
                                            <xsl:call-template name="TPerson">
                                                <xsl:with-param name="person" select = "n:coverLetter/n:contactInformation/n:contactPerson" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
