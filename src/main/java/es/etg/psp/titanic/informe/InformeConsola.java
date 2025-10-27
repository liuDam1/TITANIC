package es.etg.psp.titanic.informe;

import java.util.List;

import es.etg.psp.titanic.model.Persona;

public class InformeConsola implements Informe {
    private static final String TITULO_INFORME = "===== INFORME EN CONSOLA =====";
    private static final String FORMATO_LINEA = "Total salvados en %s: %d%n";
    private static final String SEPARADOR = "==============================";
    private static final String FORMATO_TOTAL = "TOTAL SALVADOS: %d%n";

    @Override
    public void generarInforme(List<Persona> resultados) {
        int totalPersonas = resultados.stream().mapToInt(persona -> persona.getTotalPersonas()).sum();

        System.out.println(TITULO_INFORME);
        for (Persona persona : resultados) {
            System.out.printf(FORMATO_LINEA, persona.getIdentificadorBote(), persona.getTotalPersonas());
        }
        System.out.println(SEPARADOR);
        System.out.printf(FORMATO_TOTAL, totalPersonas);
    }
}