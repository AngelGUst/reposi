<%@ page import="mx.edu.utez.practica3e.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- un formulario para insertar el usuario -->
<%
    HttpSession sesion = request.getSession();
    Usuario u = (Usuario) sesion.getAttribute("usuario");
    if (u == null) { // Que estoy registrando %>
<form method="post" action="sign_in">
    <label>Ingrese su nombre de usuario: </label>
    <input type="text" name="nombre_usuario">
    <br>
    <label>Ingrese su contraseña: </label>
    <input type="password" name="pass1">
    <br>
    <label>Confirme su contraseña: </label>
    <input type="password" name="pass2">
    <br>
    <label>Ingrese su correo: </label>
    <input type="email" name="correo">
    <br>
    <label>Ingrese su tipo de usuario: </label>
    <select name="tipo_usuario">
        <option value="1">Admin</option>
        <option value="2">Normal</option>
    </select>
    <br>
    <input type="hidden" name="operacion" value="registro">
    <input type="submit" value="Registrarse" >
</form>
<% }else{ %>
<form method="post" action="sign_in">
    <label>Ingrese su nombre de usuario: </label>
    <input type="text" name="nombre_usuario" value="<%=u.getNombre_usuario()%>">
    <br>
    <label>Ingrese su contraseña: </label>
    <input type="password" name="pass" value="<%=u.getContra()%>">
    <br>
    <label>Ingrese su correo: </label>
    <input type="email" name="correo" value="<%=u.getCorreo()%>">
    <br>
    <label>Ingrese su tipo de usuario: </label>
    <select name="tipo_usuario">
        <% if(u.getTipo_usuario() == 1) { %>
        <option value="1" selected>Admin</option>
        <option value="2">Normal</option>
        <% } else { %>
        <option value="1">Admin</option>
        <option value="2" selected>Normal</option>
        <% } %>
    </select>
    <br>

    <input type="hidden" name="operacion" value="actualizar">
    <input type="hidden" name="id" value="<%=u.getId()%>">
    <input type="submit" value="Actualizar" >
</form>
<% } %>
<%
    sesion.removeAttribute("usuario");
    sesion.removeAttribute("mensaje");
%>
</body>
</html>