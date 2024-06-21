public class Main {
    public static void main(String... args) {
        stampaPrimi(Long.parseLong(args[0]), 2, true);
    }

    private static void stampaPrimi(long numero, long divisore, boolean primoGiro) {
        if (primoGiro) {
            System.out.println();
            System.out.println(inserisciTrattini(true));
            System.out.println(inserisciSpazi("Numero", "Divisore Primo", "Potenza Divisore", "Risultato"));
            System.out.println(inserisciTrattini(false));
        }
        if (numero > 1 && !verificaPrimo(numero, true)) {
            long numeroVecchio = numero;
            long potenzaPrimo = 0;
            while (verificaPrimo(divisore, false) && numero % divisore == 0) {
                numero = numero / divisore;
                potenzaPrimo++;
            }
            if (potenzaPrimo > 0) {
                System.out.println(inserisciSpazi(numeroVecchio, divisore, potenzaPrimo, numero));
                System.out.println(inserisciTrattini(false));
            }
            divisore++;
            stampaPrimi(numero, divisore, false);
        }
    }

    private static boolean verificaPrimo(long numero, boolean stampa) {
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        if (stampa) {
            System.out.println(inserisciSpazi(numero, numero, "1", "1"));
            System.out.println(inserisciTrattini(true));
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