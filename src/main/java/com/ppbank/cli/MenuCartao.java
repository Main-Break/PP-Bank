package com.ppbank.cli;

import java.util.Scanner;

public class MenuCartao extends MenuBase {

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Cartão");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Cartão de Crédito");
        System.out.println("[2] - Cartão de Débito");
        System.out.println("[3] - Cartão Virtual");
        System.out.println("+-----------------------------+");
        System.out.println("[0] - Sair");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.cartaoCredito();
                return this;
            case 2:
                this.cartaoDebito();
                return this;
            case 3:
                this.cartaoVirtual();
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void cartaoCredito() {
        this.funcaoNaoImplementada();
    }

    void cartaoDebito() {
        this.funcaoNaoImplementada();
    }

    void cartaoVirtual() {
        this.funcaoNaoImplementada();
    }

}
