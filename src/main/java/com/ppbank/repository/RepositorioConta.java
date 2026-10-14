package com.ppbank.repository;

import com.ppbank.model.Conta;

/** O que o ServicoTransferencia precisa das contas. Interface pequena (ISP) para o serviço não depender do SQLite (DIP). */
public interface RepositorioConta {

    Conta buscarPorId(long id);

    long salvar(Conta conta);

}
