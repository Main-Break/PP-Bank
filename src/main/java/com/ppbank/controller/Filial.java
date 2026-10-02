package com.ppbank.controller;

import com.ppbank.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Filial {

    public long criar(long idBanco, String nome, String codigo, String cnpj, String endereco) {
        String sql = "INSERT INTO filial (id_banco, nome, codigo, cnpj, endereco) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setLong(1, idBanco);
            comando.setString(2, nome);
            comando.setString(3, codigo);
            comando.setString(4, cnpj);
            comando.setString(5, endereco);
            comando.executeUpdate();

            try (ResultSet chaveGerada = comando.getGeneratedKeys()) {
                return chaveGerada.next() ? chaveGerada.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar a filial.", e);
        }
    }

}
