package mx.edu.utez.practica3e.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.practica3e.dao.UsuarioDao;
import mx.edu.utez.practica3e.model.Usuario;

import java.io.IOException;

@WebServlet(name="RegistrarUsuarioServlet", value = "/sign_in")
public class RegistrarUsuarioServlet extends HttpServlet {

    //1) Primero configurar la clase para que sea servlet
    //2) Manejar el método doPost para obtener la información del formulario de registro de persona
    //3) utilizar el dao con la función insert para insertar una persona en la BD
    //4) una vez insertada la persona redirigir el usuario hacia el index.jsp


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String operacion = req.getParameter("operacion");
        if(operacion.equals("registrar")) {
            //Registro de usuario
        } else {
            //Actualizar usuario
            String nombre_usuario = req.getParameter("nombre_usuario");
            String pass = req.getParameter("pass");
            String correo = req.getParameter("correo");
            int tipo_usuario = Integer.parseInt(req.getParameter("tipo_usuario"));
            int id = Integer.parseInt(req.getParameter("id"));

            Usuario u = new Usuario();
            u.setNombre_usuario(nombre_usuario);
            u.setContra(pass);
            u.setCorreo(correo);
            u.setTipo_usuario(tipo_usuario);
            u.setId(id);

            UsuarioDao dao = new UsuarioDao();
            if(dao.update(u)){
                //Si se hizo el update
                resp.sendRedirect("gestionUsuario.jsp");
            }else{
                //pues no y mando un error
                req.getSession().setAttribute("mensaje","No se pudo actualizar");
                resp.sendRedirect("index.jsp");
            }
        }

    }
}