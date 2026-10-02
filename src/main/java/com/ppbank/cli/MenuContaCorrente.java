package com.ppbank.cli;

import com.ppbank.model.Conta;
import com.ppbank.model.ContaCorrente;
import com.ppbank.repository.ContaRepositorioSqlite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuContaCorrente extends MenuBase {

    private static final String TIPO = "CORRENTE";

    private final ContaRepositorioSqlite repositorioContas = new ContaRepositorioSqlite();

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Conta-Corrente");
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

        System.out.print("Informe o saldo inicial: ");
        BigDecimal saldo = new BigDecimal(leitor.nextLine());

        System.out.print("Informe o limite do cheque especial: ");
        BigDecimal limite = new BigDecimal(leitor.nextLine());

        this.repositorioContas.salvar(new ContaCorrente(0, numero, titular, saldo, limite));

        System.out.println("Conta criada com sucesso.");
        this.pausar(leitor);
    }

    void listarContas(Scanner leitor) {
        String[] colunas = {"id", "numero", "titular", "saldo", "limite_cheque_especial"};
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
        System.out.print("Informe o ID da conta a desativar: ");
        long id = Long.parseLong(leitor.nextLine());

        this.repositorioContas.excluir(id);

        System.out.println("Conta desativada com sucesso.");
        this.pausar(leitor);
    }

    void pesquisarConta(Scanner leitor) {
        System.out.print("Informe o número da conta: ");
        String numero = leitor.nextLine();

        String[] colunas = {"id", "numero", "titular", "saldo", "limite_cheque_especial"};
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
