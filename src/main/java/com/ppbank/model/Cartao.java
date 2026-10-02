package com.ppbank.model;

import java.math.BigDecimal;

public class Cartao {

    private final long id;
    private final String tipo;
    private final String numero;
    private final String titular;
    private final BigDecimal valor;

    public Cartao(long id, String tipo, String numero, String titular, BigDecimal valor) {
        this.id = id;
        this.tipo = tipo;
        this.numero = numero;
        this.titular = titular;
        this.valor = valor;
    }

    public long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
