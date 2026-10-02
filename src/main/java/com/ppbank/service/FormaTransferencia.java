package com.ppbank.service;

import com.ppbank.model.Conta;

import java.math.BigDecimal;

public interface FormaTransferencia {

    String getNome();

    void executar(Conta origem, Conta destino, BigDecimal valor);

}
