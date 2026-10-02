package com.ppbank.repository;

import com.ppbank.database.Database;
import com.ppbank.model.Conta;
import com.ppbank.model.ContaCorrente;
import com.ppbank.model.ContaPoupanca;
import com.ppbank.service.RepositorioConta;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.function.Function;

public class ContaRepositorioSqlite implements RepositorioConta {

    private record DadosConta(long id, String numero, String titular, BigDecimal saldo, BigDecimal parametro) {
    }

    /**
     * Reconstrói o tipo concreto de Conta a partir da coluna "tipo" (persistência),
     * sem instanceof/switch: nova conta = só registrar uma nova fábrica aqui.
     */
    private static final Map<String, Function<DadosConta, Conta>> FABRICAS = Map.of(
            "CORRENTE", dados -> new ContaCorrente(dados.id(), dados.numero(), dados.titular(), dados.saldo(), dados.parametro()),
            "POUPANCA", dados -> new ContaPoupanca(dados.id(), dados.numero(), dados.titular(), dados.saldo(), dados.parametro())
    );

    @Override
    public Conta buscarPorId(long id) {
        String sql = "SELECT id, tipo, numero, titular, saldo, parametro FROM conta WHERE id = ?";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next() ? this.mapear(resultado) : null;
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

    private Conta mapear(ResultSet resultado) throws SQLException {
        DadosConta dados = new DadosConta(
                resultado.getLong("id"),
                resultado.getString("numero"),
                resultado.getString("titular"),
                resultado.getBigDecimal("saldo"),
                resultado.getBigDecimal("parametro")
        );

        return FABRICAS.get(resultado.getString("tipo")).apply(dados);
    }

}
