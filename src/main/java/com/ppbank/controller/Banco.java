package com.ppbank.controller;

import com.ppbank.database.Database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class Banco {

    public long criar(String nome, String codigo, String cnpj, String endereco) {
        String sql = "INSERT INTO banco (nome, codigo, cnpj, endereco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, nome);
            comando.setString(2, codigo); // Código da Agência
            comando.setString(3, cnpj);
            comando.setString(4, endereco);
            comando.executeUpdate();

            try (ResultSet chaveGerada = comando.getGeneratedKeys()) {
                return chaveGerada.next() ? chaveGerada.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar o banco.", e);
        }
    }

    public long criarColaborador(String nome, String usuario, String senha, String cpf, String agencia) {
        String salt = this.gerarSalt();
        String hash = this.gerarHash(senha, salt);

        String sql = "INSERT INTO colaborador (nome, usuario, senha_hash, senha_salt, cpf, agencia) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, nome);
            comando.setString(2, usuario);
            comando.setString(3, hash);
            comando.setString(4, salt);
            comando.setString(5, cpf);
            comando.setString(6, agencia);
            comando.executeUpdate();

            try (ResultSet chaveGerada = comando.getGeneratedKeys()) {
                return chaveGerada.next() ? chaveGerada.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar o colaborador.", e);
        }
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
