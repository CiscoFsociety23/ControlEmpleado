package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Empleado;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UsuariosDao {
    
    private String dominio = "https://dev.dedsec.cl";
    
    public List<Empleado> obtenerEmpleados(){
        try {
            
            System.out.println("obtenerEmpleados(): Obteniendo listado de empleados");
            
            String urlApi = this.dominio + "/AsistenciaManager/Empleado/obtenerEmpleados";
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerEmpleados(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformar la respuesta en una lista de objetos Empleado
                ObjectMapper objectMapper = new ObjectMapper();
                List<Empleado> empleados = objectMapper.readValue(response.toString(), new TypeReference<List<Empleado>>() {});

                System.out.println("obtenerEmpleados(): Lista de empleados obtenida con éxito.");
                return empleados;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Empleado obtenerEmpleadoCorreo(String correo){
        try {
            
            System.out.println("obtenerEmpleado(): Obteniendo datos del empleado " + correo);
            
            String urlApi = this.dominio + "/AsistenciaManager/Empleado/obtenerEmpleadoCorreo?correo=" + correo;
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerEmpleado(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformando la respuesta en el objeto Empleado
                ObjectMapper objMap = new ObjectMapper();
                Empleado empleado = objMap.readValue(response.toString(), Empleado.class);
                
                System.out.println("obtenerEmpleado(): Empleado encontrado con exito: " + empleado.getRut());
                return empleado;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Empleado obtenerEmpleadoRut(String rut){
        try {
            
            System.out.println("obtenerEmpleadoRut(): Obteniendo datos del empleado " + rut);
            
            String urlApi = this.dominio + "/AsistenciaManager/Empleado/obtenerEmpleadoRut?rut=" + rut;
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerEmpleadoRut(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformando la respuesta en el objeto Empleado
                ObjectMapper objMap = new ObjectMapper();
                Empleado empleado = objMap.readValue(response.toString(), Empleado.class);
                
                System.out.println("obtenerEmpleadoRut(): Empleado encontrado con exito: " + empleado.getRut());
                return empleado;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Boolean verificarAcceso(String rut){
        try {
            
            System.out.println("verificarCredenciales(): Verificando accesos del usuario " + rut);
            
            URL url = new URL(this.dominio + "/AsistenciaManager/verificarAcceso?rut=" + rut);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("verificarCredenciales(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // La respuesta es un valor booleano en forma de "true" o "false"
                boolean result = Boolean.parseBoolean(response.toString());
                System.out.println("verificarCredenciales(): Credenciales validas " + result);
                
                return result;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}