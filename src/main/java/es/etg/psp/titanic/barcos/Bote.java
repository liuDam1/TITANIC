package es.etg.psp.titanic.barcos;

import java.util.Random;

public class Bote {
    // Constantes
    private static final String ERROR_MENSAJE = "Se requiere el ID del bote";
    private static final int MIN_DEMORA = 2;
    private static final int MAX_DEMORA = 6;
    private static final int MIN_PERSONAS = 1;
    private static final int MAX_PERSONAS = 100;
    private static final String FORMATO_SALIDA = "%s,%d,%d,%d,%d";
    private static final int CODIGO_EXITO = 0;
    private static final int CODIGO_ERROR = 1;
    private static final int MILISEGUNDOS_POR_SEGUNDO = 1000;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(ERROR_MENSAJE);
            System.exit(CODIGO_ERROR);
        }

        try {
            Random generador = new Random();
            Thread.sleep(calcularTiempoDemora(generador));
            generarSalidaRescate(args[0], generador);
            System.exit(CODIGO_EXITO);
        } catch (InterruptedException e) {
            System.exit(CODIGO_ERROR);
        }
    }

    private static int calcularTiempoDemora(Random generador) {
        return (MIN_DEMORA + generador.nextInt(MAX_DEMORA - MIN_DEMORA + 1)) * MILISEGUNDOS_POR_SEGUNDO;
    }

    private static void generarSalidaRescate(String id, Random generador) {
        int total = MIN_PERSONAS + generador.nextInt(MAX_PERSONAS);
        int mujeres = generador.nextInt(total + 1);
        int varones = generador.nextInt(total - mujeres + 1);
        int niños = total - mujeres - varones;
        
        System.out.print(String.format(FORMATO_SALIDA, id, total, mujeres, varones, niños));
    }
}
