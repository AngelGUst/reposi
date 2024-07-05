<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Solicitud de Recuperación de Contraseña</title>
</head>
<body>
<h2>Solicitud de Recuperación de Contraseña</h2>
<%
    String mensaje = (String) session.getAttribute("mensaje");
    if (mensaje != null) {
%>
<p style="color:red;"><%= mensaje %></p>
<%
        session.removeAttribute("mensaje");
    }
%>
<form action="recuContra" method="post">
    <label for="correo">Email:</label>
    <input type="email" id="correo" name="correo" required><br><br>
    <button type="submit">Solicitar</button>
</form>
</body>
</html>
