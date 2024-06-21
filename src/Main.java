public class Main {
    public static void main(String[] args) {
        stampaPrimi(Long.parseLong(args[0]), 2, true);
    }

    private static void stampaPrimi(long numero, long divisore, boolean primoGiro) {
        if (primoGiro) {
            System.out.println();
            System.out.println(inserisciTrattini(true));
            System.out.println(inserisciSpazi("Numero", TipoColonna.INIZIO) + inserisciSpazi("Divisore", TipoColonna.META) + inserisciSpazi("Esponente Divisore", TipoColonna.META) + inserisciSpazi("Risultato", TipoColonna.FINE));
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
                System.out.println(inserisciSpazi(numeroVecchio + "", TipoColonna.INIZIO) + inserisciSpazi("" + divisore, TipoColonna.META) + inserisciSpazi("" + potenzaPrimo, TipoColonna.META) + inserisciSpazi("" + numero, TipoColonna.FINE));
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
            System.out.println(inserisciSpazi(numero + "", TipoColonna.INIZIO) + inserisciSpazi(numero + "", TipoColonna.META) + inserisciSpazi("1", TipoColonna.META) + inserisciSpazi("1", TipoColonna.FINE));
            System.out.println(inserisciTrattini(true));
            System.out.println();
        }
        return true;
    }

    private static String inserisciSpazi(String numeroStringa, TipoColonna tipoColonna) {
        StringBuilder s = new StringBuilder();
        if (tipoColonna != null) {
            s.append("|");
        }
        s.append(" " + numeroStringa);
        long numeroSpazi = 20 - numeroStringa.length();
        for (long indice = 0; indice < numeroSpazi; indice++) {
            s.append(" ");
        }
        if (TipoColonna.FINE.equals(tipoColonna)) {
            s.append("|");
        }
        return s.toString();
    }

    private static String inserisciSpazi(String numeroStringa) {
        return inserisciSpazi(numeroStringa, null);
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

    public enum TipoColonna {
        INIZIO,
        META,
        FINE
    }
}