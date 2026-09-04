package com.ppbank;

import com.ppbank.modelo.ContaCorrente;
import com.ppbank.modelo.ContaPoupanca;
import com.ppbank.repositorio.RepositorioConta;
import com.ppbank.repositorio.RepositorioContaEmMemoria;
import com.ppbank.servico.ServicoConta;

public class Main {

    public static void main(String[] args) {
        RepositorioConta repositorio = new RepositorioContaEmMemoria();
        ServicoConta servicoConta = new ServicoConta(repositorio);

        servicoConta.abrirConta("001", "Kainan H.", new ContaCorrente());
        servicoConta.abrirConta("002", "Maria Silva", new ContaPoupanca());

        servicoConta.depositar("001", 1000.0);
        servicoConta.depositar("002", 500.0);

        servicoConta.transferir("001", "002", 200.0);

        servicoConta.sacar("002", 100.0);

        System.out.printf("Conta 001 - saldo: R$ %.2f%n", servicoConta.consultarSaldo("001"));
        System.out.printf("Conta 002 - saldo: R$ %.2f%n", servicoConta.consultarSaldo("002"));
    }
}
