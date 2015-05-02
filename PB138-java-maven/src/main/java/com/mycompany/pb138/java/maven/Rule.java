/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pb138.java.maven;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas
 */
public class Rule {
    
    private String left;
    private List<String> right = new ArrayList<String>();

    public Rule(String left) {
        this.left = left;
        //testm data
        right.add("asd");
        right.add("qwe");
        right.add("zxc");
    }
    
    public String getLeft(){
        return left;
    }
    
    public String getRight(){
        return right.toString();
    }
    
    
         
    
}
