package com.mycompany.pb138.java.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListServlet", urlPatterns = {ListServlet.URL_MAPPING + "/*"})

/** Shows list of grammars and allows user to filter grammar by root rule
 * or add rule do already existing grammar.
 *
 * @requestParameter grammars List<XmlList> containing required grammars
 * @requestParameter success String which indicates successful operation
 * @requestParameter error String which indicates not successful operation
 * @requestParameter id String - unique grammar id - name
 * @requestParameter rule String - rule to add to a grammar
 * @requestParameter searchValue String - root rule for searching
 * @requestParameter rule String - rule to add to a grammar
 * @requestParameter fileName String - name of the file to download
 *
 * @jsp /grammarList.jsp
*/
public class ListServlet extends HttpServlet {

    public static final String URL_MAPPING = "/list";
    private static final String LIST_JSP = "/grammarList.jsp";
    private static final Logger log = Logger.getLogger(ListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {                        
            showGrammarList(request, response);
        } catch (Exception e) {  //ServletException | IOException e
            log.log(Level.SEVERE, "Error, cannot show grammars.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String action = request.getPathInfo();
        switch (action) {
            case "/addrule":
                try {
                    String id = request.getParameter("id");
                    String rule = request.getParameter("rule");
                    System.out.println("Adding "+id+rule);
                    getDatabaseManager().addRuleToGrammar(id, rule);
                    request.setAttribute("success", "Rule(s) added.");
                    showGrammarList(request, response);
                    return;
                } catch (AddRuleException |IllegalArgumentException  e) {
                    request.setAttribute("error", e.getMessage());
                    showGrammarList(request, response);
                    return;
                }
            case "/cancelsearch":
                showGrammarList(request, response);
                return;
            case "/search":
                String searchValue = request.getParameter("searchValue");
                if (searchValue.length() == 0) {
                    request.setAttribute("error", "Root element cant be empty!");
                    showGrammarList(request, response);
                    return;
                }
                request.setAttribute("grammars", getDatabaseManager().findByRoot(searchValue) );
                request.setAttribute("search", searchValue);
                request.getRequestDispatcher(LIST_JSP).forward(request, response);
                return;
            
            case "/download": 
                try {
                    String fileName = request.getParameter("fileName");
                    File file = getDatabaseManager().getXmlFileByName(fileName);

                    OutputStream out = response.getOutputStream();
                    FileInputStream in = new FileInputStream(file);
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    in.close();
                    out.flush();
                } catch (Exception e) {
                request.setAttribute("error", e);
                request.getRequestDispatcher(LIST_JSP).forward(request, response);
                return;
                 }
                return;
            default:
                log.log(Level.WARNING, "Unknown action {0}", action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }
    
    protected void showGrammarList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("grammars", getDatabaseManager().getAllGrammar());
            request.getRequestDispatcher(LIST_JSP).forward(request, response);
        } catch (ServletException | IOException e) {
            log.log(Level.SEVERE, "Error cannot show cellars", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
     /**
     * Returns databaseManager
     *
     * @return DatabaseManager databaseManager
     */
    private DatabaseManager getDatabaseManager() {
        return (DatabaseManager) getServletContext().getAttribute("databaseManager");
    }



}
