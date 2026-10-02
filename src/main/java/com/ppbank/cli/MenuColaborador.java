package com.ppbank.cli;

import com.ppbank.repository.ColaboradorRepositorioSqlite;
import com.ppbank.service.CodificadorSenhaSha256;
import com.ppbank.service.ServicoColaborador;

import java.util.Scanner;

public class MenuColaborador extends MenuBase {

    private final ServicoColaborador servicoColaborador =
            new ServicoColaborador(new ColaboradorRepositorioSqlite(), new CodificadorSenhaSha256());

    @Override
    public void exibir() {
        this.limparTela();

        System.out.println("+-----------------------------+");
        System.out.println("    Colaboradores");
        System.out.println("+-----------------------------+");
        System.out.println("[1] - Criar Colaborador");
        System.out.println("[2] - Testar Autenticação");
        System.out.println("+-----------------------------+");
        System.out.println("[0] - Voltar");
        System.out.println("+-----------------------------+");
    }

    @Override
    public Menu processarOpcao(int opcao, Scanner leitor) {
        switch (opcao) {
            case 1:
                this.criarColaborador(leitor);
                return this;
            case 2:
                this.testarAutenticacao(leitor);
                return this;
            case 0:
                return new MenuBanco();
            default:
                return this.opcaoInvalida(leitor);
        }
    }

    void criarColaborador(Scanner leitor) {
        System.out.print("Informe o nome do colaborador: ");
        String nome = leitor.nextLine();

        System.out.print("Informe o usuário de acesso: ");
        String usuario = leitor.nextLine();

        System.out.print("Informe a senha de acesso: ");
        String senha = leitor.nextLine();

        System.out.print("Informe o CPF do colaborador: ");
        String cpf = leitor.nextLine();

        System.out.print("Informe a agência a qual ele pertence: ");
        String agencia = leitor.nextLine();

        this.servicoColaborador.criar(nome, usuario, senha, cpf, agencia);

        System.out.println("Colaborador criado com sucesso.");
        this.pausar(leitor);
    }

    void testarAutenticacao(Scanner leitor) {
        System.out.print("Informe o usuário: ");
        String usuario = leitor.nextLine();

        System.out.print("Informe a senha: ");
        String senha = leitor.nextLine();

        boolean autenticado = this.servicoColaborador.autenticar(usuario, senha);

        System.out.println(autenticado ? "Credenciais válidas." : "Credenciais inválidas.");
        this.pausar(leitor);
    }

}
