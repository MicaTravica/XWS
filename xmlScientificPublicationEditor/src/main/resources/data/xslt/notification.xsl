<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Notification</title>
                <style type="text/css">
                    table {
                        font-family: serif;
                        border-collapse: collapse;
                        margin: 50px auto 50px auto;
                        width: 90%;
                    }
                    th, td {
                        text-align: left;
                        padding: 30px;
                    }
                    tr:nth-child(even){ background-color: #f2f2f2 }
                    th {
                        background-color: #4caf50;
                        font-family: sans-serif;
                        color: white;
                    }
                    tr { border: 1px solid darkgrey; }
                    tr:hover {
                        font-style: italic;
                        background-color: #cae8cb;
                    }
                    body { font-family: sans-serif; }
                    p { text-indent: 30px; }
                    .sup {
                        vertical-align: super;
                        padding-left: 4px;
                        font-size: small;
                        text-transform: lowercase;
                    }
                    
                </style>
            </head>
            <body>
                <xsl:for-each select="n:notification/n:content/*">
                    <xsl:if test="name(.) = 'text'">   
                    <p>  
                    <!-- if has cursive go trought it sequence -->
                        <xsl:for-each select="./n:cursive/*">    
                            <xsl:if test="name(.) = 'bold'">
                                <b>
                                    <xsl:value-of select="."/>
                                </b>  
                            </xsl:if>
                            <xsl:if test="name(.) = 'italic'">
                                <i>
                                    <xsl:value-of select="."/>
                                </i>  
                            </xsl:if>
                            <xsl:if test="name(.) = 'underline'">
                                <u>
                                    <xsl:value-of select="."/>
                                </u>  
                            </xsl:if>
                        </xsl:for-each>
                    </p>
                    </xsl:if>
                    <!--quote in notification-->
                    <xsl:if test="name(.) ='quote'">
                        <q>
                        <xsl:for-each select="./n:cursive/*">
                            <xsl:if test="name(.) = 'bold'">
                                <b>
                                    <xsl:value-of select="."/>
                                </b>  
                            </xsl:if>
                            <xsl:if test="name(.) = 'italic'">
                                <i>
                                    <xsl:value-of select="."/>
                                </i>  
                            </xsl:if>
                            <xsl:if test="name(.) = 'underline'">
                                <u>
                                    <xsl:value-of select="."/>
                                </u>  
                            </xsl:if>
                        </xsl:for-each>
                        </q>
                    </xsl:if>
                </xsl:for-each>
                <br/>
                <p>
                    Date:
                    <xsl:value-of select="n:notification/n:date"/>
                </p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
