
package modelo;


public class Usuarios {
    int idUser;
    String nombre;
    String correo;
    String password;

    public Usuarios() {
    }

    public Usuarios(int idUser, String nombre, String correo, String password) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
