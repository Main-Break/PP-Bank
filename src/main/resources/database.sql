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
