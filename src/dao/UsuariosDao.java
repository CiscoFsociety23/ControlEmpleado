package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Empleado;
import dto.EmpleadoCreationReq;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.swing.JOptionPane;

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
    
    public Empleado crearEmpleado(EmpleadoCreationReq empleado) {
        try {
            System.out.println("crearEmpleado(): Creando nuevo empleado con RUT: " + empleado.getRut());

            // Configurar la URL de la API
            URL url = new URL(this.dominio + "/AsistenciaManager/Empleado/crearEmpleado");

            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo

            // Convertir el objeto `EmpleadoCreationReq` a JSON usando ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String empleadoJson = objectMapper.writeValueAsString(empleado);

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = empleadoJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("crearEmpleado(): Response Code " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // Leer la respuesta del servidor
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Transformando la respuesta en el objeto Empleado
                    ObjectMapper objMap = new ObjectMapper();
                    Empleado empleadoRes = objMap.readValue(response.toString(), Empleado.class);

                    return empleadoRes;
                }
            } else {
                System.out.println("crearEmpleado(): Error al crear empleado. Código de respuesta: " + responseCode);
                JOptionPane.showMessageDialog(null, "No es posible registrar el usuario, verifique que no se encuentre creado (rut, correo)");
                return null;
            }
        } catch (IOException e) {
            System.out.println("crearEmpleado(): Error de conexión: " + e.getMessage());
            return null;
        }
    }
    
    public Empleado actualizarEmpleado(EmpleadoCreationReq empleado) {
        try {
            System.out.println("actualizarEmpleado(): Actualizando empleado con RUT: " + empleado.getRut());

            // Configurar la URL de la API
            URL url = new URL(this.dominio + "/AsistenciaManager/Empleado/actualizarEmpleado");

            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo

            // Convertir el objeto `EmpleadoCreationReq` a JSON usando ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String empleadoJson = objectMapper.writeValueAsString(empleado);

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = empleadoJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("actualizarEmpleado(): Response Code " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del servidor
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Transformando la respuesta en el objeto Empleado
                    ObjectMapper objMap = new ObjectMapper();
                    Empleado empleadoRes = objMap.readValue(response.toString(), Empleado.class);

                    return empleadoRes;
                }
            } else {
                System.out.println("actualizarEmpleado(): Error al crear empleado. Código de respuesta: " + responseCode);
                JOptionPane.showMessageDialog(null, "No es posible registrar el usuario, verifique que no se encuentre creado (rut, correo)");
                return null;
            }
        } catch (IOException e) {
            System.out.println("actualizarEmpleado(): Error de conexión: " + e.getMessage());
            return null;
        }
    }

    
}