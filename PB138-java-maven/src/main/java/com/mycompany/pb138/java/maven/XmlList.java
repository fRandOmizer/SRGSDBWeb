/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pb138.java.maven;

import java.io.File;

/**
 *
 * @author Richard
 */
public class XmlList {
    private String rootId;
    private File xml;
    
    public void setRootId(String rootId)
    {
        this.rootId=rootId;
    }
    public void setXml(File xml)
    {
        this.xml=xml;
    }
    public String  getRootId()
    {
        return rootId;
    }
    public File getXml()
    {
        return xml;
    }
    public XmlList()
    {
        
    }
    
}
