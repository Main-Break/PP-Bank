package com.ppbank.cli;

import com.ppbank.exception.ContaNaoEncontradaException;
import com.ppbank.model.Conta;
import com.ppbank.repository.ContaRepositorioSqlite;

import java.math.BigDecimal;
import java.util.Scanner;

/** Tela de movimentações: depósito e transferência entre contas. */
public class MenuMovimentacao extends Menu {

    private final ContaRepositorioSqlite repositorioContas = new ContaRepositorioSqlite();

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Movimentações");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Depositar");
        System.out.println("+-----------------------------+");
        System.out.println("[0] - Voltar");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.depositar(leitor);
                return this;
            case 0:
                return new MenuPrincipal();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void depositar(Scanner leitor) {
        long id = this.lerNumero(leitor, "Informe o ID da conta: ");
        BigDecimal valor = this.lerDecimal(leitor, "Informe o valor do depósito: ");

        try {
            Conta conta = this.repositorioContas.buscarPorId(id);
            conta.depositar(valor);
            this.repositorioContas.salvar(conta);

            System.out.println("Depósito realizado. Novo saldo: R$ " + conta.consultarSaldo());
        } catch (ContaNaoEncontradaException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        this.pausar(leitor);
    }

}
