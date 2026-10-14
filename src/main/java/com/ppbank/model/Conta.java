package com.ppbank.model;

import com.ppbank.exception.SaldoInsuficienteException;

import java.math.BigDecimal;

/**
 * Base de toda conta bancária.
 * Template Method: sacar() e depositar() são final; cada subclasse só define podeSacar().
 * LSP: qualquer subclasse pode ser usada onde se espera uma Conta.
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
        this.validarValor(valor);
        this.saldo = this.saldo.add(valor);
    }

    public final void sacar(BigDecimal valor) {
        this.validarValor(valor);

        if (!this.podeSacar(valor)) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente para sacar " + valor + " da conta " + this.numero + ".");
        }

        this.saldo = this.saldo.subtract(valor);
    }

    // Depósito ou saque de valor zero/negativo não faz sentido.
    private void validarValor(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
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

    /** Regra de saque de cada tipo de conta. */
    protected abstract boolean podeSacar(BigDecimal valor);

    /** Valor da coluna "tipo" no banco: "CORRENTE" ou "POUPANCA". */
    public abstract String getTipoPersistencia();

    /** Valor da coluna "parametro": limite (corrente) ou taxa de rendimento (poupança). */
    public abstract BigDecimal getParametroPersistencia();

}
