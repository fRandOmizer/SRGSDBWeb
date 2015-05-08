package com.mycompany.pb138.java.maven;
import java.util.ArrayList;
import java.util.List;

/*
 * Grammar
 *
 * This class repressents a grammar. Grammar is a system of rules
 * with just one root role. First rule is set as root rule by default
 * and can be changed later.
 */
public class Grammar {

    public String name;

    List<Rule> rules = new ArrayList<Rule>();
    int rootRuleIndex = 0;

    /**
     * Grammar constructor 
     * 
     * @param name Name of a grammar
     */
    public Grammar(String name) {
        this.name = name;
    }

    /**
     * Adds a rule to the grammar 
     * 
     * @param newRule New rule to add
     */
    public void addRule(Rule newRule) {
        for(Rule rule : rules){
            if( rule.getNonterminal().equals(newRule.getNonterminal()) ){
                rule.addTerminals(newRule.getTerminals());
                return;
            }               
        }
        rules.add(newRule);
    }

    /**
     * Gets all the rules in a new List<Rule>
     * 
     * @return all rules
     */
    public List<Rule> getRules() {
        return new ArrayList(rules);
    }
    
    /**
     * Sets already existing rule as a new root rule.
     * If rule does not exist in manager, IllegalArgumentException is thrown.
     * 
     * @param rule new root rule
     */
    public void setRootRule(Rule rule) {
        if(rules.contains(rule)){
            rootRuleIndex = rules.indexOf(rule);
        }
        else
            throw new IllegalArgumentException("Rule does not exist!");
    }
    
     /**
     * Gets root rule
     * 
     * @return root rule
     */
    public Rule getRootRule() {
        return rules.get(rootRuleIndex);
    }

    /**
     * Gets name of the grammar
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

}
