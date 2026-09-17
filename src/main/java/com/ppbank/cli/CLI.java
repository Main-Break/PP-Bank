package com.ppbank.cli;

import java.util.Scanner;

public class CLI {

    private final Scanner leitor = new Scanner(System.in);
    private Menu menuAtual = new MenuPrincipal();

    public CLI() {

        while (menuAtual != null) {
            menuAtual.exibir();

            int opcao = lerOpcao();

            menuAtual = menuAtual.processarOpcao(opcao, leitor);
        }

        leitor.close();
    }

    private int lerOpcao() {
        System.out.print("--> ");

        if (!leitor.hasNextInt()) {
            leitor.nextLine();
            return -1;
        }

        int opcao = leitor.nextInt();
        leitor.nextLine();

        return opcao;
    }

}
