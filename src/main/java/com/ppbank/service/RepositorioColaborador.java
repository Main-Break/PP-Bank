package com.ppbank.service;

import com.ppbank.model.Colaborador;
import com.ppbank.model.HashSenha;

public interface RepositorioColaborador {

    void salvar(Colaborador colaborador, HashSenha credencial);

    HashSenha buscarCredencial(String usuario);

}
