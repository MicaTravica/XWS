<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:ns="http://www.uns.ac.rs/Tim1" version="2.0">

    <xsl:template name="TPerson">
        <xsl:param name = "person"/>
        <div>
            <xsl:value-of select="$person/ns:name"/><br/>
            <xsl:value-of select="$person/ns:surname"/><br/>
            <xsl:value-of select="$person/ns:email"/>
        </div>
    </xsl:template>
    
    <xsl:template name="TAuthor">
        <xsl:param name="author"/>
        <div id="{$author/@id}">
            <xsl:value-of select="$author/ns:name"/>&#160;<xsl:value-of select="$author/ns:surname"/><br/>
            <xsl:value-of select="$author/ns:institution/ns:name"/><br/>
            <xsl:value-of select="$author/ns:institution/ns:address/ns:city"/>, <xsl:value-of select="$author/ns:institution/ns:address/ns:country"/><br/>
            <xsl:value-of select="$author/ns:phone"/><br/>
            <xsl:value-of select="$author/ns:email"/>
        </div>
    </xsl:template>

    <xsl:template match="ns:chapter" name="TChapter">
        <xsl:param name="chapter"/>
        <h2 style="margins:10px">
            <xsl:attribute name="id">
                <xsl:value-of select="$chapter/@id"/>
            </xsl:attribute>
            <xsl:value-of select="$chapter/@title"/>
        </h2>
        <xsl:for-each select=".">
            <xsl:apply-templates></xsl:apply-templates>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template match="ns:subchapter" name="TSubchapter">
        <div style="margins: auto auto auto 10px">
            <h3 style="margins:10px">
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
    
    <xsl:template match="ns:paragraph | ns:answer" name="TParagraph">
        <div id="{./@id}">
        <xsl:for-each select="./*">
            <xsl:if test="name(.) = 'ns:text'">   
                <p id="{./@id}">
                    <xsl:apply-templates></xsl:apply-templates>
                </p>
            </xsl:if>
            <!--quote in notification-->
            <xsl:if test="name(.) ='ns:quote'">
                <xsl:variable name="link" select="./@ref"/>
                <q cite="#{$link}" id="{./@id}">
                    <xsl:apply-templates></xsl:apply-templates>
                </q>
            </xsl:if>
            <xsl:if test="name(.) ='ns:formula'">
                <div id="{./@id}">
                    <p> Descriptions:<br/>
                        <xsl:apply-templates select="./ns:description"></xsl:apply-templates><br/>
                        Formula:<br/>
                        <xsl:apply-templates select="./ns:content"></xsl:apply-templates>
                    </p>
                </div>
            </xsl:if>
            <!--make oredered and unordered list-->
            <xsl:if test="name(.) ='ns:list'">
                <xsl:choose>
                    <xsl:when test="@type='ordered'">
                        <ol id="{./@id}">
                            <xsl:for-each select="./*">
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </xsl:for-each>
                        </ol>
                    </xsl:when>
                    <xsl:otherwise>
                        <ul id="{./@id}">
                            <xsl:for-each select="./*">
                                <xsl:apply-templates select="."></xsl:apply-templates>
                            </xsl:for-each>
                        </ul>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:if test="name(.) ='ns:image'">
                <div id="{./@id}">
                   <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="./ns:source"/>
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
                           <xsl:value-of select="./ns:description"/>
                       </small>
                   </p>
                </div>
            </xsl:if>
            <xsl:if test="name(.) ='ns:table'">
                <table border="1" id="{./@id}">
                    <xsl:for-each select="./ns:table_row">
                        <tr>
                            <xsl:for-each select="./ns:table_cell">
                                <td>
                                    <xsl:apply-templates></xsl:apply-templates>
                                </td>
                            </xsl:for-each>
                        </tr>
                    </xsl:for-each>
                </table>
            </xsl:if>
        </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template match="ns:listitem" name="TListItem">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./*">
                    <li>  
                        <xsl:apply-templates select="."></xsl:apply-templates>
                    </li>
                </xsl:for-each>
    </xsl:template>

    <!-- TCursive and it's subnodes templates -->
    <xsl:template match="ns:cursive | ns:description" name="TCursive">
            <!-- if has cursive go trought it sequence -->
                <xsl:for-each select="./* | text()">    
                    <xsl:apply-templates select="."></xsl:apply-templates>
                </xsl:for-each>
    </xsl:template>

    <xsl:template match="ns:bold">
        <b>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </b>
    </xsl:template>

    <xsl:template match="ns:italic">
        <i>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </i>
    </xsl:template>

    <xsl:template match="ns:underline">
        <u>
            <xsl:for-each select="./* | text()">    
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </u>
    </xsl:template>

    <!-- override rule: copy any text node beneath bold -->
    <xsl:template match="ns:bold/text()">
        <xsl:copy-of select="." />
    </xsl:template>
    
    <!-- override rule: copy any text node beneath italic -->
    <xsl:template match="ns:italic/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <!-- override rule: copy any text node beneath underline -->
    <xsl:template match="ns:underline/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="ns:description/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="ns:cursive/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="ns:address | ns:organisationAddress" name="TAddress">
        <xsl:param name = "address"/>
        <div>
            <xsl:value-of select="$address/ns:city"/>, 
            <xsl:value-of select="$address/ns:country"/>
            <br/>
            <xsl:value-of select="$address/ns:street"/> 
            <xsl:value-of select="$address/ns:streetNumber"/> 
            <xsl:value-of select="$address/ns:floorNumber"/>
            <br/>
        </div>
    </xsl:template>
    
    <xsl:template name="TChapterContent">
        <xsl:param name="chapter"/>
        <xsl:variable name="link" select="$chapter/@id"/>
        <a href="#{$link}" style="font-size:20px;">
            <xsl:value-of select="$chapter/@title"/>
        </a><br/>
        <xsl:for-each select="$chapter/ns:subchapter">
            <xsl:call-template name="TSubchapterContent">
                <xsl:with-param name="subchapter" select="."></xsl:with-param>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="TSubchapterContent">
        <xsl:param name="subchapter"/>
        <div style="margins: auto auto auto 10px">
            <xsl:variable name="link" select="$subchapter/@id"/>
            <a href="#{$link}" style="font-size:16px;">
                <xsl:value-of select="$subchapter/@title"/>
            </a><br/>
            <xsl:for-each select="$subchapter/ns:subchapter">
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
            <xsl:value-of select="$qusetion/ns:questionText"/>
            <xsl:apply-templates select="$qusetion/ns:answer"></xsl:apply-templates>
        </div>
    </xsl:template>
    
    <xsl:template name="TReference">
        <xsl:param name="reference"/>
        <xsl:if test="$reference/ns:book">
            <p id="{$reference/@id}">
                <xsl:value-of select="$reference/ns:book"/>
            </p>
        </xsl:if>
        <xsl:if test="$reference/ns:link">
            <p id="{$reference/@id}">
                <a href="{$reference/ns:link}">Ref</a>
            </p>
        </xsl:if>
    </xsl:template>
    
    
    <xsl:template name="TComment">
        <xsl:param name="comment"/>
        <xsl:variable name="link" select="$comment/@ref"/>
        <a href="#{$link}" style="font-size:16px;">
            <xsl:value-of select="$comment"/>
        </a>
    </xsl:template>
    
</xsl:stylesheet>