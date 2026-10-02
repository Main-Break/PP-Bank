-- Schema consolidado do banco de dados do PP-Bank.
-- Este arquivo reflete sempre o schema completo e atual do banco,
-- nunca uma pilha de arquivos incrementais separados.
--
-- Nunca execute este arquivo manualmente: a aplicação aplica as
-- migrações automaticamente ao iniciar, através da classe Migrador
-- (com.ppbank.database.Migrador).

CREATE TABLE IF NOT EXISTS schema_version (
    versao INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS banco (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    codigo TEXT NOT NULL,
    cnpj TEXT NOT NULL UNIQUE,
    endereco TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS filial (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_banco INTEGER NOT NULL REFERENCES banco(id),
    nome TEXT NOT NULL,
    codigo TEXT NOT NULL,
    cnpj TEXT NOT NULL UNIQUE,
    endereco TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS colaborador (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha_hash TEXT NOT NULL,
    senha_salt TEXT NOT NULL,
    cpf TEXT NOT NULL UNIQUE,
    agencia TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS conta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo TEXT NOT NULL,
    numero TEXT NOT NULL UNIQUE,
    titular TEXT NOT NULL,
    saldo NUMERIC NOT NULL,
    parametro NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS transferencia (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_conta_origem INTEGER NOT NULL REFERENCES conta(id),
    id_conta_destino INTEGER NOT NULL REFERENCES conta(id),
    valor NUMERIC NOT NULL,
    forma TEXT NOT NULL,
    data_hora TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS cartao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo TEXT NOT NULL,
    numero TEXT NOT NULL UNIQUE,
    titular TEXT NOT NULL,
    valor NUMERIC NOT NULL
);
