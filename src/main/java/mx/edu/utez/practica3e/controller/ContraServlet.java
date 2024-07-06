package mx.edu.utez.practica3e.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.utez.practica3e.dao.UsuarioDao;
import mx.edu.utez.practica3e.model.Usuario;
import mx.edu.utez.practica3e.utils.DatabaseConnectionManager;
import mx.edu.utez.practica3e.utils.GmailSender;
import mx.edu.utez.practica3e.utils.SimpleRandomStringGenerator;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ContraServlet", value = "/recuContra")
public class ContraServlet extends HttpServlet {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("correo");

        Usuario usuario = usuarioDao.getByEmail(email);
        if (usuario == null) {
            request.getSession().setAttribute("mensaje", "Correo no registrado.");
            response.sendRedirect("solicitudRecuperacion.jsp");
            return;
        }

        String codigo = SimpleRandomStringGenerator.generateRandomString(20);

        usuario.setCodigoRecuperacion(codigo);
        usuarioDao.updateCodigoRecuperacion(usuario);

        String enlace = "http://localhost:8080/practica3e_war_exploded/recuContra?codigo=" + codigo;
        String mensaje = "<a href=\"" + enlace + "\">click aquí</a>";
        try {
            new GmailSender().sendMail(email, "Recuperación de Contraseña", mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("mensaje", "Correo enviado.");
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigo = request.getParameter("codigo");

        Usuario usuario = usuarioDao.getByCodigoRecuperacion(codigo);
        if (usuario == null) {
            request.getSession().setAttribute("mensaje", "Código inválido.");
            response.sendRedirect("solicitudRecuperacion.jsp");
            return;
        }

        response.sendRedirect("recuperacion.jsp?codigo=" + codigo);
    }
}
