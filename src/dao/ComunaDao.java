/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Comuna;
import dto.Empleado;
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
public class ComunaDao {
    
    private String dominio = "http://dedsec.cl:9043";
    
    public List<Comuna> obtenerComunas(){
        try {
            
            System.out.println("registarMarcaje(): Obteniendo registro de comunas ");
            
            URL url = new URL(this.dominio + "/AsistenciaManager/Comuna/obtenerComunas");
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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

                ObjectMapper objectMapper = new ObjectMapper();
                List<Comuna> comunas = objectMapper.readValue(response.toString(), new TypeReference<List<Comuna>>() {});
                
                System.out.println("obtenerComunas(): Comunas obtenidas con exito");
                return comunas;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
