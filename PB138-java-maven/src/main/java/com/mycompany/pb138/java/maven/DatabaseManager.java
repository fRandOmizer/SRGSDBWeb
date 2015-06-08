/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pb138.java.maven;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import org.basex.core.*;
import org.basex.core.cmd.*;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Native XML Database manager
 * 
 * @author Richard
 */
public class DatabaseManager {
    
    //connection to database
    private Context context;
    
    //database name
    private final String DBname = "XmlDB";
    ResourceBundle pathResource = ResourceBundle.getBundle("appPath/appPath");  
    private final String Path = pathResource.getString("path")  + "src/main/resources/";
    
    //absolute path to database
    //private final String Path = "C:/Users/Richard/Desktop/PB138-java-maven/src/main/resources/";
    
    
    /**
     * Initialization of Database
     */
    public DatabaseManager()
    {
        try
        {
            context = new Context();
            new CreateDB(DBname, Path).execute(context);
            new Open(DBname).execute(context);
            new CreateIndex("fulltext").execute(context);
        }
        catch(BaseXException ex)
        {
           
        }
    }
    
    /**
     * Add grammar to database
     * 
     * @param in InputStream of the file
     * @param fileName Name of the file
     */
    public void addGrammar(InputStream in, String fileName)
    {
        try
        {
            
            File output = new File(Path+fileName);
            
            while (output.exists() && !output.isDirectory()) 
            {
                String newName=output.getName().substring(0,output.getName().indexOf('.'));
                newName+="x.xml";
                output = new File(Path+newName);
            }
            
            OutputStream out = new FileOutputStream(output);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            
            this.CloseManager();
            
            //rfresh database
            context = new Context();
            new CreateDB(DBname, Path).execute(context);
            new Open(DBname).execute(context);
            new CreateIndex("fulltext").execute(context);
        }
        catch (IOException ex)
        {
            System.err.println("IO error: "+ex.getMessage());
        }   
    }
    /**
     * Execute XQuery on database to find files that have desired root id
     * 
     * @param rootId name of the root rule in grammar
     * @return       list of files where given root exist
     */
    public List<XmlList> findByRoot(String rootId)
    {
        List<XmlList> xmlFiles = new ArrayList<XmlList>();
        
        try
        {
            //find all files in database
            String queryResult = new XQuery(
                                            "for $doc in collection('"+DBname+"')" +
                                            "return <doc path='{ base-uri($doc) }'/>"
                                        ).execute(context);
            
            System.out.println(queryResult);
            
            String[] files = queryResult.split("<");
            
            for(int i=1; i< files.length;i++)
            {
                //parse the name of file
                files[i]=files[i].substring(files[i].indexOf('/')+1,files[i].length()-1);
                files[i]=files[i].substring(0,files[i].indexOf('.'));
                files[i]+=".xml";
            }
            
            for(int i=1; i< files.length;i++)
            {
                //find file with given root id
                //String query = "for $doc in doc('"+DBname+"/"+files[i]+"') return $doc/grammar/@root='" + rootId+"'";
                String query = "declare namespace ha=\"http://www.w3.org/2001/06/grammar\";" + "for $doc in doc('"+DBname+"/"+files[i]+"') return data($doc/ha:grammar/@root='" + rootId+"')";
                String result = new XQuery(query).execute(context);
                
                //add to result
                if(result.equals("true"))
                {
                    XmlList xmlFile=new XmlList();
                    xmlFile.setRootId(rootId);
                    File file = new File(Path+files[i]);
                    xmlFile.setXml(file);
                    xmlFiles.add(xmlFile);
                }
            }
        }
        catch( Exception ex )
        {
            System.err.println(ex);
        }
        return xmlFiles;
    }
    
    /**
     * Find all grammars with their rootId by XQuery
     * @return list of files with root id
     */
    public List<XmlList> getAllGrammar()
    {
        List<XmlList> xmlFiles = new ArrayList<XmlList>();
        
        try
        {
            //find all files in database
            String queryResult = new XQuery(
                                            "for $doc in collection('"+DBname+"')" +
                                            "return <doc path='{ base-uri($doc) }'/>"
                                        ).execute(context);
            
            System.out.println(queryResult);
            
            String[] files = queryResult.split("<");
            
            for(int i=1; i< files.length;i++)
            {
                files[i]=files[i].substring(files[i].indexOf('/')+1,files[i].length()-1);
                files[i]=files[i].substring(0,files[i].indexOf('.'));
                files[i]+=".xml";
            }
            
            for(int i=1; i< files.length;i++)
            {
                
                //String query = "for $doc in doc('"+DBname+"/"+files[i]+"') return data($doc/grammar/@root)";
                String query = "declare namespace ha=\"http://www.w3.org/2001/06/grammar\";" + "for $doc in doc('"+DBname+"/"+files[i]+"') return data($doc/ha:grammar/@root)";
                String result = new XQuery(query).execute(context);

                XmlList xmlFile=new XmlList();
                xmlFile.setRootId(result);
                File file = new File(Path+files[i]);
                xmlFile.setXml(file);
                xmlFiles.add(xmlFile);
            }
        }
        catch( Exception ex )
        {
            System.err.println(ex);
        }
        return new ArrayList(xmlFiles);
    }
    
