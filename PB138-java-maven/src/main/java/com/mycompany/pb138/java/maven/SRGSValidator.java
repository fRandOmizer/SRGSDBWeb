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
    
    /**
     * Sets schemas for validator.
     * Needs files: grammar.xsd, grammar-core.xsd and xml.xsd.
     */
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
     * Validates InputStream given. If it is not valid, sets validator's
     * exception text to reason.
     * 
     * @param InputStream in file to be validated
     * @return true if file is valid SRGSv1.0 file
     */
        public boolean validate(InputStream in) {
            Source source = new StreamSource(in);
            try {
                validator.validate(source);
                System.out.println( "Input validates.");
                return true;
            }
            catch (SAXParseException e) {
                System.out.println("Input fails to validate because: \n");
                System.out.println(e.toString());
                ex = e.toString();
                return false;
            }
            catch (SAXException e) {
                System.out.println("Input fails to validate because: \n");
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

        /**
         * 
         * @return Text of exception in validation.
         */
        
        public String getValidationError () {
            return ex;
        }
    
}
