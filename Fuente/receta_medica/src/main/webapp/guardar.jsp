<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : guardar
    Created on : 16/10/2014, 11:52:38 AM
    Author     : JorgeRivera
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    <sql:update var="cons" dataSource="medica">
        INSERT INTO paciente (nombre, apellido, fecha)
        VALUES ({$nom}, {$ap}, {$fecha} )
    </sql:update>
        
        <c:if test="{$cons==True}">
            <p>Guardado exitoso</p>
        </c:if>
            
    </body>
</html>
