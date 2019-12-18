<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:import href="common.xsl"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="coverLetter-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="coverLetter-page">
                <fo:flow flow-name="xsl-region-body">
                    
                    <fo:block text-indent="10px">
                        <fo:inline font-weight="bold">
                            <xsl:value-of select="n:coverLetter/@date"/>
                        </fo:inline>
                    </fo:block>
                    <fo:block>
                        <fo:table font-family="serif" margin="50px auto 50px auto" border="1px">
                            <fo:table-column column-width="50%"/>
                            <fo:table-column column-width="50%"/>
                            <fo:table-body>
                                <fo:table-row border="1px solid darkgrey">
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>
                                            <xsl:call-template name="TPerson">
                                                <xsl:with-param name="person" select = "n:coverLetter/n:contactInformation/n:contactPerson" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <fo:block>
                                            <xsl:call-template name="TPerson">
                                                <xsl:with-param name="person" select = "n:coverLetter/n:author" />
                                            </xsl:call-template>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row border="1px solid darkgrey" width="100%">
                                    <fo:table-cell width="100%" background-color="#4caf50" font-family="sans-serif" color="white" padding="10px" font-weight="bold">
                                        <xsl:for-each select="n:coverLetter/n:content">
                                            <fo:block>
                                                <xsl:call-template name="TChapter">
                                                    <xsl:with-param name="paragraph" select = "." />
                                                </xsl:call-template>
                                            </fo:block>
                                        </xsl:for-each>
                                        <fo:block>
                                            <xsl:value-of select="n:coverLetter/n:authorSignature"/>
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
