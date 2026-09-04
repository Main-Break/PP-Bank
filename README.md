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
  model/       Entidades e regras de negócio (Account, AccountType, CheckingAccount, SavingsAccount)
  repository/  Abstração e implementação de persistência (AccountRepository)
  service/     Orquestração das operações bancárias (AccountService)
  exception/   Exceções de domínio
  Main.java    Ponto de entrada, apenas para demonstração
```

## Onde cada princípio SOLID aparece

- **S (Responsabilidade Única):** `Account` só protege o próprio saldo, `AccountRepository` só persiste, `AccountService` só orquestra.
- **O (Aberto/Fechado):** novos tipos de conta são criados implementando `AccountType`, sem alterar `Account` nem os tipos já existentes.
- **L (Substituição de Liskov):** qualquer `AccountType` pode substituir outro sem quebrar `Account.withdraw`.
- **I (Segregação de Interfaces):** `AccountRepository` expõe só os métodos que `AccountService` realmente usa.
- **D (Inversão de Dependência):** `AccountService` depende da interface `AccountRepository`, não de `InMemoryAccountRepository`. Trocar por um repositório com banco de dados real não exige alterar as regras de negócio.
