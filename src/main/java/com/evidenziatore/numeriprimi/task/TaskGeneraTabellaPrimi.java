package com.evidenziatore.numeriprimi.task;

import com.evidenziatore.numeriprimi.entita.RigaTabellaNumeriPrimi;
import javafx.concurrent.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TaskGeneraTabellaPrimi extends Task<List<RigaTabellaNumeriPrimi>> {

    private BigInteger numeroCercato;
    private boolean daStoppare = false;
    List<RigaTabellaNumeriPrimi> listaRigaTabellaNumeriPrimi;

    /**
     * @param numeroCercato               Il BigInteger corrispondente al numero inserito nel TextField dall'utente o un suo divisore.
     * @param listaRigaTabellaNumeriPrimi La List<RigaTabellaNumeriPrimi> corrispondente all'elenco delle righe gia' individuate della TableView dei divisori del numero inserito nel TextField dall'utente.
     */
    public TaskGeneraTabellaPrimi(BigInteger numeroCercato, List<RigaTabellaNumeriPrimi> listaRigaTabellaNumeriPrimi) {
        this.numeroCercato = numeroCercato;
        this.listaRigaTabellaNumeriPrimi = listaRigaTabellaNumeriPrimi;
    }

    /**
     * @return L'elenco delle righe della TableView dei divisori del numero inserito nel TextField dall'utente.
     * @throws IOException Se non riesce a scrivere correttamente su file.
     */
    @Override
    protected List<RigaTabellaNumeriPrimi> call() throws IOException {
        Date dataInizio = new Date();
        modificaNumeroCercatoSeAnnullaContinua();
        BigInteger divisore = primoDivisore(numeroCercato, modificaDivisorePrimoSeAnnullaContinua());
        while (numeroCercato.compareTo(BigInteger.ONE) > 0 && numeroCercato.compareTo(divisore) >= 0) {
            if (daStoppare) break;
            BigInteger numeroIniziale = numeroCercato;
            BigInteger potenzaPrimo = BigInteger.ZERO;
            do {
                numeroCercato = numeroCercato.divide(divisore);
                potenzaPrimo = potenzaPrimo.add(BigInteger.ONE);
            } while (numeroCercato.mod(divisore).equals(BigInteger.ZERO));
            RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi = new RigaTabellaNumeriPrimi(numeroIniziale.toString(),
                    divisore.toString(),
                    potenzaPrimo.toString(),
                    numeroCercato.toString(),
                    (new Date().getTime() - dataInizio.getTime()));
            listaRigaTabellaNumeriPrimi.add(rigaTabellaNumeriPrimi);
            dataInizio = new Date();
            divisore = primoDivisore(numeroCercato, divisore);
        }
        scriviDivisoriSuFile();
        return listaRigaTabellaNumeriPrimi;
    }

    private void modificaNumeroCercatoSeAnnullaContinua() {
        if (!listaRigaTabellaNumeriPrimi.isEmpty())
            for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : listaRigaTabellaNumeriPrimi) {
                BigInteger contatore = BigInteger.ONE;
                while (contatore.compareTo(new BigInteger(rigaTabellaNumeriPrimi.potenzaDivisore())) <= 0) {
                    contatore = contatore.add(BigInteger.ONE);
                    numeroCercato = numeroCercato.divide(new BigInteger(rigaTabellaNumeriPrimi.divisorePrimo()));
                }
            }
    }

    /**
     * @param numero            Il BigInteger corrispondente al numero inserito nel TextField dall'utente o un suo divisore.
     * @param possibileDivisore Il BigInteger corrispondente ad un possibile divisore del numero inserito nel TextField dall'utente.
     * @return Il primo divisore del numero inserito nel TextField dall'utente maggiore o uguale al possibile divisore passato in input.
     */
    private BigInteger primoDivisore(BigInteger numero, BigInteger possibileDivisore) {
        for (BigInteger i = possibileDivisore; i.multiply(i).compareTo(numero) <= 0; i = i.add(BigInteger.ONE)) {
            if (daStoppare) break;
            if (numero.mod(i).equals(BigInteger.ZERO)) return i;
        }
        return numero;
    }

    /**
     * @return Il primo possibile divisore a seguito del click sul pulsante 'Continua'.
     */
    private BigInteger modificaDivisorePrimoSeAnnullaContinua() {
        if (!listaRigaTabellaNumeriPrimi.isEmpty()) {
            BigInteger contatore = new BigInteger(listaRigaTabellaNumeriPrimi.getFirst().divisorePrimo());
            for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : listaRigaTabellaNumeriPrimi)
                if (contatore.compareTo(new BigInteger(rigaTabellaNumeriPrimi.potenzaDivisore())) <= 0)
                    contatore = new BigInteger(rigaTabellaNumeriPrimi.potenzaDivisore());
            return contatore.add(BigInteger.ONE);
        }
        return BigInteger.valueOf(2);
    }

    /**
     * @param daStoppare Il boolean che indica se si deve fermare l'esecuzione dell'operazione.
     */
    public void setDaStoppare(boolean daStoppare) {
        this.daStoppare = daStoppare;
    }

    /**
     * @throws IOException Se non riesce a scrivere correttamente su file.
     */
    private void scriviDivisoriSuFile() throws IOException {
        Path percorsoFile = Paths.get("src/main/resources", "tabellaNumeroDivisore.txt");
        Set<BigInteger> numeriEsistenti = new HashSet<>();
        if (Files.exists(percorsoFile)) {
            List<String> righeEsistenti = Files.readAllLines(percorsoFile);
            for (String riga : righeEsistenti) {
                try {
                    numeriEsistenti.add(new BigInteger(riga.trim()));
                } catch (NumberFormatException e) {
                    // Ignora le righe non numeriche (se ce ne sono)
                }
            }
        }
        for (RigaTabellaNumeriPrimi rigaTabellaNumeriPrimi : listaRigaTabellaNumeriPrimi) {
            BigInteger divisore = new BigInteger(rigaTabellaNumeriPrimi.divisorePrimo());
            numeriEsistenti.add(divisore);
        }
        List<BigInteger> numeriOrdinati = new ArrayList<>(numeriEsistenti);
        Collections.sort(numeriOrdinati);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(percorsoFile.toFile()))) {
            for (BigInteger numero : numeriOrdinati) {
                writer.write(numero.toString());
                writer.newLine();
            }
        }
    }
}