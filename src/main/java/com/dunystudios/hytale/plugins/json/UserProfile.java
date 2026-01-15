package com.dunystudios.hytale.plugins.json;

public class UserProfile {
    public String uuid;
    public float balance;

    public UserProfile() {
        this.uuid = "";
        this.balance = 0.0f;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
