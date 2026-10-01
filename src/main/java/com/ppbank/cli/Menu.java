package com.ppbank.cli;

import java.util.Scanner;

public interface Menu {

    void exibir();

    Menu processarOpcao(int opcao, Scanner leitor);

}
