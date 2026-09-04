package com.ppbank.servico;

import com.ppbank.excecao.ContaNaoEncontradaException;
import com.ppbank.excecao.SaldoInsuficienteException;
import com.ppbank.modelo.ContaCorrente;
import com.ppbank.modelo.ContaPoupanca;
import com.ppbank.repositorio.RepositorioConta;
import com.ppbank.repositorio.RepositorioContaEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoContaTest {

    private ServicoConta servicoConta;

    @BeforeEach
    void setUp() {
        RepositorioConta repositorio = new RepositorioContaEmMemoria();
        servicoConta = new ServicoConta(repositorio);
    }

    @Test
    void deveDepositarEAtualizarSaldo() {
        servicoConta.abrirConta("001", "Kainan H.", new ContaPoupanca());
        servicoConta.depositar("001", 100.0);

        assertEquals(100.0, servicoConta.consultarSaldo("001"));
    }

    @Test
    void deveCobrarTaxaDeSaqueNaContaCorrente() {
        servicoConta.abrirConta("001", "Kainan H.", new ContaCorrente());
        servicoConta.depositar("001", 100.0);
        servicoConta.sacar("001", 50.0);

        assertEquals(49.50, servicoConta.consultarSaldo("001"));
    }

    @Test
    void naoDeveSacarSemSaldoSuficiente() {
        servicoConta.abrirConta("001", "Kainan H.", new ContaPoupanca());
        servicoConta.depositar("001", 10.0);

        assertThrows(SaldoInsuficienteException.class, () -> servicoConta.sacar("001", 50.0));
    }

    @Test
    void deveTransferirEntreContas() {
        servicoConta.abrirConta("001", "Kainan H.", new ContaCorrente());
        servicoConta.abrirConta("002", "Maria Silva", new ContaPoupanca());
        servicoConta.depositar("001", 100.0);

        servicoConta.transferir("001", "002", 30.0);

        assertEquals(69.50, servicoConta.consultarSaldo("001"));
        assertEquals(30.0, servicoConta.consultarSaldo("002"));
    }

    @Test
    void deveLancarErroAoBuscarContaInexistente() {
        assertThrows(ContaNaoEncontradaException.class, () -> servicoConta.consultarSaldo("999"));
    }
}
