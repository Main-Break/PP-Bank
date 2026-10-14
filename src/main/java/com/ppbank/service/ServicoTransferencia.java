package com.ppbank.service;

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
 */
public class ServicoTransferencia {

    private final RepositorioConta repositorioConta;
    private final RepositorioTransferencia repositorioTransferencia;
    private final FormaTransferencia formaTransferencia;
    private final CanalNotificacao canalNotificacao;

    public ServicoTransferencia(RepositorioConta repositorioConta,
                                 RepositorioTransferencia repositorioTransferencia,
                                 FormaTransferencia formaTransferencia,
                                 CanalNotificacao canalNotificacao) {
        this.repositorioConta = repositorioConta;
        this.repositorioTransferencia = repositorioTransferencia;
        this.formaTransferencia = formaTransferencia;
        this.canalNotificacao = canalNotificacao;
    }

    public Transferencia transferir(long idContaOrigem, long idContaDestino, BigDecimal valor, String contatoNotificacao) {
        // 1. Busca as contas.
        Conta origem = this.repositorioConta.buscarPorId(idContaOrigem);
        Conta destino = this.repositorioConta.buscarPorId(idContaDestino);

        // 2. Move o dinheiro conforme a forma escolhida (Pix, TED...).
        this.formaTransferencia.executar(origem, destino, valor);

        // 3. Grava os novos saldos.
        this.repositorioConta.salvar(origem);
        this.repositorioConta.salvar(destino);

        // 4. Registra no histórico.
        Transferencia transferencia = new Transferencia(
                0, origem.getId(), destino.getId(), valor, this.formaTransferencia.getNome(), LocalDateTime.now());

        this.repositorioTransferencia.registrar(transferencia);

        // 5. Avisa o cliente pelo canal escolhido.
        this.canalNotificacao.notificar(contatoNotificacao,
                "Transferência de R$ " + valor + " via " + this.formaTransferencia.getNome() + " realizada com sucesso.");

        return transferencia;
    }

}
