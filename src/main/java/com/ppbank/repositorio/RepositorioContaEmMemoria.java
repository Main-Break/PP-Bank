package com.ppbank.repositorio;

import com.ppbank.modelo.Conta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementação simples em memória, útil para o projeto base e para testes.
 * Pode ser substituída por uma implementação que acesse um banco de dados real
 * sem que ServicoConta precise mudar uma linha sequer.
 */
public class RepositorioContaEmMemoria implements RepositorioConta {

    private final Map<String, Conta> contas = new LinkedHashMap<>();

    @Override
    public void salvar(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    @Override
    public Optional<Conta> buscarPorNumero(String numero) {
        return Optional.ofNullable(contas.get(numero));
    }

    @Override
    public List<Conta> listarTodas() {
        return List.copyOf(contas.values());
    }
}
