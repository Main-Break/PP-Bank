package com.ppbank.controller;

import com.ppbank.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco {

    public long criar(String nome, String codigo, String cnpj, String endereco) {
        String sql = "INSERT INTO banco (nome, codigo, cnpj, endereco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, nome);
            comando.setString(2, codigo);
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

}
