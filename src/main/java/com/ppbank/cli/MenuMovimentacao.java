package com.ppbank.cli;

import com.ppbank.exception.ContaNaoEncontradaException;
import com.ppbank.exception.SaldoInsuficienteException;
import com.ppbank.model.Conta;
import com.ppbank.repository.ContaRepositorioSqlite;
import com.ppbank.repository.TransferenciaRepositorioSqlite;
import com.ppbank.service.CanalNotificacao;
import com.ppbank.service.FormaTransferencia;
import com.ppbank.service.NotificacaoEmail;
import com.ppbank.service.NotificacaoWhatsapp;
import com.ppbank.service.ServicoTransferencia;
import com.ppbank.service.TransferenciaPix;
import com.ppbank.service.TransferenciaTed;

import java.math.BigDecimal;
import java.util.Scanner;

/** Tela de movimentações: depósito e transferência entre contas. */
public class MenuMovimentacao extends Menu {

    private final ContaRepositorioSqlite repositorioContas = new ContaRepositorioSqlite();
    private final TransferenciaRepositorioSqlite repositorioTransferencias = new TransferenciaRepositorioSqlite();

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Movimentações");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Depositar");
        System.out.println("[2] - Transferir");
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
            case 2:
                this.transferir(leitor);
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

    void transferir(Scanner leitor) {
        long idOrigem = this.lerNumero(leitor, "Informe o ID da conta de origem: ");
        long idDestino = this.lerNumero(leitor, "Informe o ID da conta de destino: ");
        BigDecimal valor = this.lerDecimal(leitor, "Informe o valor: ");
        FormaTransferencia forma = this.escolherForma(leitor);
        CanalNotificacao canal = this.escolherCanal(leitor);

        System.out.print("Informe o contato para o aviso (telefone ou e-mail): ");
        String contato = leitor.nextLine();

        // Injeção manual: a forma e o canal escolhidos são entregues ao serviço (Strategy).
        ServicoTransferencia servico = new ServicoTransferencia(
                this.repositorioContas, this.repositorioTransferencias, forma, canal);

        try {
            servico.transferir(idOrigem, idDestino, valor, contato);
        } catch (ContaNaoEncontradaException | SaldoInsuficienteException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        this.pausar(leitor);
    }

    private FormaTransferencia escolherForma(Scanner leitor) {
        while (true) {
            long opcao = this.lerNumero(leitor, "Forma: [1] Pix (sem taxa) [2] TED (taxa de R$ 10,00): ");

            if (opcao == 1) {
                return new TransferenciaPix();
            }
            if (opcao == 2) {
                return new TransferenciaTed();
            }

            System.out.println("Opção inválida.");
        }
    }

    private CanalNotificacao escolherCanal(Scanner leitor) {
        while (true) {
            long opcao = this.lerNumero(leitor, "Aviso: [1] WhatsApp [2] E-mail: ");

            if (opcao == 1) {
                return new NotificacaoWhatsapp();
            }
            if (opcao == 2) {
                return new NotificacaoEmail();
            }

            System.out.println("Opção inválida.");
        }
    }

}
