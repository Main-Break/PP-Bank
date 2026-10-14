package com.ppbank.service;

/** Aviso por WhatsApp (simulado no console). */
public class NotificacaoWhatsapp implements CanalNotificacao {

    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("[WhatsApp para " + destinatario + "] " + mensagem);
    }

}
