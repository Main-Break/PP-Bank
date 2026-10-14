package com.ppbank.database;

/** Agrupa gravações: ou todas são salvas (confirmar) ou nenhuma (desfazer). */
public interface Transacao {

    void iniciar();

    void confirmar();

    void desfazer();

}
