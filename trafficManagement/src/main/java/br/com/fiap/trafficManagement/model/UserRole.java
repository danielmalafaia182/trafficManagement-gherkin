package br.com.fiap.trafficManagement.model;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    SYSTEM("system");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
