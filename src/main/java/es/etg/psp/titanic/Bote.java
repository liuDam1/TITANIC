package es.etg.psp.titanic;

import java.util.Random;

public class Bote {
    // Constantes para valores fijos
    private static final String ERROR_MENSAJE = "Se requiere el ID del bote";
    private static final int MIN_DEMORA = 2;
    private static final int MAX_DEMORA = 6;
    private static final int MIN_PERSONAS = 1;
    private static final int MAX_PERSONAS = 100;
    private static final String FORMATO_SALIDA = "%s,%d,%d,%d,%d%n";
    private static final int CODIGO_EXITO = 0;
    private static final int CODIGO_ERROR = 1;
    private static final int MILISEGUNDOS_POR_SEGUNDO = 1000;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(ERROR_MENSAJE);
            System.exit(CODIGO_ERROR);
        }

        String identificador = args[0];
        Random generadorAleatorio = new Random();

        try {
            // Simular tiempo de demora entre MIN_DEMORA y MAX_DEMORA segundos
            int tiempoDemora = MIN_DEMORA + generadorAleatorio.nextInt(MAX_DEMORA - MIN_DEMORA + 1);
            Thread.sleep(tiempoDemora * MILISEGUNDOS_POR_SEGUNDO);

            // Generar datos aleatorios
            int totalPersonas = MIN_PERSONAS + generadorAleatorio.nextInt(MAX_PERSONAS);
            int mujeres = generadorAleatorio.nextInt(totalPersonas + 1);
            int varones = generadorAleatorio.nextInt(totalPersonas - mujeres + 1);
            int niños = totalPersonas - mujeres - varones;

            // Salida con formato CSV simple
            System.out.printf(FORMATO_SALIDA, identificador, totalPersonas, mujeres, varones, niños);
            System.exit(CODIGO_EXITO);

        } catch (InterruptedException e) {
            System.exit(CODIGO_ERROR);
        }
    }
}