package mx.edu.utez.practica3e.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.practica3e.dao.UsuarioDao;
import mx.edu.utez.practica3e.model.Usuario;

import java.io.IOException;

@WebServlet(name="eliminarServlet", value ="/eliminar")
public class eliminarServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        boolean estado = Boolean.parseBoolean(req.getParameter("estado"));

        Usuario u = new Usuario();
        u.setId(id);
        u.setEstado(!estado); // se cambia el estado actual (true ó false)

        UsuarioDao dao = new UsuarioDao();
        if(dao.Delete(u)){
            // Si se hizo el update de estado se recarga la pagina
            resp.sendRedirect("gestionUsuario.jsp");
        } else {
            // No se cambio el estado marca error
            req.getSession().setAttribute("mensaje", "No se pudo cambiar el estado");
            resp.sendRedirect("gestionUsuario.jsp");
        }
    }
}