package com.ppbank.model;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {

    private final BigDecimal limiteChequeEspecial;

    public ContaCorrente(long id, String numero, String titular, BigDecimal saldo, BigDecimal limiteChequeEspecial) {
        super(id, numero, titular, saldo);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public BigDecimal getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    @Override
    protected boolean podeSacar(BigDecimal valor) {
        return this.consultarSaldo().subtract(valor).compareTo(this.limiteChequeEspecial.negate()) >= 0;
    }

    @Override
    public String getTipoPersistencia() {
        return "CORRENTE";
    }

    @Override
    public BigDecimal getParametroPersistencia() {
        return this.limiteChequeEspecial;
    }

}
