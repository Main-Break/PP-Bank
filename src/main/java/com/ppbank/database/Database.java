package com.ppbank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton: garante uma única conexão com o banco SQLite durante toda a execução da aplicação.
 * Única responsabilidade desta classe é prover essa conexão (SRP) - quem precisa de acesso ao
 * banco depende apenas do tipo java.sql.Connection, não de detalhes de como ela foi aberta (DIP).
 */
public class Database {

    private static final String URL = "jdbc:sqlite:ppbank.db";

    private static Database instancia;

    private final Connection conexao;

    private Database() {
        try {
            this.conexao = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível conectar ao banco de dados.", e);
        }
    }

    public static synchronized Database getInstancia() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }

    public Connection getConexao() {
        return conexao;
    }

}
