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

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(jsonString);

            JsonNode usernameNode = jsonNode.get("username");
            if (usernameNode != null && usernameNode.isTextual()) {
                return usernameNode.asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting username from JSON: " + e.getMessage());
        }
        return null;
    }

    public static String extractPassword(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(jsonString);

            JsonNode passwordNode = jsonNode.get("password");
            if (passwordNode != null && passwordNode.isTextual()) {
                return passwordNode.asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting password from JSON: " + e.getMessage());
        }
        return null;
    }
}
