package com.mycompany.pb138.java.maven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tomas
 */
//@WebServlet(urlPatterns = {"/ListServlet"})
@WebServlet(name = "ListServlet", urlPatterns = {ListServlet.URL_MAPPING + "/*"})
public class ListServlet extends HttpServlet {

    public static final String URL_MAPPING = "/list";
    private static final String LIST_JSP = "/list.jsp";
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            List<Grammar> grammarList = new ArrayList<Grammar>();

            
            Grammar testGrammar = new Grammar("gramatika c1");
            testGrammar.addRule(new Rule("left side"));
            
            Grammar testGrammar2 = new Grammar("gramatika c2");
            testGrammar2.addRule(new Rule("left side"));
            
            Grammar testGrammar3 = new Grammar("gramatika c3");
            testGrammar3.addRule(new Rule("left side"));
            
            
            grammarList.add(testGrammar);
            grammarList.add(testGrammar2);
            grammarList.add(testGrammar3);
            

            request.setAttribute("grammarList", grammarList);

            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            //log.log(Level.SEVERE, "Error cannot show cellars", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
