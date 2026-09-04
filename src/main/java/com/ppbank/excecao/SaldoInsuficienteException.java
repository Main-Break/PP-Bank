package com.ppbank.excecao;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String numeroConta) {
        super("Saldo insuficiente na conta " + numeroConta);
    }
}
