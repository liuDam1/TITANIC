package es.etg.psp.titanic;

import es.etg.psp.titanic.servicio.ServicioEmergencias;

public class Main {
    public static void main(String[] args) {
        ServicioEmergencias servicio = new ServicioEmergencias();
        servicio.iniciarSimulacion();
    }
}