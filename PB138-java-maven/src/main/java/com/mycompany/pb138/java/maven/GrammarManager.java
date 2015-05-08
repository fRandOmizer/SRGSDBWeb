package com.mycompany.pb138.java.maven;
import java.util.ArrayList;
import java.util.List;

/*
 * GrammarManager
 *
 * Manages grammars - can load grammars in SRGS format from XML file,
 * can edit grammars and then save then back to XML file.
 */
public class GrammarManager {
    
    private List<Grammar> grammars = new ArrayList<Grammar>();
    private String xmlFile;

    /**
     * GrammarManager constructor
     * 
     * @param xmlFile name of the file where grammars are stored in
     */
    public GrammarManager(String xmlFile) {  
        this.xmlFile = xmlFile;
    }
   
    /**
     * Gets all the grammars in a new List<Grammar>
     * 
     * @return all grammars
     */
    public List<Grammar> getGrammars(){
        return new ArrayList(grammars);
    }
    
    /**
     * Adds a grammar to the manager 
     * 
     * @param newGrammar New grammar to add
     */
    public void addGrammar(Grammar newGrammar){
        grammars.add(newGrammar);
    }
    
    /**
     * Adds a rule to the grammar.
     * 
     * If grammar does not exist in the manager, fires IllegalArgumentException.
     * 
     * @param grammar New grammar to add
     * @param rule New rule to add
     */
    public void addRuleToGrammar(Grammar grammar, Rule rule){
        if(grammars.contains(grammar) ){
            grammar.addRule(rule);
        }
        else
        {
            throw new IllegalArgumentException("Grammar does not exist in database.");
        }
    }
    
    /**
     * Returns a grammar with required rootRule
     * 
     * If such a grammar does not exist in the manager, fires IllegalArgumentException.
     * 
     * @param rule New rule to add
     */
    public Grammar findGrammarByRootRule(Rule rootRule){
        //TODO
       throw new IllegalArgumentException("Grammar does not exist in database.");
    }
    
    /**
     * Loads grammars from XML file, specified in constructor
     */
    public void loadGrammarsFromXML(){
        //TODO
        createTestData();
    }
    
    /**
     * Saves grammars to XML file, specified in constructor
     */
    public void saveGrammarsToXML(){
        //TODO
    }
    
    /**
     * TEST DATA - TEMPORARY
     */
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
