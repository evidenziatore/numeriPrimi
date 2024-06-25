package com.evidenziatore.numeriprimi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NumeriPrimiService {

    public List<NumeriPrimiObject> generaTabellaPrimi(BigInteger numeroCorrente) {
        List<NumeriPrimiObject> numeriPrimiObjectList = new ArrayList<>();
        Date dataInizio = new Date();
        BigInteger divisore = primoDivisore(numeroCorrente, BigInteger.valueOf(2));
        while (numeroCorrente.compareTo(BigInteger.ONE) > 0 && numeroCorrente.compareTo(divisore) >= 0) {
            BigInteger numeroIniziale = numeroCorrente;
            BigInteger potenzaPrimo = BigInteger.ZERO;
            do {
                numeroCorrente = numeroCorrente.divide(divisore);
                potenzaPrimo = potenzaPrimo.add(BigInteger.ONE);
            } while (numeroCorrente.mod(divisore).equals(BigInteger.ZERO));
            NumeriPrimiObject numeriPrimiObject = new NumeriPrimiObject(numeroIniziale.toString(),
                    divisore.toString(),
                    potenzaPrimo.toString(),
                    numeroCorrente.toString(),
                    (new Date().getTime() - dataInizio.getTime()));
            numeriPrimiObjectList.add(numeriPrimiObject);
            dataInizio = new Date();
            divisore = primoDivisore(numeroCorrente, divisore);
        }
        return numeriPrimiObjectList;
    }

    private static BigInteger primoDivisore(BigInteger numero, BigInteger possibileDivisore) {
        for (BigInteger i = possibileDivisore; i.multiply(i).compareTo(numero) <= 0; i = i.add(BigInteger.ONE)) {
            if (numero.mod(i).equals(BigInteger.ZERO)) {
                return i;
            }
        }
        return numero;
    }

}