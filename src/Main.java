import java.util.Date;

public class Main {
    public static void main(String... args) {
        stampaPrimi(Long.parseLong(args[0]), 2, true, new Date());
    }

    private static void stampaPrimi(long numero, long possibileDivisore, boolean primoGiro, Date dataInizio) {
        if (primoGiro) {
            System.out.println();
            System.out.println(inserisciTrattini(true));
            System.out.println(inserisciSpazi("Numero", "Divisore Primo", "Potenza Divisore", "Risultato"));
            System.out.println(inserisciTrattini(false));
        }
        if (numero > 1 && !verificaPrimo(numero, possibileDivisore, true, dataInizio)) {
            long numeroVecchio = numero;
            long potenzaPrimo = 0;
            while (verificaPrimo(possibileDivisore, 2, false, dataInizio) && numero % possibileDivisore == 0) {
                numero = numero / possibileDivisore;
                potenzaPrimo++;
            }
            if (potenzaPrimo > 0) {
                System.out.println(inserisciSpazi(numeroVecchio, possibileDivisore, potenzaPrimo, numero));
                System.out.println(inserisciTrattini(false));
            }
            possibileDivisore++;
            stampaPrimi(numero, possibileDivisore, false, dataInizio);
        }
    }

    private static boolean verificaPrimo(long numero, long possibileDivisore, boolean stampa, Date dataInizio) {
        for (long i = possibileDivisore; i * i <= numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        if (stampa) {
            System.out.println(inserisciSpazi(numero, numero, "1", "1"));
            System.out.println(inserisciTrattini(true));
            System.out.println();
            System.out.println("TEMPO IMPIEGATO: " + (new Date().getTime() - dataInizio.getTime()) + " ms");
            System.out.println();
        }
        return true;
    }

    private static String inserisciSpazi(Object... numeriStringa) {
        StringBuilder s = new StringBuilder();
        for (Object numeroStringa : numeriStringa) {
            s.append("| ");
            s.append(numeroStringa);
            long numeroSpazi = 20 - numeroStringa.toString().length();
            for (long indice = 0; indice < numeroSpazi; indice++) {
                s.append(" ");
            }
        }
        s.append("|");
        return s.toString();
    }

    private static String inserisciTrattini(boolean primaUltima) {
        StringBuilder s = new StringBuilder();
        for (long indice = 0; indice < 89; indice++) {
            if (!primaUltima) {
                if (indice == 0 || indice == 22 || indice == 44 || indice == 66 || indice == 88) {
                    s.append("|");
                } else {
                    s.append("-");
                }
            } else {
                s.append("-");
            }
        }
        return s.toString();
    }
}