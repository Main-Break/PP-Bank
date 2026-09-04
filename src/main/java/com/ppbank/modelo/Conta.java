package com.ppbank.modelo;

import com.ppbank.excecao.SaldoInsuficienteException;

/**
 * Entidade Conta: responsável apenas por manter e proteger o seu próprio saldo.
 * A regra de taxação varia por tipo de conta (TipoConta), que é injetado
 * no momento da criação e nunca é verificado por "if/instanceof" aqui dentro
 * (Liskov: qualquer TipoConta pode ser usado sem quebrar o comportamento da conta).
 */
public class Conta {

    private final String numero;
    private final String titular;
    private final TipoConta tipo;
    private double saldo;

    public Conta(String numero, String titular, TipoConta tipo) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Número da conta é obrigatório");
        }
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("Titular é obrigatório");
        }
        this.numero = numero;
        this.titular = titular;
        this.tipo = tipo;
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser maior que zero");
        }
        this.saldo += valor;
    }

    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser maior que zero");
        }
        double total = valor + tipo.calcularTaxaSaque(valor);
        if (total > saldo) {
            throw new SaldoInsuficienteException(numero);
        }
        this.saldo -= total;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public double getSaldo() {
        return saldo;
    }
}
