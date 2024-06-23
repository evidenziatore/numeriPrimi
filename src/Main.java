import java.util.Date;

public class Main {
    public static void main(String... args) {
        stampaPrimi(Long.parseLong(args[0]));
    }

    private static void stampaPrimi(long numero) {
        Date dataInizio = new Date();
        System.out.println();
        System.out.println(inserisciTrattini(true));
        System.out.println(inserisciSpazi("Numero", "Divisore Primo", "Potenza Divisore", "Risultato"));
        System.out.println(inserisciTrattini(false));
        long possibileDivisore = 2;
        while (numero >= possibileDivisore) {
            if (numero % possibileDivisore == 0) {
                long numeroVecchio = numero;
                long potenzaPrimo = 0;
                while (numero % possibileDivisore == 0) {
                    numero = numero / possibileDivisore;
                    potenzaPrimo++;
                }
                if (potenzaPrimo > 0) {
                    System.out.println(inserisciSpazi(numeroVecchio, possibileDivisore, potenzaPrimo, numero));
                    if (numero > 1) {
                        System.out.println(inserisciTrattini(false));
                    } else {
                        System.out.println(inserisciTrattini(true));
                    }
                }
            }
            possibileDivisore++;
        }
        System.out.println();
        System.out.println("TEMPO IMPIEGATO: " + (new Date().getTime() - dataInizio.getTime()) + " ms");
        System.out.println();
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