package br.ufrn.banco.api.dto;

public class TransferenciaRequest {

    private int from;
    private int to;
    private double amount;

    public TransferenciaRequest() {
    }

    public TransferenciaRequest(int from, int to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

