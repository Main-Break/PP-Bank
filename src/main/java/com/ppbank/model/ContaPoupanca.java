package com.ppbank.model;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

    private final BigDecimal taxaRendimento;

    public ContaPoupanca(long id, String numero, String titular, BigDecimal saldo, BigDecimal taxaRendimento) {
        super(id, numero, titular, saldo);
        this.taxaRendimento = taxaRendimento;
    }

    public BigDecimal getTaxaRendimento() {
        return taxaRendimento;
    }

    public void renderJuros() {
        this.depositar(this.consultarSaldo().multiply(this.taxaRendimento));
    }

    @Override
    protected boolean podeSacar(BigDecimal valor) {
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
