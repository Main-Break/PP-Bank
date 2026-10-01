package com.ppbank.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Migrador {

    private static final String ARQUIVO_SCHEMA = "/database.sql";
    private static final int VERSAO_ATUAL = 1;

    private final Connection conexao;

    public Migrador(Connection conexao) {
        this.conexao = conexao;
    }

    public void aplicar() {
        try {
            this.criarTabelaDeVersao();

            int versaoInstalada = this.obterVersaoInstalada();

            if (versaoInstalada < VERSAO_ATUAL) {
                this.aplicarSchema();
                this.atualizarVersaoInstalada(versaoInstalada);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível aplicar as migrações do banco de dados.", e);
        }
    }

    private void criarTabelaDeVersao() throws SQLException {
        try (Statement comando = conexao.createStatement()) {
            comando.execute("CREATE TABLE IF NOT EXISTS schema_version (versao INTEGER NOT NULL)");
        }
    }

    private int obterVersaoInstalada() throws SQLException {
        try (Statement comando = conexao.createStatement();
             ResultSet resultado = comando.executeQuery("SELECT versao FROM schema_version LIMIT 1")) {
            return resultado.next() ? resultado.getInt("versao") : 0;
        }
    }

    private void aplicarSchema() throws SQLException {
        String script = this.lerArquivoSchema();

        try (Statement comando = conexao.createStatement()) {
            for (String instrucao : script.split(";")) {
                String instrucaoLimpa = instrucao.trim();
                if (!instrucaoLimpa.isEmpty()) {
                    comando.execute(instrucaoLimpa);
                }
            }
        }
    }

    private void atualizarVersaoInstalada(int versaoAnterior) throws SQLException {
        String sql = versaoAnterior == 0
                ? "INSERT INTO schema_version (versao) VALUES (?)"
                : "UPDATE schema_version SET versao = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, VERSAO_ATUAL);
            comando.executeUpdate();
        }
    }

    private String lerArquivoSchema() {
        try (InputStream entrada = Migrador.class.getResourceAsStream(ARQUIVO_SCHEMA)) {
            if (entrada == null) {
                throw new IllegalStateException("Arquivo " + ARQUIVO_SCHEMA + " não encontrado no classpath.");
            }
            return new String(entrada.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de schema.", e);
        }
    }

}
