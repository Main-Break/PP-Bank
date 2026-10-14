package com.ppbank.model;

/** Hash da senha e o salt usado para gerá-lo. É isso que é salvo, nunca a senha. */
public record HashSenha(String hash, String salt) {
}
