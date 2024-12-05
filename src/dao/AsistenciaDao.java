/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Atrasos;
import dto.Ausencia;
import dto.Empleado;
import dto.SalidasAnticipadas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

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
            
            System.out.println("obtenerInasistencias(): Obteniendo listado de salidas anticipadas");
            
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

                System.out.println("obtenerInasistencias(): Lista de salidas anticipadas obtenida con éxito.");
                return ausencias;
            }            
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
