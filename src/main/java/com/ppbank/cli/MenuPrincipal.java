package com.ppbank.cli;

import java.util.Scanner;

/** Tela inicial: leva para as demais telas. */
public class MenuPrincipal extends Menu {

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Menu Principal");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Conta Corrente");
        System.out.println("[2] - Poupança");
        System.out.println("[3] - Cartão");
        System.out.println("[4] - Banco");
        System.out.println("[5] - Movimentações");
        System.out.println("+-----------------------------+");
        System.out.println("[0] - Sair");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                return new MenuContaCorrente();
            case 2:
                return new MenuPoupanca();
            case 3:
                return new MenuCartao();
            case 4:
                return new MenuBanco();
            case 5:
                return new MenuMovimentacao();
            case 0:
                return null;
            default:
                return this.opcaoInvalida(leitor);
        }
    }

}
