package com.ppbank.exception;

/** Lançada por Conta.sacar() quando podeSacar() recusa o valor. */
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }

}
