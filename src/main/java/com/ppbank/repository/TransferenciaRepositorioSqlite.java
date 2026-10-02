package com.ppbank.repository;

import com.ppbank.database.Database;
import com.ppbank.model.Transferencia;
import com.ppbank.service.RepositorioTransferencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferenciaRepositorioSqlite implements RepositorioTransferencia {

    @Override
    public void registrar(Transferencia transferencia) {
        String sql = "INSERT INTO transferencia (id_conta_origem, id_conta_destino, valor, forma, data_hora) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement comando = Database.getInstancia().getConexao().prepareStatement(sql)) {
            comando.setLong(1, transferencia.getIdContaOrigem());
            comando.setLong(2, transferencia.getIdContaDestino());
            comando.setBigDecimal(3, transferencia.getValor());
            comando.setString(4, transferencia.getForma());
            comando.setString(5, transferencia.getDataHora().toString());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível registrar a transferência.", e);
        }
    }

}
