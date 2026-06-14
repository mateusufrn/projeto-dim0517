package br.ufrn.banco.api.dto;

public class CadastrarContaRequest {

    private int numero;
    private String tipo;
    private double saldoInicial;

    public CadastrarContaRequest() {
    }

    public CadastrarContaRequest(int numero, String tipo, double saldoInicial) {
        this.numero = numero;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}

