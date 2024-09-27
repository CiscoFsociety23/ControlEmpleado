
package dao;
//import java.sql.*;
 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ConexionDao {
    
    private Connection conexion;
    
    public Connection getConexion() {
        try {
            // Reemplaza los valores entre comillas simples por tus datos de conexión
            String url = "jdbc:mysql://control-asistencia.dedsec.cl:3306/ResgistroAsistencia";
            String usuario = "control-asistencia";
            String contraseña = "s6QqWfMFUyGRFLjyFP81";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión establecida");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
        }
        return conexion;
    }

    // Datos de conexión
    // private static final String URL = "jdbc:mysql://localhost:3306/DBEMPLEADOS";
    // private static final String USER = "root";
    // private static final String PASSWORD = "luc4samar0";
    
    private static final String URL = "jdbc:mysql://control-asistencia.dedsec.cl:3306/ResgistroAsistencia";
    private static final String USER = "control-asistencia";
    private static final String PASSWORD = "s6QqWfMFUyGRFLjyFP81";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Registrar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conexion != null) {
               System.out.println("Conexión exitosa a la base de datos!");
               
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        }
        return conexion;
    }

    /*public static void main(String[] args) {
        // Prueba la conexión
        Connection conexion = conectar();
        
        // Cierra la conexión
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }*/
}
