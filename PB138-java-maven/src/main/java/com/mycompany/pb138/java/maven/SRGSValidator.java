package com.mycompany.pb138.java.maven;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    private String ex; //exception text if file is not valid
    
    public SRGSValidator() {
    
    
            SchemaFactory factory = SchemaFactory.newInstance( "http://www.w3.org/2001/XMLSchema");
             File XSDFile = new File("grammar.xsd");
        
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
                System.out.println( " validates.");
                return true;
            }
            catch (SAXParseException e) {
                System.out.println(" fails to validate because: \n");
                System.out.println(e.toString());
                ex = e.toString();
                return false;
            }
            catch (SAXException e) {
                System.out.println(" fails to validate because: \n");
                System.out.println(e.getMessage());
                ex = e.getMessage();
                return false;
            }
            catch (IOException io) {
                System.err.println("Error reading XML source: ");
                System.err.println(io.getMessage());
                return false;
            }

    }   
    
}
