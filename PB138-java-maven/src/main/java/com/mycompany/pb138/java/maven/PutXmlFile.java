package com.mycompany.pb138.java.maven;

import java.io.File;

import org.exist.xmldb.XmldbURI;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

/**
 * PutXmlFile
 * 
 * This class is used to put XML files into database.
 * When database does not exist it is created.
 */
public class PutXmlFile {

	public final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";


	public static int putXmlFile(File f) throws Exception {

		String collection = "/db/grammars";
                //File f = new File(file);
                
        // initialize driver
		String driver = "org.exist.xmldb.DatabaseImpl";
		Class<?> cl = Class.forName(driver);			
		Database database = (Database)cl.newInstance();
		database.setProperty("create-database", "true"); //creates db if does not exist
		DatabaseManager.registerDatabase(database);
		
        // try to get collection
		Collection col = 
			DatabaseManager.getCollection(URI + collection);
		if(col == null) {
            // collection does not exist: get root collection and create.
            Collection root = DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION);
            CollectionManagementService mgtService = 
                (CollectionManagementService)root.getService("CollectionManagementService", "1.0");
            col = mgtService.createCollection(collection.substring((XmldbURI.ROOT_COLLECTION + "/").length()));
        }
		
        // create new XMLResource
		XMLResource document = (XMLResource)col.createResource(f.getName(), "XMLResource");
		document.setContent(f);
		System.out.print("storing document " + document.getId() + "...");
		col.storeResource(document);
		System.out.println("ok.");
                
                return 0;
	}
}
