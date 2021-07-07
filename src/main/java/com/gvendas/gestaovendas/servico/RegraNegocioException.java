package com.gvendas.gestaovendas.servico;

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem){
        super(mensagem);
    }
}
