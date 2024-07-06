<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recuperacion.JSP</title>
    <link href="${pageContext.request.contextPath}/CSS/bootstrap.css" >
    <link href="${pageContext.request.contextPath}/CSS/datatables.css" >
</head>
<body>
<form method="post" action="updateContra">
    <h1>recuperacion.jsp</h1>
    <label>Nueva Contraseña: </label>
    <input type="password" name="contraseña">
    <br>
    <input type="hidden" name="codigo" value="<%= request.getParameter("codigo") %>">
    <br>
    <input type="submit" value="Cambiar">
</form>
</body>
</html>