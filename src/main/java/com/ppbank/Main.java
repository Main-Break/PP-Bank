package com.ppbank;

import com.ppbank.cli.CLI;
import com.ppbank.database.Database;
import com.ppbank.database.Migrador;

public class Main {

    public static void main(String[] args) {
        new Migrador(Database.getInstancia().getConexao()).aplicar();

        CLI terminalCLI = new CLI();
    }
}
