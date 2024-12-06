/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Asistencia;
import dto.Atrasos;
import dto.Ausencia;
import dto.CorreccionAsistencia;
import dto.CorreccionAsistenciaReq;
import dto.Empleado;
import dto.SalidasAnticipadas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author sysadmin
 */
public class AsistenciaDao {
    
    private String dominio = "https://dev.dedsec.cl";
    
    public Boolean registarMarcaje(Empleado empleado, String tipoMarca){
        try {
            
            System.out.println("registarMarcaje(): Registrando marca del empleado " + empleado.getNombre());
            
            URL url = new URL(this.dominio + "/AsistenciaManager/registarMarca?rut=" + empleado.getRut() + "&tipoMarca=" + tipoMarca);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("registarMarcaje(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // La respuesta es un valor booleano en forma de "true" o "false"
                boolean result = Boolean.parseBoolean(response.toString());
                System.out.println("registarMarcaje(): Estado de la marca " + result);
                
                return result;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public List<Atrasos> obtenerAtrasos(){
        try {
            
            System.out.println("obtenerAtrasos(): Obteniendo listado de atrasos");
            
            String urlApi = this.dominio + "/AsistenciaManager/obtenerAtrasos";
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerAtrasos(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformar la respuesta en una lista de objetos Empleado
                ObjectMapper objectMapper = new ObjectMapper();
                List<Atrasos> atrasos = objectMapper.readValue(response.toString(), new TypeReference<List<Atrasos>>() {});

                System.out.println("obtenerAtrasos(): Lista de atrasos obtenida con éxito.");
                return atrasos;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<SalidasAnticipadas> obtenerSalidasAnticipadas(){
        try {
            
            System.out.println("obtenerSalidasAnticipadas(): Obteniendo listado de salidas anticipadas");
            
            String urlApi = this.dominio + "/AsistenciaManager/obtenerSalidasAnticipadas";
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerSalidasAnticipadas(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformar la respuesta en una lista de objetos Empleado
                ObjectMapper objectMapper = new ObjectMapper();
                List<SalidasAnticipadas> salidas = objectMapper.readValue(response.toString(), new TypeReference<List<SalidasAnticipadas>>() {});

                System.out.println("obtenerSalidasAnticipadas(): Lista de salidas anticipadas obtenida con éxito.");
                return salidas;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Ausencia> obtenerInasistencias(String fecha){
        try {
            
            System.out.println("obtenerInasistencias(): Obteniendo listado de inasistencias");
            
            String urlApi = this.dominio + "/AsistenciaManager/empleadosAusentes?fecha=" + fecha;
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerInasistencias(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformar la respuesta en una lista de objetos Empleado
                ObjectMapper objectMapper = new ObjectMapper();
                List<Ausencia> ausencias = objectMapper.readValue(response.toString(), new TypeReference<List<Ausencia>>() {});

                System.out.println("obtenerInasistencias(): Lista de inasistencias obtenida con éxito.");
                return ausencias;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Asistencia> obtenerAsistencias(){
        try {
            
            System.out.println("obtenerAsistencias(): Obteniendo listado de sasistencia");
            
            String urlApi = this.dominio + "/AsistenciaManager/obtenerAsistencias";
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerAsistencias(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformar la respuesta en una lista de objetos Empleado
                ObjectMapper objectMapper = new ObjectMapper();
                List<Asistencia> asistencias = objectMapper.readValue(response.toString(), new TypeReference<List<Asistencia>>() {});

                System.out.println("obtenerAsistencias(): Lista de asistencia obtenida con éxito.");
                return asistencias;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Boolean realizarCorreccion(CorreccionAsistenciaReq correccion){
        try {
            System.out.println("realizarCorreccion(): Realizando la correcion de asistencia: " + correccion.getIdAsistencia());

            // Configurar la URL de la API
            URL url = new URL(this.dominio + "/AsistenciaManager/corregirMarca");

            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo

            // Convertir el objeto `CorreccionAsistenciaReq` a JSON usando ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            String correccionJson = objectMapper.writeValueAsString(correccion);

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = correccionJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("realizarCorreccion(): Response Code " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del servidor
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // La respuesta es un valor booleano en forma de "true" o "false"
                    boolean result = Boolean.parseBoolean(response.toString());
                    System.out.println("realizarCorreccion(): Correccion realizada? " + result);

                    return result;
                }
            } else {
                System.out.println("realizarCorreccion(): Error al corregir la asistencia. Código de respuesta: " + responseCode);
                JOptionPane.showMessageDialog(null, "No es posible corregir la asistencia, verifique que esten los datos correctos");
                return false;
            }
        } catch (IOException e) {
            System.out.println("realizarCorreccion(): Error de conexión: " + e.getMessage());
            return false;
        }
    }
    
    public List<CorreccionAsistencia> obtenerCorrecciones(){
        try {
            
            System.out.println("obtenerCorrecciones(): Obteniendo listado de correcciones");
            
            String urlApi = this.dominio + "/AsistenciaManager/obtenerCorrecciones";
            URL url = new URL(urlApi);
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerCorrecciones(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Transformar la respuesta en una lista de objetos Empleado
                ObjectMapper objectMapper = new ObjectMapper();
                List<CorreccionAsistencia> correcciones = objectMapper.readValue(response.toString(), new TypeReference<List<CorreccionAsistencia>>() {});

                System.out.println("obtenerCorrecciones(): Lista de correcciones obtenida con éxito.");
                return correcciones;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
