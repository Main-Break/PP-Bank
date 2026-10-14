package com.ppbank.cli;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    public abstract void exibir();

    public abstract Menu processarOpcao(int opcao, Scanner leitor);

    protected void limparTela() {
        for (int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
    }

    protected Menu opcaoInvalida(Scanner leitor) {
        System.out.println("Opção inválida!");
        this.pausar(leitor);
        return this;
    }

    protected void pausar(Scanner leitor) {
        System.out.print("Pressione ENTER para continuar...");
        leitor.nextLine();
    }

    protected BigDecimal lerDecimal(Scanner leitor, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            try {
                return new BigDecimal(leitor.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número, ex: 150.00");
            }
        }
    }

    protected long lerNumero(Scanner leitor, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            try {
                return Long.parseLong(leitor.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    /**
     * Exibe linhas em formato de tabela, no mesmo estilo do resultado
     * de um SELECT no cliente de linha de comando do MySQL/MariaDB.
     */
    protected void exibirTabela(String[] colunas, List<String[]> linhas) {
        int[] larguras = new int[colunas.length];

        for (int i = 0; i < colunas.length; i++) {
            larguras[i] = colunas[i].length();
        }

        for (String[] linha : linhas) {
            for (int i = 0; i < colunas.length; i++) {
                larguras[i] = Math.max(larguras[i], linha[i].length());
            }
        }

        String separador = this.montarSeparadorTabela(larguras);

        System.out.println(separador);
        System.out.println(this.montarLinhaTabela(colunas, larguras));
        System.out.println(separador);

        for (String[] linha : linhas) {
            System.out.println(this.montarLinhaTabela(linha, larguras));
        }

        System.out.println(separador);
        System.out.println(linhas.size() + (linhas.size() == 1 ? " linha" : " linhas") + " na tabela.");
    }

    private String montarSeparadorTabela(int[] larguras) {
        StringBuilder construtor = new StringBuilder("+");

        for (int largura : larguras) {
            construtor.append("-".repeat(largura + 2)).append("+");
        }

        return construtor.toString();
    }

    private String montarLinhaTabela(String[] valores, int[] larguras) {
        StringBuilder construtor = new StringBuilder("|");

        for (int i = 0; i < valores.length; i++) {
            construtor.append(" ").append(String.format("%-" + larguras[i] + "s", valores[i])).append(" |");
        }

        return construtor.toString();
    }

}
