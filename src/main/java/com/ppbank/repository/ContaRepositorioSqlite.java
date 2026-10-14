package com.ppbank.repository;

import com.ppbank.database.Database;
import com.ppbank.exception.ContaNaoEncontradaException;
import com.ppbank.model.Conta;
import com.ppbank.model.ContaCorrente;
import com.ppbank.model.ContaPoupanca;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContaRepositorioSqlite implements RepositorioConta {

    @Override
    public Conta buscarPorId(long id) {
        String sql = "SELECT id, tipo, numero, titular, saldo, parametro FROM conta WHERE id = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (!resultado.next()) {
                    throw new ContaNaoEncontradaException(id);
                }

                return this.mapear(resultado);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível buscar a conta.", e);
        }
    }

    @Override
    public long salvar(Conta conta) {
        if (conta.getId() == 0) {
            return this.inserir(conta);
        }

        this.atualizar(conta);
        return conta.getId();
    }

    private long inserir(Conta conta) {
        String sql = "INSERT INTO conta (tipo, numero, titular, saldo, parametro) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, conta.getTipoPersistencia());
            comando.setString(2, conta.getNumero());
            comando.setString(3, conta.getTitular());
            comando.setBigDecimal(4, conta.consultarSaldo());
            comando.setBigDecimal(5, conta.getParametroPersistencia());
            comando.executeUpdate();

            try (ResultSet chaveGerada = comando.getGeneratedKeys()) {
                return chaveGerada.next() ? chaveGerada.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar a conta.", e);
        }
    }

    private void atualizar(Conta conta) {
        String sql = "UPDATE conta SET saldo = ? WHERE id = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setBigDecimal(1, conta.consultarSaldo());
            comando.setLong(2, conta.getId());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível atualizar a conta.", e);
        }
    }

    public List<Conta> listarTodas() {
        String sql = "SELECT id, tipo, numero, titular, saldo, parametro FROM conta ORDER BY numero";
        List<Conta> contas = new ArrayList<>();

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                contas.add(this.mapear(resultado));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível listar as contas.", e);
        }

        return contas;
    }

    public void excluir(long id) {
        String sql = "DELETE FROM conta WHERE id = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setLong(1, id);
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível excluir a conta.", e);
        }
    }

    private Conta mapear(ResultSet resultado) throws SQLException {
        long id = resultado.getLong("id");
        String numero = resultado.getString("numero");
        String titular = resultado.getString("titular");
        BigDecimal saldo = resultado.getBigDecimal("saldo");
        BigDecimal parametro = resultado.getBigDecimal("parametro");

        if (resultado.getString("tipo").equals("POUPANCA")) {
            return new ContaPoupanca(id, numero, titular, saldo, parametro);
        }

        return new ContaCorrente(id, numero, titular, saldo, parametro);
    }

}
