package com.ppbank.controller;

import com.ppbank.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public com.ppbank.model.Banco buscarUnico() {
        String sql = "SELECT id, nome, codigo, cnpj, endereco FROM banco ORDER BY id LIMIT 1";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            return resultado.next() ? this.mapear(resultado) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível buscar o banco.", e);
        }
    }

    public List<com.ppbank.model.Banco> listarTodos() {
        String sql = "SELECT id, nome, codigo, cnpj, endereco FROM banco ORDER BY nome";
        List<com.ppbank.model.Banco> bancos = new ArrayList<>();

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                bancos.add(this.mapear(resultado));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível listar os bancos.", e);
        }

        return bancos;
    }

    public void atualizar(long id, String nome, String codigo, String cnpj, String endereco) {
        String sql = "UPDATE banco SET nome = ?, codigo = ?, cnpj = ?, endereco = ? WHERE id = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setString(1, nome);
            comando.setString(2, codigo);
            comando.setString(3, cnpj);
            comando.setString(4, endereco);
            comando.setLong(5, id);
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível atualizar o banco.", e);
        }
    }

    private com.ppbank.model.Banco mapear(ResultSet resultado) throws SQLException {
        return new com.ppbank.model.Banco(
                resultado.getLong("id"),
                resultado.getString("nome"),
                resultado.getString("codigo"),
                resultado.getString("cnpj"),
                resultado.getString("endereco")
        );
    }

}
