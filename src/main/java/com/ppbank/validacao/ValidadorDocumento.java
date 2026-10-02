package com.ppbank.validacao;

public final class ValidadorDocumento {

    private ValidadorDocumento() {
    }

    public static boolean validarCpf(String cpf) {
        String valor = cpf.replaceAll("[^0-9]", "");

        if (valor.length() != 11 || todosOsCaracteresIguais(valor)) {
            return false;
        }

        int primeiroDigito = calcularDigito(valor.substring(0, 9), new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2});
        int segundoDigito = calcularDigito(valor.substring(0, 9) + primeiroDigito, new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2});

        return valor.equals(valor.substring(0, 9) + primeiroDigito + segundoDigito);
    }

    /**
     * Valida CNPJ no formato alfanumérico adotado pela Receita Federal: os 12
     * primeiros caracteres podem ser dígitos ou letras (A-Z), e os 2 dígitos
     * verificadores finais continuam sempre numéricos. O valor de cada
     * caractere no cálculo é o seu código ASCII subtraído de 48.
     */
    public static boolean validarCnpj(String cnpj) {
        String valor = cnpj.toUpperCase().replaceAll("[^0-9A-Z]", "");

        if (valor.length() != 14 || todosOsCaracteresIguais(valor)) {
            return false;
        }

        String base = valor.substring(0, 12);
        String digitosInformados = valor.substring(12);

        if (!digitosInformados.chars().allMatch(Character::isDigit)) {
            return false;
        }

        int primeiroDigito = calcularDigitoAscii(base, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int segundoDigito = calcularDigitoAscii(base + primeiroDigito, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return digitosInformados.equals("" + primeiroDigito + segundoDigito);
    }

    private static boolean todosOsCaracteresIguais(String valor) {
        return valor.chars().distinct().count() == 1;
    }

    private static int calcularDigito(String base, int[] pesos) {
        int soma = 0;

        for (int i = 0; i < pesos.length; i++) {
            soma += (base.charAt(i) - '0') * pesos[i];
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    private static int calcularDigitoAscii(String base, int[] pesos) {
        int soma = 0;

        for (int i = 0; i < pesos.length; i++) {
            soma += (base.charAt(i) - 48) * pesos[i];
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

}
