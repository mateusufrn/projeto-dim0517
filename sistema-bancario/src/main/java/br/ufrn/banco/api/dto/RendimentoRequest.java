package br.ufrn.banco.api.dto;

public class RendimentoRequest {

    private double taxaJuros;

    public RendimentoRequest() {
    }

    public RendimentoRequest(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }
}

