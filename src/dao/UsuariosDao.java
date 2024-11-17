package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UsuariosDao {
    
    public Boolean verificarCredenciales(String correo, String contrasena){
        try {
            
            URL url = new URL("https://dedsec.cl/AsistenciaManager/verificarAcceso");
            
            // Crear la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // Necesario para enviar datos en el cuerpo
            
            // JSON a enviar
            String jsonInputString = String.format("{\"correo\": \"%s\", \"contrasena\": \"%s\"}", correo, contrasena);

            // Escribir los datos en el cuerpo de la solicitud
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Obtener la respuesta
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Leer la respuesta del servidor
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // La respuesta es un valor booleano en forma de "true" o "false"
                boolean result = Boolean.parseBoolean(response.toString());
                System.out.println("Respuesta del servidor: " + result);
                
                return result;
            }
        } catch(IOException e) {
            System.out.println("Ha ocurrido un error al verificar credenciales: " + e.getMessage());
            return false;
        }
    }
    
}