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
public class GrammarManager {
    
    private List<Grammar> grammars = new ArrayList<Grammar>();
    private String xmlFile;

    public GrammarManager(String xmlFile) {  
        this.xmlFile = xmlFile;
    }
   
    public List<Grammar> getGrammars(){
        return new ArrayList(grammars);
    }
    
    public void addGrammar(Grammar newGrammar){
        grammars.add(newGrammar);
    }
    
    public void addRuleToGrammar(Grammar g, Rule r){
        if(grammars.contains(g) ){
            g.addRule(r);
        }
        else
        {
            throw new IllegalArgumentException("Grammar does not exist in database.");
        }
    }
    
    public void findGrammarByRootRule(){
        //TODO
    }
    
    public void loadGrammarsFromXML(){
        //TODO
        createTestData();
    }
    
    public void createTestData(){

        Grammar grammar1 = new Grammar("Test Grammar #1");
        grammar1.addRule(new Rule("Cities", new String[]{"Bratislava", "Brno", "Kosice", "Praha"}));
        grammar1.addRule(new Rule("Cities", new String[]{"Zlin"}));
        grammar1.addRule(new Rule("Cities", new String[]{"Zlin"}));
        grammar1.addRule(new Rule("Food", new String[]{"Tomatoes", "Cheese"}));
        grammar1.addRule(new Rule("Food", new String[]{"Salami"}));
        grammar1.addRule(new Rule("Colors", new String[]{"Red", "Blue", "Purple", "Orange"}));

        Grammar grammar2 = new Grammar("Test Grammar #2");
        grammar2.addRule(new Rule("Lorem", new String[]{"ipsum", "dolor", "sit", "amet"}));
        grammar2.addRule(new Rule("consectetur", new String[]{"adipiscing", "elit", "Maecenas", "tincidunt"}));
        
        Grammar grammar3 = new Grammar("Test Grammar #3 - withous rules");
        
        grammars.add(grammar1);
        grammars.add(grammar2);
        grammars.add(grammar3);
    }
    
    
    
    
}
