package com.evidenziatore.numeriprimi;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeneraTabellaPrimiTask extends Task<List<NumeriPrimiObject>> {

    private BigInteger numeroCercato;

    public GeneraTabellaPrimiTask(BigInteger numeroCercato) {
        this.numeroCercato = numeroCercato;
    }

    @Override
    protected List<NumeriPrimiObject> call() {
        List<NumeriPrimiObject> numeriPrimiObjectList = new ArrayList<>();
        Date dataInizio = new Date();
        BigInteger divisore = primoDivisore(numeroCercato, BigInteger.valueOf(2));
        while (numeroCercato.compareTo(BigInteger.ONE) > 0 && numeroCercato.compareTo(divisore) >= 0) {
            BigInteger numeroIniziale = numeroCercato;
            BigInteger potenzaPrimo = BigInteger.ZERO;
            do {
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

    private BigInteger primoDivisore(BigInteger numero, BigInteger possibileDivisore) {
        for (BigInteger i = possibileDivisore; i.multiply(i).compareTo(numero) <= 0; i = i.add(BigInteger.ONE)) {
            if (numero.mod(i).equals(BigInteger.ZERO)) {
                return i;
            }
        }
        return numero;
    }

}