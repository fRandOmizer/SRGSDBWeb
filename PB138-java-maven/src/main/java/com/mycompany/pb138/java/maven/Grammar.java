package com.mycompany.pb138.java.maven;


import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tomas
 */
public class Grammar {
    
   public String name;
   
   List <Rule> rules = new ArrayList<Rule>(); //v dalsom commite pravdepodobne mapa

    public Grammar(String name) {
        this.name=name;
        
    }
    
    public void addRule(Rule rule){
        rules.add(rule);
    }
    
    public List<Rule> getRules(){
       return rules; 
    }
    
    public String getName(){
        return name;
    }
    
   
   
  
    
}
