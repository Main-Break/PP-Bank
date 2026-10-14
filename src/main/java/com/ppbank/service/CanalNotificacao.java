package com.ppbank.service;

/** Strategy: como avisar o cliente. Novo canal = nova classe, sem mexer no serviço (OCP). */
public interface CanalNotificacao {

    void notificar(String destinatario, String mensagem);

}
