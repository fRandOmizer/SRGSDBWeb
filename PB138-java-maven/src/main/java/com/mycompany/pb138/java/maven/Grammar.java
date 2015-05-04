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

    List<Rule> rules = new ArrayList<Rule>();

    public Grammar(String name) {
        this.name = name;
    }

    public void addRule(Rule newRule) {
        for(Rule rule : rules){
            if( rule.getNonterminal().equals(newRule.getNonterminal()) ){
                rule.addTerminals(newRule.getTerminals());
                return;
            }
                
        }
        rules.add(newRule);
    }

    public List<Rule> getRules() {
        return new ArrayList(rules);
    }

    public String getName() {
        return name;
    }

}
