<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">

        <title>>SRGSDB Web Application</title>

        <!-- Bootstrap core CSS -->
        <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="http://getbootstrap.com/examples/starter-template/starter-template.css" rel="stylesheet">

        <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
        <script src="http://getbootstrap.com/assets/js/ie-emulation-modes-warning.js"></script>

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/SRGSDB-Web-App">SRGSDBWeb</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li ><a href="/SRGSDB-Web-App">Home</a></li>
                        <li class="active"><a href="/SRGSDB-Web-App/list">Grammar database</a></li>
                        <li><a href="/SRGSDB-Web-App/add">Add grammar</a></li>
                        <li><a href="https://github.com/fRandOmizer/SRGSDBWeb/wiki/Datab%C3%A1ze-SRGS-gramatik-s-webov%C3%BDm-rozhran%C3%ADm">Project page</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>

        <div class="container">


            
            <c:if test="${not empty error}">
                <br><br>
                <div class="alert alert-danger" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <c:out value="${error}"/>
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <br><br>
                <div class="alert alert-success" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <c:out value="${success}"/>
                </div>
            </c:if>
                
            <br><h1>Find grammars by root element: </h1>
            <form action="${pageContext.request.contextPath}/list/search" method="post">
                <input type="text" name="searchValue" placeholder="root element">
                <input type="submit" class="btn btn-primary active" value="Search!">
            </form>
            <c:if test="${empty search}">
                <br><h1>Grammars: </h1>
            </c:if>
                <c:if test="${not empty search}">
                    <br><h1>Grammars with root element: "${search}"   
                        <form action="${pageContext.request.contextPath}/list/cancelsearch" method="post">
                            <input class="btn btn-danger active" type="submit" value="Cancel search">
                        </form> 
                    </h1>
                </c:if>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Xml file</th>
                        <th>Root element</th>
                        <th>Download xml</th>
                        <th>Add Rule</th>

                    </tr>
                </thead>
                <tbody>
 
                    
                    <c:forEach items="${grammars}" var="grammar">
                        <tr>
                            <td>${grammar.getXml().getName()}</td>
                            <td>${grammar.getRootId()}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/list/download?fileName=${grammar.getXml().getName()}" method="post">
                                    <input class="btn btn-primary active" type="submit" value="Download">
                                </form> 
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/list/addrule?id=${grammar.getXml().getName()}" method="post">
                                    <input type="text" name="rule" placeholder="rule">
                                    <input class="btn btn-primary active" type="submit" value="Update grammar">
                                </form>
                            </td>
                            
                        </tr>
                        
                       
                        
                    </c:forEach>
                </tbody>
            </table>


        </div><!-- /.container -->


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
    </body>
</html>
