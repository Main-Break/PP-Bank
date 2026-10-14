package com.ppbank.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transacao sobre a conexão única do SQLite.
 * Se o programa cair antes do confirmar(), o SQLite descarta tudo ao reabrir o banco.
 */
public class TransacaoSqlite implements Transacao {

    private final Connection conexao = Database.getInstancia().getConexao();

    // true só para quem abriu a transação: é quem decide confirmar ou desfazer.
    private boolean abertaAqui;

    @Override
    public void iniciar() {
        try {
            // Já existe transação aberta (ex: demonstração do Main): só participa dela.
            this.abertaAqui = this.conexao.getAutoCommit();

            if (this.abertaAqui) {
                this.conexao.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível iniciar a transação.", e);
        }
    }

    @Override
    public void confirmar() {
        this.finalizar(true);
    }

    @Override
    public void desfazer() {
        this.finalizar(false);
    }

    private void finalizar(boolean confirmar) {
        if (!this.abertaAqui) {
            return;
        }

        try {
            if (confirmar) {
                this.conexao.commit();
            } else {
                this.conexao.rollback();
            }

            this.conexao.setAutoCommit(true);
            this.abertaAqui = false;
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível finalizar a transação.", e);
        }
    }

}
