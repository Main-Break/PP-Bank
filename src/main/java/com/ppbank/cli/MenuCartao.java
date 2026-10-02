package com.ppbank.cli;

import com.ppbank.controller.Cartao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCartao extends MenuBase {

    private final Cartao cartao = new Cartao();

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
        System.out.println("[0] - Voltar");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.gerenciarCartao(leitor, "CREDITO", "Limite");
                return this;
            case 2:
                this.gerenciarCartao(leitor, "DEBITO", "Saldo");
                return this;
            case 3:
                this.gerenciarCartao(leitor, "VIRTUAL", "Saldo");
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void gerenciarCartao(Scanner leitor, String tipo, String rotuloValor) {
        System.out.println("[1] - Criar");
        System.out.println("[2] - Listar");
        System.out.print("--> ");

        int opcao = leitor.hasNextInt() ? leitor.nextInt() : -1;
        leitor.nextLine();

        if (opcao == 1) {
            this.criarCartao(leitor, tipo, rotuloValor);
        } else if (opcao == 2) {
            this.listarCartoes(leitor, tipo, rotuloValor);
        } else {
            this.opcaoInvalida(leitor);
        }
    }

    void criarCartao(Scanner leitor, String tipo, String rotuloValor) {
        System.out.print("Informe o número do cartão: ");
        String numero = leitor.nextLine();

        System.out.print("Informe o titular: ");
        String titular = leitor.nextLine();

        System.out.print("Informe o " + rotuloValor.toLowerCase() + ": ");
        BigDecimal valor = new BigDecimal(leitor.nextLine());

        this.cartao.criar(tipo, numero, titular, valor);

        System.out.println("Cartão criado com sucesso.");
        this.pausar(leitor);
    }

    void listarCartoes(Scanner leitor, String tipo, String rotuloValor) {
        String[] colunas = {"id", "tipo", "numero", "titular", rotuloValor.toLowerCase()};
        List<String[]> linhas = new ArrayList<>();

        for (var item : this.cartao.listarPorTipo(tipo)) {
            linhas.add(new String[]{
                    String.valueOf(item.getId()), item.getTipo(), item.getNumero(), item.getTitular(),
                    item.getValor().toString()
            });
        }

        this.exibirTabela(colunas, linhas);
        this.pausar(leitor);
    }

}
