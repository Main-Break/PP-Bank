package com.ppbank.service;

import com.ppbank.model.HashSenha;

public interface CodificadorSenha {

    HashSenha codificar(String senha);

    boolean verificar(String senha, HashSenha hash);

}
