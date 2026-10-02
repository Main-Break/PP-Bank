package com.ppbank.service;

public class NotificacaoWhatsapp implements CanalNotificacao {

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("[WhatsApp para " + destinatario + "] " + mensagem);
    }

}
