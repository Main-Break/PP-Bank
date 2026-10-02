package com.ppbank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transferencia {

    private final long id;
    private final long idContaOrigem;
    private final long idContaDestino;
    private final BigDecimal valor;
    private final String forma;
    private final LocalDateTime dataHora;

    public Transferencia(long id, long idContaOrigem, long idContaDestino, BigDecimal valor, String forma,
                          LocalDateTime dataHora) {
        this.id = id;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
        this.forma = forma;
        this.dataHora = dataHora;
    }

    public long getId() {
        return id;
    }

    public long getIdContaOrigem() {
        return idContaOrigem;
    }

    public long getIdContaDestino() {
        return idContaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getForma() {
        return forma;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

}
