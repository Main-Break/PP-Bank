package com.ppbank.service;

import com.ppbank.model.Colaborador;
import com.ppbank.model.HashSenha;

public class ServicoColaborador {

    private final RepositorioColaborador repositorioColaborador;
    private final CodificadorSenha codificadorSenha;

    public ServicoColaborador(RepositorioColaborador repositorioColaborador, CodificadorSenha codificadorSenha) {
        this.repositorioColaborador = repositorioColaborador;
        this.codificadorSenha = codificadorSenha;
    }

    public void criar(String nome, String usuario, String senha, String cpf, String agencia) {
        Colaborador colaborador = new Colaborador(0, nome, usuario, cpf, agencia);
        HashSenha credencial = this.codificadorSenha.codificar(senha);

        this.repositorioColaborador.salvar(colaborador, credencial);
    }

    public boolean autenticar(String usuario, String senha) {
        HashSenha credencial = this.repositorioColaborador.buscarCredencial(usuario);

        if (credencial == null) {
            return false;
        }

        return this.codificadorSenha.verificar(senha, credencial);
    }

}
