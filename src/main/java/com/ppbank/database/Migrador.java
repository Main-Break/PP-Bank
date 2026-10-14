package com.ppbank.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/** Cria as tabelas na inicialização executando o database.sql. */
public class Migrador {

    private static final String ARQUIVO_SCHEMA = "/database.sql";

    private final Connection conexao;

    public Migrador(Connection conexao) {
        this.conexao = conexao;
    }

    public void aplicar() {
        try (Statement comando = conexao.createStatement()) {
            // Remove as linhas de comentário e executa cada comando separado por ";".
            String script = this.lerArquivoSchema().replaceAll("(?m)^--.*$", "");

            for (String instrucao : script.split(";")) {
                String instrucaoLimpa = instrucao.trim();
                if (!instrucaoLimpa.isEmpty()) {
                    comando.execute(instrucaoLimpa);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível criar as tabelas do banco de dados.", e);
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
