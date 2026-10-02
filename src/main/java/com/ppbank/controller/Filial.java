package com.ppbank.controller;

import com.ppbank.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public List<com.ppbank.model.Filial> listarPorBanco(long idBanco) {
        String sql = "SELECT id, id_banco, nome, codigo, cnpj, endereco FROM filial WHERE id_banco = ? ORDER BY nome";
        List<com.ppbank.model.Filial> filiais = new ArrayList<>();

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setLong(1, idBanco);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    filiais.add(new com.ppbank.model.Filial(
                            resultado.getLong("id"),
                            resultado.getLong("id_banco"),
                            resultado.getString("nome"),
                            resultado.getString("codigo"),
                            resultado.getString("cnpj"),
                            resultado.getString("endereco")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível listar as filiais.", e);
        }

        return filiais;
    }

}
