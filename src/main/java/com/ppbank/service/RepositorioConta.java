package com.ppbank.service;

import com.ppbank.model.Conta;

public interface RepositorioConta {

    Conta buscarPorId(long id);

    long salvar(Conta conta);

}
