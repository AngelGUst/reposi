package mx.edu.utez.practica3e.dao;


import mx.edu.utez.practica3e.model.Usuario;
import mx.edu.utez.practica3e.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

// Estas clases DAO permiten el uso de funciones CRUD
public class UsuarioDao {

    //Programar una función R (lectura) para obtener un usuario
    //con el fin de hacer el inicio de sesión
    public Usuario getOne(String nombre_usuario, String contra) {
        Usuario u = new Usuario();
        String query = "select * from usuario where nombre_usuario = ? and contra = sha2(?,256)";

        try {
            //1) conectarnos a la BD
            Connection con = DatabaseConnectionManager.getConnection();
            //2) Configurar el query y ejecutarlo
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre_usuario);
            ps.setString(2, contra);
            ResultSet rs = ps.executeQuery();
            //3) Obtener la información
            if (rs.next()) {
                //Entonces llenamos la información del usuario
                u.setId(rs.getInt("id"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setContra(rs.getString("contra"));
                u.setCorreo(rs.getString("correo"));
                u.setTipo_usuario(rs.getInt("tipo_usuario"));
                u.setEstado(rs.getBoolean("estado"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public boolean insert(Usuario u) {
        boolean flag = false;
        String query = "insert into usuario(,nombre_usuario,contra,correo,tipo_usuario,estado) values(?,sha2(?,256),?,?,?) ";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, u.getNombre_usuario());
            ps.setString(2, u.getContra());
            ps.setString(3, u.getCorreo());
            ps.setInt(4, u.getTipo_usuario());
            ps.setBoolean(5, u.isEstado());
            if (ps.executeUpdate() == 1) {
                flag = true;//Porque significa que si se inserto en la BD
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //Seria la R del CRUD
    public ArrayList<Usuario> getAll() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String query = "select * from usuario";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                //Entonces llenamos la información del usuario
                u.setId(rs.getInt("id"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setContra(rs.getString("contra"));
                u.setCorreo(rs.getString("correo"));
                u.setTipo_usuario(rs.getInt("tipo_usuario"));
                u.setEstado(rs.getBoolean("estado"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public Usuario getOne(int id) {
        Usuario u = new Usuario();
        String query = "select * from usuario where id = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setContra(rs.getString("contra"));
                u.setCorreo(rs.getString("correo"));
                u.setTipo_usuario(rs.getInt("tipo_usuario"));
                u.setEstado(rs.getBoolean("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public boolean update(Usuario u) {
        boolean flag = false;
        String query = "update usuario set nombre_usuario = ?, contra = ?, correo = ?, tipo_usuario = ? where id = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, u.getNombre_usuario());
            ps.setString(2, u.getContra());
            ps.setString(3, u.getCorreo());
            ps.setInt(4, u.getTipo_usuario());
            ps.setInt(5, u.getId());
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean Delete(Usuario u) {
        boolean flag = false;
        String query = "UPDATE usuario SET estado = ? WHERE id = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setBoolean(1, u.isEstado());
            ps.setInt(2, u.getId());
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public Usuario veficicarUsuario(String correo) {
        Usuario u = new Usuario();
        String query = "select * from usuario where correo = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public Usuario inCodigo(String contra, String codigo, int id) {
        Usuario u = new Usuario();
        String query = "update usuario set contra = ?, codigo = ? where id = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, contra);
            ps.setString(2, codigo);
            ps.setInt(3, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setContra(rs.getString("contra"));
                u.setCodigo(rs.getString("codigo"));
                u.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public Usuario verificarUsuario(String correo) {
        Usuario u = new Usuario();
        String query = "SELECT * FROM usuario WHERE correo = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setCorreo(rs.getString("correo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public boolean insertarCodigo(int id, String codigo) {
        boolean flag = false;
        String query = "INSERT INTO codigo_recuperacion (correo, codigo) VALUES (?, ?)";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, codigo);
            if (ps.executeUpdate() == 1) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public Usuario verificarCodigo(String codigo) {
        Usuario u = new Usuario();
        String query = "SELECT u.* FROM usuario u JOIN codigo_recuperacion cr ON u.id = cr.usuario_id WHERE cr.codigo = ?";
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setTipo_usuario(rs.getInt("tipo_usuario"));
                u.setEstado(rs.getBoolean("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
}
