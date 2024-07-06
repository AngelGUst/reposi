<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP - Solicitud de Recuperación</title>
    <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center mt-5">Solicitud de Recuperación</h2>
            <form method="post" action="recuContra" class="mt-4">
                <div class="form-group mb-3">
                    <label for="correo">Ingrese su correo:</label>
                    <input type="email" class="form-control" id="correo" name="correo" required>
                </div>
                <%
                    HttpSession sesion = request.getSession();
                    String mensaje = (String) sesion.getAttribute("mensaje");

                    if(mensaje != null){ %>
                <p class="text-danger"><%=mensaje%></p>
                <% } %>
                <button type="submit" class="btn btn-dark botonesApp btn-block">Solicitar</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
