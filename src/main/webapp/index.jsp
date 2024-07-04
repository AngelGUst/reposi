<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link href="${pageContext.request.contextPath}/CSS/bootstrap.css" >
    <link href="${pageContext.request.contextPath}/CSS/datatables.css" >
</head>
<body>
<form method="post" action="login">
    <label>Ingrese nombre de usuario: </label>
    <input type="text" name="nombre_usuario">
    <br>
    <label>Ingrese contraseña: </label>
    <input type="password" name="contra">
    <br>
    <%
        HttpSession sesion = request.getSession();
        String mensaje = (String) sesion.getAttribute("mensaje");

        if(mensaje!=null){ %>
    <p style="color: red;"><%=mensaje%></p>
    <% } %>
    <input type="submit" value="Iniciar sesión">
</form>
<a href="registrarUsuario.jsp">Registrarme</a>
<a href="gestionUsuario.jsp">Ver Usuarios</a>
<a href="solicitudRecuperacion.jsp">Actualizar Contra</a>
</body>
</html>