package com.ppbank;

import com.ppbank.cli.CLI;
import com.ppbank.database.Database;
import com.ppbank.database.Migrador;
import com.ppbank.model.ContaCorrente;
import com.ppbank.model.ContaPoupanca;
import com.ppbank.repository.ContaRepositorioSqlite;
import com.ppbank.repository.TransferenciaRepositorioSqlite;
import com.ppbank.service.NotificacaoEmail;
import com.ppbank.service.NotificacaoWhatsapp;
import com.ppbank.service.RepositorioConta;
import com.ppbank.service.RepositorioTransferencia;
import com.ppbank.service.ServicoTransferencia;
import com.ppbank.service.TransferenciaPix;
import com.ppbank.service.TransferenciaTed;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        new Migrador(Database.getInstancia().getConexao()).aplicar();

        executarCenariosDeTransferencia();

        new CLI();
    }

    /**
     * Demonstra o fluxo principal com duas combinações distintas de forma de
     * transferência e canal de notificação, montadas por injeção manual de
     * dependência: nenhuma delas exige alterar ServicoTransferencia (OCP).
     */
    private static void executarCenariosDeTransferencia() {
        RepositorioConta repositorioConta = new ContaRepositorioSqlite();
        RepositorioTransferencia repositorioTransferencia = new TransferenciaRepositorioSqlite();

        System.out.println("=== Cenário 1: Transferência via Pix + notificação por WhatsApp ===");

        ContaCorrente contaOrigemPix = new ContaCorrente(0, "0001-1", "Ana Souza", new BigDecimal("1000.00"), new BigDecimal("200.00"));
        ContaPoupanca contaDestinoPix = new ContaPoupanca(0, "0002-2", "Bruno Lima", new BigDecimal("300.00"), new BigDecimal("0.005"));

        long idOrigemPix = repositorioConta.salvar(contaOrigemPix);
        long idDestinoPix = repositorioConta.salvar(contaDestinoPix);

        ServicoTransferencia servicoPix = new ServicoTransferencia(
                repositorioConta, repositorioTransferencia, new TransferenciaPix(), new NotificacaoWhatsapp());

        servicoPix.transferir(idOrigemPix, idDestinoPix, new BigDecimal("150.00"), "+55 99 99999-9999");

        System.out.println();
        System.out.println("=== Cenário 2: Transferência via TED + notificação por e-mail ===");

        ContaCorrente contaOrigemTed = new ContaCorrente(0, "0003-3", "Carla Mendes", new BigDecimal("5000.00"), BigDecimal.ZERO);
        ContaCorrente contaDestinoTed = new ContaCorrente(0, "0004-4", "Diego Alves", new BigDecimal("800.00"), BigDecimal.ZERO);

        long idOrigemTed = repositorioConta.salvar(contaOrigemTed);
        long idDestinoTed = repositorioConta.salvar(contaDestinoTed);

        ServicoTransferencia servicoTed = new ServicoTransferencia(
                repositorioConta, repositorioTransferencia, new TransferenciaTed(), new NotificacaoEmail());

        servicoTed.transferir(idOrigemTed, idDestinoTed, new BigDecimal("500.00"), "cliente@ppbank.com");

        System.out.println();
    }

}
