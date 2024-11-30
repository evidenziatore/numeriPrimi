package com.evidenziatore.numeriprimi.entita;

public class RigaTabellaNumeriPrimi {
    private String numero;
    private String divisorePrimo;
    private String potenzaDivisore;
    private String risultato;
    private Long tempiDiCalcolo;

    public RigaTabellaNumeriPrimi() {
    }

    public RigaTabellaNumeriPrimi(String numero, String divisorePrimo, String potenzaDivisore, String risultato, Long tempiDiCalcolo) {
        this.numero = numero;
        this.divisorePrimo = divisorePrimo;
        this.potenzaDivisore = potenzaDivisore;
        this.risultato = risultato;
        this.tempiDiCalcolo = tempiDiCalcolo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDivisorePrimo() {
        return divisorePrimo;
    }

    public void setDivisorePrimo(String divisorePrimo) {
        this.divisorePrimo = divisorePrimo;
    }

    public String getPotenzaDivisore() {
        return potenzaDivisore;
    }

    public void setPotenzaDivisore(String potenzaDivisore) {
        this.potenzaDivisore = potenzaDivisore;
    }

    public String getRisultato() {
        return risultato;
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
    }

    public Long getTempiDiCalcolo() {
        return tempiDiCalcolo;
    }

    public void setTempiDiCalcolo(Long tempiDiCalcolo) {
        this.tempiDiCalcolo = tempiDiCalcolo;
    }
}