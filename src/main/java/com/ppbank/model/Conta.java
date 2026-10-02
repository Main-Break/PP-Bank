package com.ppbank.model;

import com.ppbank.exception.SaldoInsuficienteException;

import java.math.BigDecimal;

/**
 * sacar() e depositar() são final: nenhuma subclasse pode quebrar o contrato.
 * A única extensão permitida é o critério de saque, via podeSacar() (Template Method).
 */
public abstract class Conta {

    private final long id;
    private final String numero;
    private final String titular;
    private BigDecimal saldo;

    protected Conta(long id, String numero, String titular, BigDecimal saldo) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }

    public final void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public final void sacar(BigDecimal valor) {
        if (!this.podeSacar(valor)) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente para sacar " + valor + " da conta " + this.numero + ".");
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public final BigDecimal consultarSaldo() {
        return this.saldo;
    }

    public long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    protected abstract boolean podeSacar(BigDecimal valor);

    public abstract String getTipoPersistencia();

    public abstract BigDecimal getParametroPersistencia();

}
