package com.ppbank.exception;

/** Lançada quando o id informado não existe na tabela conta. */
public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException(long id) {
        super("Conta com id " + id + " não encontrada.");
    }

}
