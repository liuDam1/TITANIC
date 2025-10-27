package es.etg.psp.titanic.barcos;

import java.util.Random;

public class Bote {
    private static final String ERROR_MENSAJE = "Se requiere el ID del bote";
    private static final int MIN_DEMORA = 2;
    private static final int MAX_DEMORA = 6;
    private static final int MIN_PERSONAS = 1;
    private static final int MAX_PERSONAS = 100;
    private static final String FORMATO_SALIDA = "%s;%d;%d;%d;%d";
    private static final int CODIGO_EXITO = 0;
    private static final int CODIGO_ERROR = 1;
    private static final int MILISEGUNDOS_POR_SEGUNDO = 1000;

    private final String id;
    private final Random generador;

    public Bote(String id) {
        this.id = id;
        this.generador = new Random();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(ERROR_MENSAJE);
            System.exit(CODIGO_ERROR);
        }

        try {
            Bote bote = new Bote(args[0]);
            bote.ejecutarRescate();
            System.exit(CODIGO_EXITO);
        } catch (InterruptedException e) {
            System.exit(CODIGO_ERROR);
        }
    }

    public void ejecutarRescate() throws InterruptedException {
        Thread.sleep(calcularTiempoDemora());

        String datosRescate = generarDatosRescate();
        System.out.print(datosRescate);
    }

    private int calcularTiempoDemora() {
        int segundos = MIN_DEMORA + generador.nextInt(MAX_DEMORA - MIN_DEMORA + 1);
        return segundos * MILISEGUNDOS_POR_SEGUNDO;
    }

    private String generarDatosRescate() {
        int total = generarTotalPersonas();
        int mujeres = generarMujeres(total);
        int varones = generarVarones(total, mujeres);
        int niños = calcularNiños(total, mujeres, varones);

        return String.format(FORMATO_SALIDA, id, total, mujeres, varones, niños);
    }

    private int generarTotalPersonas() {
        return MIN_PERSONAS + generador.nextInt(MAX_PERSONAS - MIN_PERSONAS + 1);
    }

    private int generarMujeres(int total) {
        return generador.nextInt(total + 1);
    }

    private int generarVarones(int total, int mujeres) {
        return generador.nextInt(total - mujeres + 1);
    }

    private int calcularNiños(int total, int mujeres, int varones) {
        return total - mujeres - varones;
    }
}
