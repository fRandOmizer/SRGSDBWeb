package com.mycompany.pb138.java.maven;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "AddGrammarServlet", urlPatterns = {AddGrammarServlet.URL_MAPPING + "/*"})

/** Allows users to upload new grammar.
 *
 * @requestParameter file to upload
 * @requestParameter success String which indicates successful operation
 * @requestParameter error String which indicates not successful operation
 * @jsp /addGrammar.jsp
*/
public class AddGrammarServlet extends HttpServlet {

    public static final String URL_MAPPING = "/add";
    private static final String LIST_JSP = "/addGrammar.jsp";
    private static final Logger log = Logger.getLogger(AddGrammarServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            showPage(request, response);
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
            case "/uploadgrammar":
                try {
                    Part filePart = request.getPart("file");
                    String fileName = filePart.getSubmittedFileName();
                    InputStream fileContent = filePart.getInputStream(); //input stream uploadnuteho suboru
                    
                    SRGSValidator validator = new SRGSValidator();
                    if(!validator.validate(fileContent)){
                    
                        request.setAttribute("error", "Grammar from file \"" + fileName + "\" is not valid:"+validator.getSyntaxError());
                        showPage(request, response);
                        return;
                    }
                    InputStream fileContent2 = filePart.getInputStream();
                    getDatabaseManager().addGrammar(fileContent2, fileName);

                    request.setAttribute("success", "Grammar from file \"" + fileName + "\" succesfully uploaded.");
                    showPage(request, response);

                } catch (IOException e) {
                    request.setAttribute("error", "Error while uploading file: " + e);
                    showPage(request, response);
                    return;
                }

                break;
            default:
                log.log(Level.WARNING, "Unknown action {0}", action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    protected void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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
