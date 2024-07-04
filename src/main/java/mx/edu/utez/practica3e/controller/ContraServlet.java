package mx.edu.utez.practica3e.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.utez.practica3e.dao.UsuarioDao;
import mx.edu.utez.practica3e.model.Usuario;

import java.io.IOException;

@WebServlet(name = "ContraServlet", value = "/solicitud")
public class ContraServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1) Obtener la información del formulario
        String correo = req.getParameter("correo");
        String ruta = "index.jsp";

        //2) conectarme a la base de datos y buscar al usuario segun
        // las credenciales del form
        UsuarioDao dao = new UsuarioDao();
        Usuario u = dao.veficicarUsuario(correo);

        if(u.getCorreo() == null){
            //No existe el usuario en la base de datos
            HttpSession sesion = req.getSession();
            sesion.setAttribute("mensaje","El usuario no existe en la BD");
            resp.sendRedirect(ruta);
        }else{
            //Si existe el usuario
            ruta="recuperacion.jsp";
            HttpSession sesion = req.getSession();
            sesion.setAttribute("usuario",u);
            resp.sendRedirect(ruta);
        }


    }
}