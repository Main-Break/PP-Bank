package com.ppbank.model;

public class HashSenha {

    private final String hash;
    private final String salt;

    public HashSenha(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

}
