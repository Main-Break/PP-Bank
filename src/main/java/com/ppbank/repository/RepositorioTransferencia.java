package com.ppbank.repository;

import com.ppbank.model.Transferencia;

/** Grava o histórico de transferências. */
public interface RepositorioTransferencia {

    void registrar(Transferencia transferencia);

}
