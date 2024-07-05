<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cambio de Contraseña</title>
</head>
<body>
<%
    String mensaje = (String) session.getAttribute("mensaje");
    if (mensaje != null) {
%>
<p style="color:red;"><%= mensaje %></p>
<%
        session.removeAttribute("mensaje");
    }
    String codigo = request.getParameter("codigo");
%>
<form action="updateContra" method="post">
    <label for="nuevaContra">Nueva Contraseña:</label>
    <input type="password" id="nuevaContra" name="nuevaContra" required><br><br>
    <input type="hidden" name="codigo" value="<%= codigo %>">
    <button type="submit">Cambiar</button>
</form>
</body>
</html>