package com.ppbank.repository;

import com.ppbank.database.Database;
import com.ppbank.model.Colaborador;
import com.ppbank.model.HashSenha;
import com.ppbank.service.ConsultaColaboradores;
import com.ppbank.service.RepositorioColaborador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorRepositorioSqlite implements RepositorioColaborador, ConsultaColaboradores {

    @Override
    public void salvar(Colaborador colaborador, HashSenha credencial) {
        String sql = "INSERT INTO colaborador (nome, usuario, senha_hash, senha_salt, cpf, agencia) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setString(1, colaborador.getNome());
            comando.setString(2, colaborador.getUsuario());
            comando.setString(3, credencial.getHash());
            comando.setString(4, credencial.getSalt());
            comando.setString(5, colaborador.getCpf());
            comando.setString(6, colaborador.getAgencia());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar o colaborador.", e);
        }
    }

    @Override
    public Colaborador buscarPorUsuario(String usuario) {
        String sql = "SELECT id, nome, usuario, cpf, agencia FROM colaborador WHERE usuario = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setString(1, usuario);

            try (ResultSet resultado = comando.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }

                return new Colaborador(
                        resultado.getLong("id"),
                        resultado.getString("nome"),
                        resultado.getString("usuario"),
                        resultado.getString("cpf"),
                        resultado.getString("agencia")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível buscar o colaborador.", e);
        }
    }

    @Override
    public List<Colaborador> listarTodos() {
        String sql = "SELECT id, nome, usuario, cpf, agencia FROM colaborador ORDER BY nome";
        List<Colaborador> colaboradores = new ArrayList<>();

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                colaboradores.add(new Colaborador(
                        resultado.getLong("id"),
                        resultado.getString("nome"),
                        resultado.getString("usuario"),
                        resultado.getString("cpf"),
                        resultado.getString("agencia")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível listar os colaboradores.", e);
        }

        return colaboradores;
    }

    @Override
    public HashSenha buscarCredencial(String usuario) {
        String sql = "SELECT senha_hash, senha_salt FROM colaborador WHERE usuario = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setString(1, usuario);

            try (ResultSet resultado = comando.executeQuery()) {
                if (!resultado.next()) {
                    return null;
                }

                return new HashSenha(resultado.getString("senha_hash"), resultado.getString("senha_salt"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível buscar as credenciais do colaborador.", e);
        }
    }

}
