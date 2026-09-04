# PP-Bank
Projeto de banco para a disciplina de Padrões de Projeto

## Requisitos

- Java 17+
- Maven

## Como rodar

```
mvn compile exec:java -Dexec.mainClass="com.ppbank.Main"
```

## Como testar

```
mvn test
```

## Estrutura

```
src/main/java/com/ppbank/
  modelo/       Entidades e regras de negócio (Conta, TipoConta, ContaCorrente, ContaPoupanca)
  repositorio/  Abstração e implementação de persistência (RepositorioConta)
  servico/      Orquestração das operações bancárias (ServicoConta)
  excecao/      Exceções de domínio
  Main.java     Ponto de entrada, apenas para demonstração
```

## Onde cada princípio SOLID aparece

- **S (Responsabilidade Única):** `Conta` só protege o próprio saldo, `RepositorioConta` só persiste, `ServicoConta` só orquestra.
- **O (Aberto/Fechado):** novos tipos de conta são criados implementando `TipoConta`, sem alterar `Conta` nem os tipos já existentes.
- **L (Substituição de Liskov):** qualquer `TipoConta` pode substituir outro sem quebrar `Conta.sacar`.
- **I (Segregação de Interfaces):** `RepositorioConta` expõe só os métodos que `ServicoConta` realmente usa.
- **D (Inversão de Dependência):** `ServicoConta` depende da interface `RepositorioConta`, não de `RepositorioContaEmMemoria`. Trocar por um repositório com banco de dados real não exige alterar as regras de negócio.
