<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:import href="common.xsl"/>

    <xsl:template match="/">
        <html>
            <head>
                <style type="text/css">
                    tr {
                        padding-bottom: 1em;
                    }
                </style>
                <title>CoverLetter</title>
            </head>
            <body>
                <p>
                    <xsl:value-of select="n:coverLetter/@date"/>
                </p>
                <div>
                    <table>
                        <tr>
                            <td colspan="2">
                                <xsl:call-template name="TPerson">
                                    <xsl:with-param name="person" select = "n:coverLetter/n:contactInformation/n:contactPerson" />
                                </xsl:call-template>
                            </td>
                            <td>
                            </td>
                            <td colspan="2">
                                <xsl:call-template name="TPerson">
                                    <xsl:with-param name="person" select = "n:coverLetter/n:author" />
                                </xsl:call-template>
                            </td>
                        </tr>
                        <tr>
                            <br/>
                            <div>
                                <td colspan="4">
                                <xsl:for-each select="n:coverLetter/n:content">
                                    
                                        <xsl:call-template name="TChapter">
                                            <xsl:with-param name="paragraph" select = "." />
                                        </xsl:call-template>
                                    
                                </xsl:for-each>
                                </td>
                            </div>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <div>
                                    <xsl:value-of select="n:coverLetter/n:authorSignature"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>


</xsl:stylesheet>
