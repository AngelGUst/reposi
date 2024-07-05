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
import mx.edu.utez.practica3e.utils.generadoraCodigo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ContraServlet", value = "/recuContra")
public class ContraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String correo = req.getParameter("correo");
        String ruta = "solicitudRecuperacion.jsp";
        if (correo != null && !correo.isEmpty()) {
            try (Connection con = DatabaseConnectionManager.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM usuario WHERE correo = ?");
                ps.setString(1, correo);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    UsuarioDao dao = new UsuarioDao();
                    Usuario u = dao.verificarUsuario(correo);

                    String codigo = generadoraCodigo.generateRandomString(20);
                    dao.insertarCodigo(u.getId(), codigo);
                    String link = "http://localhost:8080/practica3e_war_exploded/recuContra?codigo=" + codigo;
                    GmailSender.sendMail(correo, "Recuperación de contraseña", "Haga clic en el siguiente enlace para cambiar su contraseña: <a href=\"" + link + "\">click aquí</a>");

                    ruta = "recuperacion.jsp";
                    HttpSession sesion = req.getSession();
                    sesion.setAttribute("usuario", u);
                    sesion.setAttribute("mensaje", "Se ha enviado un correo de recuperación a " + correo);
                } else {
                    HttpSession sesion = req.getSession();
                    sesion.setAttribute("mensaje", "El usuario no existe en la BD");
                }
            } catch (Exception e) {
                e.printStackTrace();
                HttpSession sesion = req.getSession();
                sesion.setAttribute("mensaje", "Hubo un error al procesar la solicitud.");
            }
        } else {
            HttpSession sesion = req.getSession();
            sesion.setAttribute("mensaje", "El correo no puede estar vacío.");
        }
        resp.sendRedirect(ruta);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codigo = req.getParameter("codigo");
        if (codigo != null && !codigo.isEmpty()) {
            UsuarioDao dao = new UsuarioDao();
            Usuario u = dao.verificarCodigo(codigo);
            if (u.getId() > 0) {
                HttpSession sesion = req.getSession();
                sesion.setAttribute("usuario", u);
                resp.sendRedirect("recuperacion.jsp?codigo=" + codigo);
            } else {
                HttpSession sesion = req.getSession();
                sesion.setAttribute("mensaje", "El código de recuperación no es válido.");
                resp.sendRedirect("solicitudRecuperacion.jsp");
            }
        } else {
            HttpSession sesion = req.getSession();
            sesion.setAttribute("mensaje", "El código no puede estar vacío.");
            resp.sendRedirect("solicitudRecuperacion.jsp");
        }
    }

}
