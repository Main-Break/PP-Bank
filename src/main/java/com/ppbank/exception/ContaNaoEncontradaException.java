package com.ppbank.exception;

public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException(long id) {
        super("Conta com id " + id + " não encontrada.");
    }

}
