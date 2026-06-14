package br.ufrn.banco.api.dto;

public class OperacaoRequest {

    private double valor;

    public OperacaoRequest() {
    }

    public OperacaoRequest(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

