<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:n="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:template name="TPerson">
        <xsl:param name = "person"/>
        <div>
            <xsl:value-of select="$person/n:name"/><br/>
            <xsl:value-of select="$person/n:surname"/><br/>
            <xsl:value-of select="$person/n:email"/>
        </div>
    </xsl:template>
    
    <xsl:template name="TAuthor">
        <xsl:param name="author"/>
        <div>
            <xsl:value-of select="$author/n:name"/>&#160;<xsl:value-of select="$author/n:surname"/><br/>
            <xsl:value-of select="$author/n:institution/n:name"/><br/>
            <xsl:value-of select="$author/n:institution/n:address/n:city"/>, <xsl:value-of select="$author/n:institution/n:address/n:country"/><br/>
            <xsl:value-of select="$author/n:phone"/><br/>
            <xsl:value-of select="$author/n:email"/>
        </div>
    </xsl:template>

    <xsl:template match="n:chapter" name="TChapter">
        <xsl:param name="chapter"/>
        <h2 style="margin:10px">
            <xsl:attribute name="id">
                <xsl:value-of select="$chapter/@id"/>
            </xsl:attribute>
            <xsl:value-of select="$chapter/@title"/>
        </h2>
        <xsl:for-each select=".">
            <xsl:apply-templates></xsl:apply-templates>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="n:subchapter" name="TSubchapter">
        <div style="margin: auto auto auto 10px">
            <h3 style="margin:10px">
                <xsl:attribute name="id">
                    <xsl:value-of select="./@id"/>
                </xsl:attribute>
                <xsl:value-of select="./@title"/>
            </h3>
            <xsl:for-each select=".">
                <xsl:apply-templates></xsl:apply-templates>
            </xsl:for-each>
        </div>
    </xsl:template>
    
    <xsl:template match="n:paragraph | n:answer" name="TParagraph">
        <xsl:for-each select="./*">
            <xsl:if test="name(.) = 'ns:text'">   
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
                    <p> Description:<br/>
                        <xsl:apply-templates select="./n:description"></xsl:apply-templates><br/>
                        Formula:<br/>
                        <xsl:apply-templates select="./n:content"></xsl:apply-templates>
                    </p>
                </div>
            </xsl:if>
            <!--make oredered and unordered list-->
            <xsl:if test="name(.) ='list'">
                <xsl:choose>
                    <xsl:when test="@type='ordered'">
                        <ol>
                            <xsl:for-each select="./*">
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </xsl:for-each>
                        </ol>
                    </xsl:when>
                    <xsl:otherwise>
                        <ul>
                            <xsl:for-each select="./*">
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </xsl:for-each>
                        </ul>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:if test="name(.) ='image'">
                <div>
                   <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="./n:source"/>
                        </xsl:attribute>
                       <xsl:attribute name="alt">
                           Image not found
                       </xsl:attribute>
                       <xsl:attribute name="width">
                           <xsl:value-of select="./@width"/>
                       </xsl:attribute>
                       <xsl:attribute name="height">
                           <xsl:value-of select="./@height"/>
                       </xsl:attribute>
                   </img>
                   <p>
                       <small>
                           <xsl:value-of select="./n:description"/>
                       </small>
                   </p>
                </div>
            </xsl:if>
            <xsl:if test="name(.) ='table'">
                <table border="1">
                    <xsl:for-each select="./n:table_row">
                        <tr>
                            <xsl:for-each select="./n:table_cell">
                                <td>
                                    <xsl:apply-templates></xsl:apply-templates>
                                </td>
                            </xsl:for-each>
                        </tr>
                    </xsl:for-each>
                </table>
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
    <xsl:template match="n:bold/text()">
        <xsl:copy-of select="." />
    </xsl:template>
    
    <!-- override rule: copy any text node beneath italic -->
    <xsl:template match="n:italic/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <!-- override rule: copy any text node beneath underline -->
    <xsl:template match="n:underline/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:description/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:cursive/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="n:address | n:organisationAddress" name="TAddress">
        <xsl:param name = "address"/>
        <div>
            <xsl:value-of select="$address/n:city"/>, 
            <xsl:value-of select="$address/n:country"/>
            <br/>
            <xsl:value-of select="$address/n:street"/> 
            <xsl:value-of select="$address/n:streetNumber"/> 
            <xsl:value-of select="$address/n:floorNumber"/>
            <br/>
        </div>
    </xsl:template>
    
    <xsl:template name="TChapterContent">
        <xsl:param name="chapter"/>
        <xsl:variable name="link" select="$chapter/@id"/>
        <a href="#{$link}" style="font-size:20px;">
            <xsl:value-of select="$chapter/@title"/>
        </a><br/>
        <xsl:for-each select="$chapter/n:subchapter">
            <xsl:call-template name="TSubchapterContent">
                <xsl:with-param name="subchapter" select="."></xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="TSubchapterContent">
        <xsl:param name="subchapter"/>
        <div style="margin: auto auto auto 10px">
            <xsl:variable name="link" select="$subchapter/@id"/>
            <a href="#{$link}" style="font-size:16px;">
                <xsl:value-of select="$subchapter/@title"/>
            </a><br/>
            <xsl:for-each select="$subchapter/n:subchapter">
                <xsl:call-template name="TSubchapterContent">
                    <xsl:with-param name="subchapter" select="."></xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>    
        </div>
    </xsl:template>
    
    <xsl:template name="TQusetion">
        <xsl:param name="qusetion"/>
        <div>
            <span class="dot"></span>
            <xsl:value-of select="$qusetion/n:questionText"/>
            <xsl:apply-templates select="$qusetion/n:answer"></xsl:apply-templates>
        </div>
    </xsl:template>
</xsl:stylesheet>