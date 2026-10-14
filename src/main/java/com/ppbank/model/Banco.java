package com.ppbank.model;

/** Dados cadastrais do banco principal (nome, código, CNPJ e endereço). */
public class Banco {

    private final long id;
    private final String nome;
    private final String codigo;
    private final String cnpj;
    private final String endereco;

    public Banco(long id, String nome, String codigo, String cnpj, String endereco) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

}
