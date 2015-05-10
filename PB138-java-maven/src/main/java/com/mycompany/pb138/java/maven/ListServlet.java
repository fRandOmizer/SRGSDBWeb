package com.mycompany.pb138.java.maven;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ListServlet", urlPatterns = {ListServlet.URL_MAPPING + "/*"})
public class ListServlet extends HttpServlet {

    public static final String URL_MAPPING = "/list";
    private static final String LIST_JSP = "/grammarList.jsp";
    private static final Logger log = Logger.getLogger(ListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try 
        {
            request.setAttribute("grammarList", ((GrammarManager) getServletContext().getAttribute("GrammarManager")).getGrammars());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            log.log(Level.SEVERE, "Error, cannot show grammars.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();

        switch (action) {
            case "/add":
                try {
                    String name = request.getParameter("name");
                    List<Rule> rules = null; //TODO

                    Grammar newGrammar = new Grammar(name);
                    for (Rule rule : rules) {
                        newGrammar.addRule(rule);
                    }
                    ((GrammarManager) getServletContext().getAttribute("GrammarManager")).addGrammar(newGrammar);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Cannot add Grammar.", e);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                    return;
                }
            default:
                log.log(Level.WARNING, "Unknown action {0}", action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
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
    }

}
