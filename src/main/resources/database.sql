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

CREATE TABLE IF NOT EXISTS colaborador (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    usuario TEXT NOT NULL UNIQUE,
    senha_hash TEXT NOT NULL,
    senha_salt TEXT NOT NULL,
    cpf TEXT NOT NULL UNIQUE,
    agencia TEXT NOT NULL
);
