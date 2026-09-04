package com.ppbank.servico;

import com.ppbank.excecao.ContaNaoEncontradaException;
import com.ppbank.modelo.Conta;
import com.ppbank.modelo.TipoConta;
import com.ppbank.repositorio.RepositorioConta;

/**
 * Orquestra as operações bancárias. Depende apenas da abstração RepositorioConta
 * (recebida via construtor), nunca de uma implementação concreta - isso permite
 * trocar o armazenamento ou usar um repositório falso nos testes sem tocar aqui.
 */
public class ServicoConta {

    private final RepositorioConta repositorio;

    public ServicoConta(RepositorioConta repositorio) {
        this.repositorio = repositorio;
    }

    public Conta abrirConta(String numero, String titular, TipoConta tipo) {
        if (repositorio.buscarPorNumero(numero).isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com o número " + numero);
        }
        Conta conta = new Conta(numero, titular, tipo);
        repositorio.salvar(conta);
        return conta;
    }

    public void depositar(String numero, double valor) {
        Conta conta = buscarConta(numero);
        conta.depositar(valor);
        repositorio.salvar(conta);
    }

    public void sacar(String numero, double valor) {
        Conta conta = buscarConta(numero);
        conta.sacar(valor);
        repositorio.salvar(conta);
    }

    public void transferir(String numeroOrigem, String numeroDestino, double valor) {
        Conta origem = buscarConta(numeroOrigem);
        Conta destino = buscarConta(numeroDestino);
        origem.sacar(valor);
        destino.depositar(valor);
        repositorio.salvar(origem);
        repositorio.salvar(destino);
    }

    public double consultarSaldo(String numero) {
        return buscarConta(numero).getSaldo();
    }

    private Conta buscarConta(String numero) {
        return repositorio.buscarPorNumero(numero)
                .orElseThrow(() -> new ContaNaoEncontradaException(numero));
    }
}
