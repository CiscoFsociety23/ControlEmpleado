
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Usuarios;

 
public class UsuariosDao {
    Connection con;
    ConexionDao cn = new ConexionDao();
    PreparedStatement ps;
    ResultSet rs;
    
    /*public Usuarios login(String user,String pass){
      Usuarios us = new Usuarios();
      String sql ="select * from usuarios where usuario ='"+user+"'and password'"+pass+" '";
      try{
      con=cn.conectar();
      ps=con.prepareStatement(sql);
      rs=ps.executeQuery();
      while (rs.next()){
        us.setIdUser(rs.getInt(1));
        us.setNombre(rs.getString(2));
        us.setUsuario(rs.getString(3));
        us.setPassword(rs.getString(4));
          
         }
      }catch(Exception e){
       JOptionPane.showMessageDialog(null,e);
      }
      return us;
    }*/
    
    public Usuarios login(String user, String pass) {
        Usuarios us = new Usuarios();
        String sql = "SELECT * FROM Empleado WHERE Nombre = ? AND Contrasena = ?";
        try {
            con = ConexionDao.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                us.setIdUser(rs.getInt("IdEmpleado"));
                us.setNombre(rs.getString("Nombre"));
                us.setCorreo(rs.getString("Correo"));
                us.setPassword(rs.getString("Contrasena"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar sesión: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return us;
    }

    
}

/*
///////////////codigo  mas seguro////////////
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import modelo.Usuarios;

public class UsuariosDao {
    Connection con;
    ConexionDao cn = new ConexionDao();
    PreparedStatement ps;
    ResultSet rs;

    // Método para login
    public Usuarios login(String user, String pass) {
        Usuarios us = new Usuarios();
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);  // Setea el primer parámetro (usuario)
            ps.setString(2, pass);  // Setea el segundo parámetro (contraseña)
            rs = ps.executeQuery();
            if (rs.next()) {
                us.setIdUser(rs.getInt("id"));  // Ajusta según el nombre real de la columna en la base de datos
                us.setNombre(rs.getString("nombre"));
                us.setUsuario(rs.getString("usuario"));
                us.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar sesión: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return us;
    }
}



*/
