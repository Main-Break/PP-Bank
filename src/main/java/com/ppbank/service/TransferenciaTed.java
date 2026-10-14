package com.ppbank.service;

import com.ppbank.model.Conta;

import java.math.BigDecimal;

/** TED: cobra R$ 10,00 de taxa da conta de origem. */
public class TransferenciaTed implements FormaTransferencia {

    private static final BigDecimal TAXA = new BigDecimal("10.00");

    @Override
    public String getNome() {
        return "TED";
    }

    @Override
    public void executar(Conta origem, Conta destino, BigDecimal valor) {
        origem.sacar(valor.add(TAXA));
        destino.depositar(valor);
    }

}
