package com.ppbank.modelo;

/**
 * Conta corrente: cobra uma taxa fixa a cada saque.
 */
public class ContaCorrente implements TipoConta {

    private static final double TAXA_SAQUE = 0.50;

    @Override
    public double calcularTaxaSaque(double valorSaque) {
        return TAXA_SAQUE;
    }

    @Override
    public String getDescricao() {
        return "Conta Corrente";
    }
}
