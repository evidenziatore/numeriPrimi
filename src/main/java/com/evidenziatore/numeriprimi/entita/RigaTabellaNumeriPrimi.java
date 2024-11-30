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

    public String getDivisorePrimo() {
        return divisorePrimo;
    }

    public String getPotenzaDivisore() {
        return potenzaDivisore;
    }

    public String getRisultato() {
        return risultato;
    }

    public Long getTempiDiCalcolo() {
        return tempiDiCalcolo;
    }
}