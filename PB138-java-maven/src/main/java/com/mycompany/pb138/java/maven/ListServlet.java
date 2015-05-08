package com.mycompany.pb138.java.maven;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ListServlet", urlPatterns = {ListServlet.URL_MAPPING + "/*"})
public class ListServlet extends HttpServlet {

    public static final String URL_MAPPING = "/list";
    private static final String LIST_JSP = "/grammarList.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            GrammarManager gm = new GrammarManager("ourXMLDatabse.xml");
            gm.loadGrammarsFromXML();
            
            request.setAttribute("grammarList", gm.getGrammars());
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
    }

}
