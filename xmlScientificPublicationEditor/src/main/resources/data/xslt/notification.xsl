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


    <xsl:template name="TChapter">
        <xsl:param name = "paragraph" />
        <xsl:for-each select="$paragraph/*">
                    <xsl:if test="name(.) = 'text'">   
                        <p>
                            <xsl:apply-templates></xsl:apply-templates>
                        </p>
                    </xsl:if>
                    <!--quote in notification-->
                    <xsl:if test="name(.) ='quote'">
                        <q>
                            <xsl:apply-templates></xsl:apply-templates>
                        </q>
                    </xsl:if>
                    <xsl:if test="name(.) ='formula'">
                        <div>
                            <p> Description:
                                <br/>
                                <xsl:apply-templates select="./n:description"></xsl:apply-templates>
                            </p>
                            <br/>
                            <p>
                                Formula:
                                <xsl:apply-templates select="./n:content"></xsl:apply-templates>
                            </p>
                        </div>
                    </xsl:if>
                    <!--make oredered and unordered list-->
                    <xsl:if test="name(.) ='list'">
                        <xsl:if test="@type='ordered'">
                            <ol>
                                <xsl:for-each select="./*">
                                    <xsl:apply-templates select="."></xsl:apply-templates>
                                </xsl:for-each>
                            </ol>
                        </xsl:if>
                        <xsl:if test="@type='unordered'">
                            <ul>
                                <xsl:for-each select="./*">
                                    <xsl:apply-templates select="."></xsl:apply-templates>
                                </xsl:for-each>
                            </ul>
                        </xsl:if>
                    </xsl:if>
                </xsl:for-each>
    </xsl:template>

    <xsl:template match="n:listItem/n:text" name="TListItem">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./*">
                    <li>  
                        <xsl:apply-templates select="."></xsl:apply-templates>
                    </li>
                </xsl:for-each>
    </xsl:template>

    <!-- TCursive and it's subnodes templates -->
    <xsl:template match="n:cursive | n:description" name="TCursive">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
                </xsl:for-each>
    </xsl:template>

    <xsl:template match="n:bold">
        <b>
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </b>
    </xsl:template>

    <xsl:template match="n:italic">
        <i>
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </i>
    </xsl:template>

    <xsl:template match="n:underline">
        <u>
            <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </u>
    </xsl:template>

    <!-- override rule: copy any text node beneath bold -->
    <xsl:template match="n:bold//text()">
        <xsl:copy-of select="." />
    </xsl:template>
    
    <!-- override rule: copy any text node beneath italic -->
    <xsl:template match="n:italic//text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <!-- override rule: copy any text node beneath underline -->
    <xsl:template match="n:underline//text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:description//text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:cursive//text()">
        <xsl:copy-of select="." />
    </xsl:template>

</xsl:stylesheet>
