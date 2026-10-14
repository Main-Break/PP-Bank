package com.ppbank.cli;

import com.ppbank.model.Conta;
import com.ppbank.model.ContaPoupanca;
import com.ppbank.repository.ContaRepositorioSqlite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Tela de poupanças: criar, listar, desativar e pesquisar. */
public class MenuPoupanca extends Menu {

    private static final String TIPO = "POUPANCA";

    private final ContaRepositorioSqlite repositorioContas = new ContaRepositorioSqlite();

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
        System.out.println("[0] - Voltar");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.criarConta(leitor);
                return this;
            case 2:
                this.listarContas(leitor);
                return this;
            case 3:
                this.desativarConta(leitor);
                return this;
            case 4:
                this.pesquisarConta(leitor);
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void criarConta(Scanner leitor) {
        System.out.print("Informe o número da conta: ");
        String numero = leitor.nextLine();

        System.out.print("Informe o titular: ");
        String titular = leitor.nextLine();

        BigDecimal saldo = this.lerDecimal(leitor, "Informe o saldo inicial: ");

        BigDecimal taxaRendimento = this.lerDecimal(leitor, "Informe a taxa de rendimento (ex: 0.005 para 0,5%): ");

        this.repositorioContas.salvar(new ContaPoupanca(0, numero, titular, saldo, taxaRendimento));

        System.out.println("Conta criada com sucesso.");
        this.pausar(leitor);
    }

    void listarContas(Scanner leitor) {
        String[] colunas = {"id", "numero", "titular", "saldo", "taxa_rendimento"};
        List<String[]> linhas = new ArrayList<>();

        for (Conta conta : this.repositorioContas.listarTodas()) {
            if (TIPO.equals(conta.getTipoPersistencia())) {
                linhas.add(new String[]{
                        String.valueOf(conta.getId()), conta.getNumero(), conta.getTitular(),
                        conta.consultarSaldo().toString(), conta.getParametroPersistencia().toString()
                });
            }
        }

        this.exibirTabela(colunas, linhas);
        this.pausar(leitor);
    }

    void desativarConta(Scanner leitor) {
        long id = this.lerNumero(leitor, "Informe o ID da conta a desativar: ");

        this.repositorioContas.excluir(id);

        System.out.println("Conta desativada com sucesso.");
        this.pausar(leitor);
    }

    void pesquisarConta(Scanner leitor) {
        System.out.print("Informe o número da conta: ");
        String numero = leitor.nextLine();

        String[] colunas = {"id", "numero", "titular", "saldo", "taxa_rendimento"};
        List<String[]> linhas = new ArrayList<>();

        for (Conta conta : this.repositorioContas.listarTodas()) {
            if (TIPO.equals(conta.getTipoPersistencia()) && conta.getNumero().equals(numero)) {
                linhas.add(new String[]{
                        String.valueOf(conta.getId()), conta.getNumero(), conta.getTitular(),
                        conta.consultarSaldo().toString(), conta.getParametroPersistencia().toString()
                });
            }
        }

        this.exibirTabela(colunas, linhas);
        this.pausar(leitor);
    }

}
