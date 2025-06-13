package com.vault.user;

public class User {
    private int id; // database ID
    private String username;
    private String vaultPath;

    public User(int id, String username, String vaultPath) {
        this.id = id;
        this.username = username;
        this.vaultPath = vaultPath;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getVaultPath() {
        return vaultPath;
    }

    public String toString() {
        return "User[id = " + id + ", username = " + username + ", vaultPath = " + vaultPath + "]";
    }
}
