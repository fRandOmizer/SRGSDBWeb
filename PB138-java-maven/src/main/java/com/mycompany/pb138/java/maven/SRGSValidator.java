package com.mycompany.pb138.java.maven;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * SRGSValidator
 * 
 * SRGSValidator validates SRGS grammar.
 *
 * @author svs
 */
public class SRGSValidator {
    
    private Validator validator; //validator object
    private String syntaxErorr; // syntax erorr in file if not valid
    
    public SRGSValidator() {
    
    
            SchemaFactory factory = SchemaFactory.newInstance( "http://www.w3.org/2001/XMLSchema");
            ResourceBundle pathResource = ResourceBundle.getBundle("appPath/appPath"); 
            File XSDFile = new File(pathResource.getString("path")  + "grammar.xsd");
        
        try {
            Schema schema = factory.newSchema(XSDFile);
            validator = schema.newValidator();

        } catch (SAXException e) {
            System.err.println("Error reading XML Schema: ");
        }
    }
    
    /**
     * 
     * @param InputStream in file to be validated
     * @return true if file is valid SRGDv1.0 file
     */
        public boolean validate(InputStream in) {
            Source source = new StreamSource(in);
            try {
                validator.validate(source);
                System.out.println( "Validates.");
                return true;
            }
            catch (SAXParseException e) {
                System.out.println("Fails to validate because: \n");
                System.out.println(e.toString());
                setSyntaxError(e.getMessage());
                return false;
            }
            catch (SAXException e) {
                System.out.println("Fails to validate because: \n");
                System.out.println(e.getMessage());
                setSyntaxError(e.getMessage());
                return false;
            }
            catch (IOException io) {
                System.err.println("Error reading XML source: ");
                System.err.println(io.getMessage());
                return false;
            }

    }  
        
        public void setSyntaxError (String err) {
            this.syntaxErorr = err;
        }
        
        public String getSyntaxError() {
            return this.syntaxErorr;
        }
    
}