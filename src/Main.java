import java.math.BigInteger;
import java.util.Date;

public class Main {
    public static void main(String... args) {
        try {
            BigInteger numero = new BigInteger(args[0]);
            stampaPrimi(numero);
        } catch (NumberFormatException e) {
            System.out.println("Inserire un numero valido.");
        }
    }

    private static void stampaPrimi(BigInteger numero) {
        Date dataInizio = new Date();
        System.out.println();
        System.out.println(inserisciTrattini(true));
        System.out.println(inserisciSpazi("Numero", "Divisore Primo", "Potenza Divisore", "Risultato"));
        System.out.println(inserisciTrattini(false));
        BigInteger divisore = primoDivisore(numero, BigInteger.valueOf(2));
        while (numero.compareTo(BigInteger.ONE) > 0 && numero.compareTo(divisore) >= 0) {
            BigInteger numeroVecchio = numero;
            long potenzaPrimo = 0;
            do {
                numero = numero.divide(divisore);
                potenzaPrimo++;
            } while (numero.mod(divisore).equals(BigInteger.ZERO));
            if (potenzaPrimo > 0) {
                System.out.println(inserisciSpazi(numeroVecchio, divisore, potenzaPrimo, numero));
                if (numero.compareTo(BigInteger.ONE) > 0) {
                    System.out.println(inserisciTrattini(false));
                } else {
                    System.out.println(inserisciTrattini(true));
                }
            }
            divisore = primoDivisore(numero, divisore);
        }
        System.out.println();
        System.out.println("TEMPO IMPIEGATO: " + (new Date().getTime() - dataInizio.getTime()) + " ms");
        System.out.println();
    }

    private static BigInteger primoDivisore(BigInteger numero, BigInteger possibileDivisore) {
        for (BigInteger i = possibileDivisore; i.multiply(i).compareTo(numero) <= 0; i = i.add(BigInteger.ONE)) {
            if (numero.mod(i).equals(BigInteger.ZERO)) {
                return i;
            }
        }
        return numero;
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