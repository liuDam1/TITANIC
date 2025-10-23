package es.etg.psp;

import es.etg.psp.titanic.servicio.ServicioEmergencias;

/**
 * Clase principal del programa que inicia la simulación de rescate del Titanic.
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        ServicioEmergencias servicio = new ServicioEmergencias();
        servicio.iniciarSimulacion();
    }
}