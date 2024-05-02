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
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON string into JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Retrieve the value associated with "username" key
            JsonNode usernameNode = jsonNode.get("username");
            if (usernameNode != null && usernameNode.isTextual()) {
                return usernameNode.asText();
            }
        } catch (Exception e) {
            // Handle JSON parsing or other exceptions
            System.err.println("Error extracting username from JSON: " + e.getMessage());
        }
        return null; // Return null if username not found or error occurred
    }

    public static String extractPassword(String jsonString) {
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON string into JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Retrieve the value associated with "password" key
            JsonNode passwordNode = jsonNode.get("password");
            if (passwordNode != null && passwordNode.isTextual()) {
                return passwordNode.asText();
            }
        } catch (Exception e) {
            // Handle JSON parsing or other exceptions
            System.err.println("Error extracting password from JSON: " + e.getMessage());
        }
        return null; // Return null if password not found or error occurred
    }
}
