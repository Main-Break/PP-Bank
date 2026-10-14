package com.ppbank.service;

/** Aviso por e-mail (simulado no console). */
public class NotificacaoEmail implements CanalNotificacao {

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("[E-mail para " + destinatario + "] " + mensagem);
    }

}
