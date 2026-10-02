package com.ppbank.cli;

import com.ppbank.controller.Banco;
import com.ppbank.controller.Filial;

import java.util.Scanner;

public class MenuBanco extends MenuBase {

    private final Banco banco = new Banco();
    private final Filial filial = new Filial();

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
                this.criarFilial(leitor);
                return this;
            case 2:
                return new MenuColaborador();
            case 3:
                this.editarInformacoesBanco(leitor);
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void criarFilial(Scanner leitor) {
        var bancoExistente = this.banco.buscarUnico();

        if (bancoExistente == null) {
            System.out.println("Configure as informações do banco antes de criar uma filial.");
            this.pausar(leitor);
            return;
        }

        System.out.print("Informe o nome da filial: ");
        String nome = leitor.nextLine();

        System.out.print("Informe o código da filial: ");
        String codigo = leitor.nextLine();

        System.out.print("Informe o CNPJ da filial: ");
        String cnpj = leitor.nextLine();

        System.out.print("Informe o endereço da filial: ");
        String endereco = leitor.nextLine();

        this.filial.criar(bancoExistente.getId(), nome, codigo, cnpj, endereco);

        System.out.println("Filial criada com sucesso.");
        this.pausar(leitor);
    }

    void editarInformacoesBanco(Scanner leitor) {
        var bancoExistente = this.banco.buscarUnico();

        System.out.print("Informe o nome do banco: ");
        String nome = leitor.nextLine();

        System.out.print("Informe o código do banco: ");
        String codigo = leitor.nextLine();

        System.out.print("Informe o CNPJ do banco: ");
        String cnpj = leitor.nextLine();

        System.out.print("Informe o endereço do banco: ");
        String endereco = leitor.nextLine();

        if (bancoExistente == null) {
            this.banco.criar(nome, codigo, cnpj, endereco);
        } else {
            this.banco.atualizar(bancoExistente.getId(), nome, codigo, cnpj, endereco);
        }

        System.out.println("Informações do banco salvas com sucesso.");
        this.pausar(leitor);
    }

}
