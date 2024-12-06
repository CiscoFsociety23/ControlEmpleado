/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Turno;
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
public class TurnoDao {
    
    private String dominio = "http://dedsec.cl:9043";
    
    public List<Turno> obtenerTurnos(){
        try {
            
            System.out.println("obtenerTurnos(): Obteniendo registro de turnos");
            
            URL url = new URL(this.dominio + "/AsistenciaManager/Turno/obtenerTurnos");
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("obtenerTurnos(): Response Code " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                List<Turno> turnos = objectMapper.readValue(response.toString(), new TypeReference<List<Turno>>() {});
                
                System.out.println("obtenerTurnos(): Turnos obtenidos con exito");
                return turnos;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}
