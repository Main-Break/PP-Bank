package com.ppbank.model;

import java.math.BigDecimal;

/** Conta que não pode ficar negativa e rende juros pela taxaRendimento. */
public class ContaPoupanca extends Conta {

    private final BigDecimal taxaRendimento;

    public ContaPoupanca(long id, String numero, String titular, BigDecimal saldo, BigDecimal taxaRendimento) {
        super(id, numero, titular, saldo);
        this.taxaRendimento = taxaRendimento;
    }

    public BigDecimal getTaxaRendimento() {
        return taxaRendimento;
    }

    /** Deposita saldo × taxa. Usa depositar() para respeitar o contrato de Conta. */
    public void renderJuros() {
        this.depositar(this.consultarSaldo().multiply(this.taxaRendimento));
    }

    @Override
    protected boolean podeSacar(BigDecimal valor) {
        // Saldo após o saque não pode ser negativo.
        return this.consultarSaldo().subtract(valor).compareTo(BigDecimal.ZERO) >= 0;
    }

    @Override
    public String getTipoPersistencia() {
        return "POUPANCA";
    }

    @Override
    public BigDecimal getParametroPersistencia() {
        return this.taxaRendimento;
    }

}
