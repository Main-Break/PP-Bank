package com.ppbank.model;

public class Colaborador {

    private final long id;
    private final String nome;
    private final String usuario;
    private final String cpf;
    private final String agencia;

    public Colaborador(long id, String nome, String usuario, String cpf, String agencia) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.cpf = cpf;
        this.agencia = agencia;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAgencia() {
        return agencia;
    }

}
