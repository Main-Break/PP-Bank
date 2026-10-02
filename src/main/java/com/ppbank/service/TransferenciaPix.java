package com.ppbank.service;

import com.ppbank.model.Conta;

import java.math.BigDecimal;

public class TransferenciaPix implements FormaTransferencia {

    @Override
    public String getNome() {
        return "Pix";
    }

    @Override
    public void executar(Conta origem, Conta destino, BigDecimal valor) {
        origem.sacar(valor);
        destino.depositar(valor);
    }

}
