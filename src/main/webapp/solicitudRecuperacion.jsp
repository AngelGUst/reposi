
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="solicitud">
    <label>Ingrese su correo: </label>
    <input type="email" name="correo" value="correo">
    <br>
    <input type="hidden" name="operacion" value="actualizar">
    <input type="hidden" name="id" value="codigo">
    <input type="submit" value="Solicitar" >
</form>
</body>
</html>
