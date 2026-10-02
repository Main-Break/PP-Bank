package com.ppbank.service;

import com.ppbank.model.HashSenha;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CodificadorSenhaSha256 implements CodificadorSenha {

    @Override
    public HashSenha codificar(String senha) {
        String salt = this.gerarSalt();
        return new HashSenha(this.gerarHash(senha, salt), salt);
    }

    @Override
    public boolean verificar(String senha, HashSenha hash) {
        return this.gerarHash(senha, hash.getSalt()).equals(hash.getHash());
    }

    private String gerarSalt() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private String gerarHash(String senha, String salt) {
        try {
            MessageDigest digestor = MessageDigest.getInstance("SHA-256");
            digestor.update(Base64.getDecoder().decode(salt));
            byte[] hash = digestor.digest(senha.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Não foi possível gerar o hash da senha.", e);
        }
    }

}
