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
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "UpdateContraServlet", value = "/updateContra")
public class UpdateContraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nuevaContra = req.getParameter("nuevaContra");
        String codigo = req.getParameter("codigo");
        HttpSession sesion = req.getSession();

        if (nuevaContra != null && !nuevaContra.isEmpty() && codigo != null && !codigo.isEmpty()) {
            UsuarioDao dao = new UsuarioDao();
            Usuario u = dao.verificarCodigo(codigo);

            if (u != null && u.getId() > 0) {
                try (Connection con = DatabaseConnectionManager.getConnection()) {
                    PreparedStatement ps = con.prepareStatement("UPDATE usuario SET contraseña = ?, codigo_recuperacion = NULL WHERE id = ?");
                    ps.setString(1, nuevaContra);
                    ps.setInt(2, u.getId());
                    int rowsUpdated = ps.executeUpdate();

                    if (rowsUpdated > 0) {
                        sesion.setAttribute("mensaje", "La contraseña se ha actualizado correctamente.");

                        // Opcional: Enviar un correo de confirmación
                        GmailSender gmailSender = new GmailSender();
                        gmailSender.sendMail(u.getCorreo(), "Contraseña Actualizada", "Tu contraseña ha sido actualizada exitosamente.");

                        resp.sendRedirect("index.jsp");
                    } else {
                        sesion.setAttribute("mensaje", "Hubo un problema al actualizar la contraseña. Por favor, inténtalo de nuevo.");
                        resp.sendRedirect("recuperacion.jsp?codigo=" + codigo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sesion.setAttribute("mensaje", "Hubo un error al procesar la solicitud.");
                    resp.sendRedirect("recuperacion.jsp?codigo=" + codigo);
                }
            } else {
                sesion.setAttribute("mensaje", "El código de recuperación no es válido.");
                resp.sendRedirect("solicitudRecuperacion.jsp");
            }
        } else {
            sesion.setAttribute("mensaje", "Todos los campos son obligatorios.");
            resp.sendRedirect("recuperacion.jsp?codigo=" + codigo);
        }
    }
}