package com.ppbank.repositorio;

import com.ppbank.modelo.Conta;

import java.util.List;
import java.util.Optional;

/**
 * Abstração de persistência de contas. O ServicoConta depende apenas desta
 * interface, nunca de uma implementação concreta (Dependency Inversion) -
 * trocar de armazenamento em memória para um banco de dados real não exige
 * alterar nenhuma regra de negócio.
 */
public interface RepositorioConta {

    void salvar(Conta conta);

    Optional<Conta> buscarPorNumero(String numero);

    List<Conta> listarTodas();
}
