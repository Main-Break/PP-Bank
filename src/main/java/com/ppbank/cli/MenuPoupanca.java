package com.ppbank.cli;

import java.util.Scanner;

public class MenuPoupanca extends MenuBase {

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Poupanca");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Criar Conta");
        System.out.println("[2] - Listar Contas");
        System.out.println("[3] - Desativar");
        System.out.println("[4] - Pesquisar");
        System.out.println("+-----------------------------+");
        System.out.println("[0] - Sair");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.criarConta();
                return this;
            case 2:
                this.listarContas();
                return this;
            case 3:
                this.desativarConta();
                return this;
            case 4:
                this.pesquisarConta();
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void criarConta() {
        this.funcaoNaoImplementada();
    }

    void listarContas() {
        this.funcaoNaoImplementada();
    }

    void desativarConta() {
        this.funcaoNaoImplementada();
    }

    void pesquisarConta() {
        this.funcaoNaoImplementada();
    }

}
