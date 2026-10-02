package com.ppbank.service;

import com.ppbank.model.Conta;
import com.ppbank.model.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        Conta origem = this.repositorioConta.buscarPorId(idContaOrigem);
        Conta destino = this.repositorioConta.buscarPorId(idContaDestino);

        this.formaTransferencia.executar(origem, destino, valor);

        this.repositorioConta.salvar(origem);
        this.repositorioConta.salvar(destino);

        Transferencia transferencia = new Transferencia(
                0, origem.getId(), destino.getId(), valor, this.formaTransferencia.getNome(), LocalDateTime.now());

        this.repositorioTransferencia.registrar(transferencia);

        this.canalNotificacao.notificar(contatoNotificacao,
                "Transferência de R$ " + valor + " via " + this.formaTransferencia.getNome() + " realizada com sucesso.");

        return transferencia;
    }

}
