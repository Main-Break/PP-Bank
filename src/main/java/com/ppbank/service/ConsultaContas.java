package com.ppbank.service;

import com.ppbank.model.Conta;

import java.util.List;

public interface ConsultaContas {

    List<Conta> listarTodas();

    void excluir(long id);

}
