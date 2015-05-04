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
    
    private String nonterminal;
    private List<String> terminals = new ArrayList<String>();

    public Rule(String nonterminal, String[] terminals) {
        this.nonterminal = nonterminal;
        for(String terminal : terminals){
            this.terminals.add(terminal);
        }
    }

    public void addTerminals(String[] terminals) {
        for (String terminal : terminals) {
            if(this.terminals.contains(terminal) ){
                return;
            }
            this.terminals.add(terminal);
        }
    }

    public String getNonterminal(){
        return nonterminal;
    }
    
    public String[] getTerminals(){
        return terminals.toArray(new String[terminals.size()]);
    }
}
