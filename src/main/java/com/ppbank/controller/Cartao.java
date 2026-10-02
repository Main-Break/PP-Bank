package com.ppbank.controller;

import com.ppbank.database.Database;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cartao {

    public long criar(String tipo, String numero, String titular, BigDecimal valor) {
        String sql = "INSERT INTO cartao (tipo, numero, titular, valor) VALUES (?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, tipo);
            comando.setString(2, numero);
            comando.setString(3, titular);
            comando.setBigDecimal(4, valor);
            comando.executeUpdate();

            try (ResultSet chaveGerada = comando.getGeneratedKeys()) {
                return chaveGerada.next() ? chaveGerada.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar o cartão.", e);
        }
    }

    public List<com.ppbank.model.Cartao> listarPorTipo(String tipo) {
        String sql = "SELECT id, tipo, numero, titular, valor FROM cartao WHERE tipo = ? ORDER BY numero";
        List<com.ppbank.model.Cartao> cartoes = new ArrayList<>();

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setString(1, tipo);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    cartoes.add(new com.ppbank.model.Cartao(
                            resultado.getLong("id"),
                            resultado.getString("tipo"),
                            resultado.getString("numero"),
                            resultado.getString("titular"),
                            resultado.getBigDecimal("valor")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível listar os cartões.", e);
        }

        return cartoes;
    }

}
