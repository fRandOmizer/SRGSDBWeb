package com.mycompany.pb138.java.maven;
import java.util.ArrayList;
import java.util.List;

/*
 * Rule
 *
 * Represents a rule in a grammar. Each rule consist of one nonterminal
 * and one ore more unique terminals.
 */
public class Rule {
    
    private String nonterminal;
    private List<String> terminals = new ArrayList<String>();


    /**
     * Rule constructor
     * 
     * @param nonterminal just one nonterminal given as a string
     * @param terminals one ore more terminals given as string array
     */
    public Rule(String nonterminal, String[] terminals) {
        this.nonterminal = nonterminal;
        for(String terminal : terminals){
            this.terminals.add(terminal);
        }
    }


    /**
     * Add one ore more terminals to the rule. Duplicate terminals 
     * in rule are ignored.
     * 
     * @param terminals one ore more terminals given as string array
     */
    public void addTerminals(String[] terminals) {
        for (String terminal : terminals) {
            if(this.terminals.contains(terminal) ){
                continue;
            }
            this.terminals.add(terminal);
        }
    }

    /**
     * Returns nonterminal
     * 
     * @return  nonterminal of rule
     */
    public String getNonterminal(){
        return nonterminal;
    }
    
    /**
     * Returns terminals in string array
     * 
     * @return all terminals of rule
     */
    public String[] getTerminals(){
        return terminals.toArray(new String[terminals.size()]);
    }
}
