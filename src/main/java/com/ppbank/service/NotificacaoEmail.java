package com.ppbank.service;

public class NotificacaoEmail implements CanalNotificacao {

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("[E-mail para " + destinatario + "] " + mensagem);
    }

}
