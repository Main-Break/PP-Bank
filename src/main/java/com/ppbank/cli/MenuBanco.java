package com.ppbank.cli;

import com.ppbank.controller.Banco;

import java.util.Scanner;

public class MenuBanco extends MenuBase {

    private final Scanner leitor_menu = new Scanner(System.in);

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
        Banco banco = new Banco();

        System.out.print("Informe o nome do banco: ");
        String nome = this.leitor_menu.nextLine();

        System.out.print("Informe o codigo da agencia: ");
        String codigo = this.leitor_menu.nextLine();

        System.out.print("Informe o CNPJ da agenda: ");
        String cnpj = this.leitor_menu.nextLine();

        System.out.print("Informe o endereco: ");
        String endereco = this.leitor_menu.nextLine();

        banco.criar(
                nome=nome,
                codigo=codigo,
                cnpj=cnpj,
                endereco=endereco
        );

    }

    void colaboradores() {
        this.funcaoNaoImplementada();
    }

    void editarInformacoesBanco() {
        this.funcaoNaoImplementada();
    }

}
