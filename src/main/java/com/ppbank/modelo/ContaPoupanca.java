package com.ppbank.modelo;

/**
 * Conta poupança: não cobra taxa de saque.
 */
public class ContaPoupanca implements TipoConta {

    @Override
    public double calcularTaxaSaque(double valorSaque) {
        return 0.0;
    }

    @Override
    public String getDescricao() {
        return "Conta Poupança";
    }
}
