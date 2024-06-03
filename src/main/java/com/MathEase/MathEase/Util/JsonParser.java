package com.MathEase.MathEase.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    /**
     * Extracts the username value from a JSON string.
     *
     * @param jsonString The JSON string containing a username field.
     * @return The extracted username value, or null if not found or an error occurs.
     */
    public static String extractUsername(String jsonString) {
        try {
            //  Create an ObjectMapper object
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON string into a JsonNode object
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Get the "username" field from the JSON object
            JsonNode usernameNode = jsonNode.get("username");
            if (usernameNode != null && usernameNode.isTextual()) {
                return usernameNode.asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting username from JSON: " + e.getMessage());
        }
        return null;
    }

    // Extracts the password value from a JSON string.
    public static String extractPassword(String jsonString) {
        try {
            // Create an ObjectMapper object
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON string into a JsonNode object
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Get the "password" field from the JSON object
            JsonNode passwordNode = jsonNode.get("password");
            // Check if the "password" field is present and is a string
            if (passwordNode != null && passwordNode.isTextual()) {
                return passwordNode.asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting password from JSON: " + e.getMessage());
        }
        return null;
    }
}
