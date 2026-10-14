package com.ppbank.service;

import com.ppbank.model.Conta;

import java.math.BigDecimal;

/** Strategy: como o dinheiro sai e entra. Novo tipo (ex: Boleto) = nova classe, sem mexer no serviço (OCP). */
public interface FormaTransferencia {

    String getNome();

    void executar(Conta origem, Conta destino, BigDecimal valor);

}
