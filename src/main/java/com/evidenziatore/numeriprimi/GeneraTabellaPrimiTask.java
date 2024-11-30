package com.evidenziatore.numeriprimi;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GeneraTabellaPrimiTask extends Task<List<NumeriPrimiObject>> {

    private BigInteger numeroCercato;

    private boolean daCancellare = false;

    List<NumeriPrimiObject> numeriPrimiObjectList;

    public GeneraTabellaPrimiTask(BigInteger numeroCercato, List<NumeriPrimiObject> numeriPrimiObjectList) {
        this.numeroCercato = numeroCercato;
        this.numeriPrimiObjectList = numeriPrimiObjectList;
    }

    @Override
    protected List<NumeriPrimiObject> call() {
        Date dataInizio = new Date();
        getNumeroCercato();
        BigInteger divisore = primoDivisore(numeroCercato, getDivisorePrimo());
        while (numeroCercato.compareTo(BigInteger.ONE) > 0 && numeroCercato.compareTo(divisore) >= 0) {
            if (daCancellare) break;
            BigInteger numeroIniziale = numeroCercato;
            BigInteger potenzaPrimo = BigInteger.ZERO;
            do {
                if (daCancellare) break;
                numeroCercato = numeroCercato.divide(divisore);
                potenzaPrimo = potenzaPrimo.add(BigInteger.ONE);
            } while (numeroCercato.mod(divisore).equals(BigInteger.ZERO));
            NumeriPrimiObject numeriPrimiObject = new NumeriPrimiObject(numeroIniziale.toString(),
                    divisore.toString(),
                    potenzaPrimo.toString(),
                    numeroCercato.toString(),
                    (new Date().getTime() - dataInizio.getTime()));
            numeriPrimiObjectList.add(numeriPrimiObject);
            dataInizio = new Date();
            divisore = primoDivisore(numeroCercato, divisore);
        }
        return numeriPrimiObjectList;
    }

    private void getNumeroCercato() {
        if (!numeriPrimiObjectList.isEmpty()) {
            for (NumeriPrimiObject numeriPrimiObject : numeriPrimiObjectList) {
                BigInteger counter = BigInteger.ONE;
                while (counter.compareTo(new BigInteger(numeriPrimiObject.getPotenzaDivisore())) <= 0) {
                    counter = counter.add(BigInteger.ONE);
                    numeroCercato = numeroCercato.divide(new BigInteger(numeriPrimiObject.getDivisorePrimo()));
                }
            }
        }
    }

    private BigInteger getDivisorePrimo() {
        if (!numeriPrimiObjectList.isEmpty()) {
            BigInteger counter = new BigInteger(numeriPrimiObjectList.getFirst().getDivisorePrimo());
            for (NumeriPrimiObject numeriPrimiObject : numeriPrimiObjectList) {
                if (counter.compareTo(new BigInteger(numeriPrimiObject.getPotenzaDivisore())) <= 0) {
                    counter = new BigInteger(numeriPrimiObject.getPotenzaDivisore());
                }
            }
        }
        return BigInteger.valueOf(2);
    }

    private BigInteger primoDivisore(BigInteger numero, BigInteger possibileDivisore) {
        for (BigInteger i = possibileDivisore; i.multiply(i).compareTo(numero) <= 0; i = i.add(BigInteger.ONE)) {
            if (daCancellare) break;
            if (numero.mod(i).equals(BigInteger.ZERO)) {
                return i;
            }
        }
        return numero;
    }

    public void setDaCancellare(boolean daCancellare) {
        this.daCancellare = daCancellare;
    }
}