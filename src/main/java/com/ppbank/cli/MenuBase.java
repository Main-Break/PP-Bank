package com.ppbank.cli;

import java.util.Scanner;

public abstract class MenuBase implements Menu {

    protected void limparTela() {
        for (int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
    }

    protected Menu opcaoInvalida(Scanner leitor) {
        System.out.println("Opção inválida!");
        System.out.print("Pressione ENTER para continuar...");
        leitor.nextLine();
        return this;
    }

    protected void funcaoNaoImplementada() {
        System.out.println("Função ainda não implementada.");
    }

}
