package com.ppbank.service;

import com.ppbank.database.Transacao;
import com.ppbank.model.Conta;
import com.ppbank.model.Transferencia;
import com.ppbank.repository.RepositorioConta;
import com.ppbank.repository.RepositorioTransferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Orquestra uma transferência entre contas.
 * Depende só de interfaces recebidas no construtor (DIP), por isso Pix/TED e
 * WhatsApp/e-mail são trocados sem alterar esta classe (OCP).
 * As gravações rodam numa Transacao: ou tudo é salvo, ou nada.
 */
public class ServicoTransferencia {

    private final RepositorioConta repositorioConta;
    private final RepositorioTransferencia repositorioTransferencia;
    private final FormaTransferencia formaTransferencia;
    private final CanalNotificacao canalNotificacao;
    private final Transacao transacao;

    public ServicoTransferencia(RepositorioConta repositorioConta,
                                 RepositorioTransferencia repositorioTransferencia,
                                 FormaTransferencia formaTransferencia,
                                 CanalNotificacao canalNotificacao,
                                 Transacao transacao) {
        this.repositorioConta = repositorioConta;
        this.repositorioTransferencia = repositorioTransferencia;
        this.formaTransferencia = formaTransferencia;
        this.canalNotificacao = canalNotificacao;
        this.transacao = transacao;
    }

    public Transferencia transferir(long idContaOrigem, long idContaDestino, BigDecimal valor, String contatoNotificacao) {
        // Origem e destino iguais criariam dinheiro do nada.
        if (idContaOrigem == idContaDestino) {
            throw new IllegalArgumentException("A conta de origem e a de destino devem ser diferentes.");
        }

        // 1. Busca as contas.
        Conta origem = this.repositorioConta.buscarPorId(idContaOrigem);
        Conta destino = this.repositorioConta.buscarPorId(idContaDestino);

        // 2. Move o dinheiro conforme a forma escolhida (Pix, TED...).
        this.formaTransferencia.executar(origem, destino, valor);

        Transferencia transferencia = new Transferencia(
                0, origem.getId(), destino.getId(), valor, this.formaTransferencia.getNome(), LocalDateTime.now());

        // 3. Grava saldos e histórico juntos: se algo falhar no meio, nada é salvo.
        this.transacao.iniciar();
        try {
            this.repositorioConta.salvar(origem);
            this.repositorioConta.salvar(destino);
            this.repositorioTransferencia.registrar(transferencia);
            this.transacao.confirmar();
        } catch (RuntimeException e) {
            this.transacao.desfazer();
            throw e;
        }

        // 4. Avisa o cliente (só depois de tudo salvo).
        this.canalNotificacao.notificar(contatoNotificacao,
                "Transferência de R$ " + valor + " via " + this.formaTransferencia.getNome() + " realizada com sucesso.");

        return transferencia;
    }

}
