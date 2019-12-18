<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:import href="common.xsl"/>

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
                <div>
                    <xsl:call-template name="TChapter">
                        <xsl:with-param name="paragraph" select = "n:notification/n:content" />
                    </xsl:call-template>
                </div>
                <br/>
                <p>
                    Date:
                    <xsl:value-of select="n:notification/n:date"/>
                </p>
            </body>
        </html>
    </xsl:template>


</xsl:stylesheet>
