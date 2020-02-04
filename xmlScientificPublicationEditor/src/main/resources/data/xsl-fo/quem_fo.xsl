<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns="http://www.uns.ac.rs/Tim1"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    <xsl:import href="common_fo.xsl"/>
    <xsl:template match="/">
    <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="questionnaire-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:for-each select="ns:qs/ns:questionnaire">
            <fo:page-sequence master-reference="questionnaire-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align-last="justify">
                        <fo:leader leader-pattern="space" />
                        <xsl:value-of select="./ns:date"/>
                    </fo:block>
                    <fo:block text-align-last="center">
                        <fo:inline font-size="24">
                            Questionnaire
                        </fo:inline>
                    </fo:block>
                    <fo:block margin-bottom="5px" margin-top="5px">
                        <xsl:for-each select="./ns:content">
                            <fo:block>
                                <xsl:call-template name="TParagraph"/>
                            </fo:block>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block margin-bottom="5px" margin-top="5px">
                        <xsl:for-each select="./ns:questions/ns:question">
                            <fo:block margin-bottom="5px" margin-top="5px">
                                <xsl:call-template name="TQusetion">
                                    <xsl:with-param name="qusetion" select="."></xsl:with-param>
                                </xsl:call-template>
                            </fo:block>
                        </xsl:for-each>
                    </fo:block>
                    <fo:block font-size="14px" margin-top="5px">
                        Mark: <xsl:value-of select="./ns:mark"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
            </xsl:for-each>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
