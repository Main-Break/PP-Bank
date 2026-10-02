package com.ppbank.service;

import com.ppbank.model.Colaborador;
import com.ppbank.model.HashSenha;

public interface RepositorioColaborador {

    void salvar(Colaborador colaborador, HashSenha credencial);

    Colaborador buscarPorUsuario(String usuario);

    HashSenha buscarCredencial(String usuario);

}