    /**
     * Close database and connection
     */
    public void CloseManager()
    {
        try
        {
            new DropIndex("text").execute(context);
            new DropIndex("attribute").execute(context);
            new DropIndex("fulltext").execute(context);
            new DropDB(DBname).execute(context);
            //new Close().execute(context);
            context.close();
        }
        catch(BaseXException ex)
        {
            
        }
    }
    
     /**
     * Returns required XML file
     * 
     * @param name name of the file
     * 
     * @return File file
     */
    public File getXmlFileByName(String name){
        return new File(Path+name);
    }
    
    
    /**
     * Add rule to given XML
     * 
     * @param id        root name
     * @param strRule   rule to add to file
     * @throws AddRuleException
     * @throws IllegalArgumentException 
     */
    public void addRuleToGrammar(String id, String strRule) throws AddRuleException, IllegalArgumentException{

        //rule to DOM
        Document documentS;  //input
        try {
            documentS = convertStringToDocument(strRule);
            documentS.getDocumentElement().normalize();
        } catch (AddRuleException e) {
            System.err.println("Error  in converting input string to DOM Document : " + e.getMessage());
            throw new AddRuleException("Error  in converting input string to DOM Document : " + e.getMessage());
        }

        NodeList rules = documentS.getElementsByTagName("rule");

        //append rule
        
            Document document;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(getXmlFileByName(id));
                document.getDocumentElement().normalize();
            } catch (ParserConfigurationException e){
                System.err.println("ParserConfigurationException[parsing file from DB]: " + e.getMessage());
                throw new AddRuleException("ParserConfigurationException[parsing file from DB]: " + e.getMessage());
            } catch (SAXException e) {
                System.err.println("SAXException[parsing file from DB]: " + e.getMessage());
                throw new AddRuleException("SAXException[parsing file from DB]: " + e.getMessage());
            }catch (IOException io) {
                System.err.println("IOException[parsing file from DB]: " + io.getMessage());
                throw new AddRuleException("IOException[parsing file from DB]: " + io.getMessage());
            }

            NodeList grammarList = document.getElementsByTagName("grammar");
            Element grammar = (Element) grammarList.item(0);

            //copies node and transfers it to destination document
            try{
            Node newNode = document.importNode(rules.item(0), true);
            grammar.appendChild((Element) newNode);
            }
            catch(Exception e){
                throw new IllegalArgumentException("Rule is not in SRGSR format! "+e.getMessage());
            }
            // write to xml
            try{
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");    //indentation
                Result output = new StreamResult(getXmlFileByName(id));
                Source input = new DOMSource(document);
                transformer.transform(input, output);
            } catch (TransformerConfigurationException e){
                System.err.println("TransformerConfigurationException[saving DOM to xml]: " + e.getMessage());
                throw new AddRuleException("TransformerConfigurationException[saving DOM to xml]: " + e.getMessage());
            } catch (TransformerException e) {
                System.err.println("TransformException:[saving DOM to xml] " + e.getMessage());
                throw new AddRuleException("TransformException:[saving DOM to xml] " + e.getMessage());
            }
        
    }
    
    /**
     * Converts String to Document format 
     * (needed in addRuleToGrammar)
     * 
     * @param xmlStr
     * @return 
     */
    public static Document convertStringToDocument(String xmlStr) throws AddRuleException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (ParserConfigurationException e){
            System.err.println("ParserConfigurationException: " + e.getMessage());
                    throw new AddRuleException("ParserConfigurationException: " + e.getMessage());
        }catch (SAXException e) {
            System.err.println("SAXException: " + e.getMessage());
                    throw new AddRuleException("SAXException: " + e.getMessage());
        }catch (IOException io) {
            System.err.println("IOException: " + io.getMessage());
                    throw new AddRuleException("IOException: " + io.getMessage());
        }
    }
    
}
