package com.ppbank.cli;

import java.util.Scanner;

public class MenuBanco extends MenuBase {

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Configurações do Banco");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Criar Filial");
        System.out.println("[2] - Colaboradores");
        System.out.println("[3] - Editar Informações do Banco");
        System.out.println("+-----------------------------+");
        System.out.println("[0] - Sair");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.criarFilial();
                return this;
            case 2:
                this.colaboradores();
                return this;
            case 3:
                this.editarInformacoesBanco();
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void criarFilial() {
        this.funcaoNaoImplementada();
    }

    void colaboradores() {
        this.funcaoNaoImplementada();
    }

    void editarInformacoesBanco() {
        this.funcaoNaoImplementada();
    }

}
