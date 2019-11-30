package com.example.xmlScientificPublicationEditor.util;

import static com.example.xmlScientificPublicationEditor.util.template.XUpdateTemplate.REMOVE;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

import com.example.xmlScientificPublicationEditor.util.AuthenticationUtilities;

public class UpdateDB {
    
    public static long update(String collectionId, String contextXPath, String xmlFragment, String updateTemplate ) throws Exception
    {
        long mods = 0;
        Collection col = AuthenticationUtilities.initDBCollection(collectionId);
        try{
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            mods = xupdateService.update(String.format(updateTemplate, contextXPath, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");
        // long mods = xupdateService.updateResource(,String.format(INSERT_AFTER, contextXPath, xmlFragment));
            return mods;    
        }
        finally {
            if(col != null) {
                try { 
                	col.close();
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
    }

    public static long delete(String collectionId, String contextXPath) throws Exception{
        long mods = 0;
        Collection col = AuthenticationUtilities.initDBCollection(collectionId);
        try{
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            mods = xupdateService.update(String.format( REMOVE, contextXPath));
            System.out.println("[INFO] " + mods + " modifications processed.");
            return mods;
        }
        finally {
            if(col != null) {
                try { 
                	col.close();
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
    } 
}
