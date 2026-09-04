package com.ppbank.modelo;

/**
 * Abstração da regra de taxação de cada tipo de conta.
 * Novos tipos de conta são adicionados criando uma nova implementação,
 * sem alterar o código de Conta ou dos demais tipos já existentes (Open/Closed).
 */
public interface TipoConta {

    double calcularTaxaSaque(double valorSaque);

    String getDescricao();
}
