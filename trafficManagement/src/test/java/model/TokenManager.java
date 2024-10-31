package model;

public class TokenManager {
    private static String token;

    // Armazena o token obtido no login
    public static void setToken(String authToken) {
        token = authToken;
    }

    // Recupera o token armazenado
    public static String getToken() {
        return token;
    }
}
