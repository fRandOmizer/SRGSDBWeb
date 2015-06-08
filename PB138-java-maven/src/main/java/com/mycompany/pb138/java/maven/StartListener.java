/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pb138.java.maven;

import java.io.File;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartListener implements ServletContextListener {
    
    private static final Logger log = Logger.getLogger(StartListener.class.getName());

    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Web application started!");
        ServletContext servletContext = sce.getServletContext();


        DatabaseManager databaseManager = new DatabaseManager();
        servletContext.setAttribute("databaseManager", databaseManager);
        log.info("Grammar manager created!");
        
        databaseManager.getAllGrammar();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ((DatabaseManager) servletContext.getAttribute("databaseManager")).CloseManager();
        log.info("Application ended!");
    }
    
}
