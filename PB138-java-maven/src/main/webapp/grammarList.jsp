<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
    Document   : list
    Created on : May 2, 2015, 10:04:00 PM
    Author     : Tomas
--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>List of Grammars:</h1>
        

        <c:forEach items="${grammarList}" var="grammar">
            <div>
                ${grammar.getName()}
                <c:forEach items="${grammar.getRules()}" var="rule">
                    <div>
                        ${rule.getNonterminal()}
                        -->
                        <c:forEach items="${rule.getTerminals()}" var="terminal">
                             ${terminal}
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>

    
    </body>
</html>
