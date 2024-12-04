/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Empleado;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    
}
